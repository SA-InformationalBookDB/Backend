package hu.bme.szarch.ibdb.service;

import hu.bme.szarch.ibdb.domain.oauth.AccessToken;
import hu.bme.szarch.ibdb.domain.oauth.RefreshToken;
import hu.bme.szarch.ibdb.error.Errors;
import hu.bme.szarch.ibdb.error.ServerException;
import hu.bme.szarch.ibdb.repository.AccessTokenRepository;
import hu.bme.szarch.ibdb.repository.RefreshTokenRepository;
import hu.bme.szarch.ibdb.service.dto.token.AccessTokenResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TokenService extends TokenGenerator {

    @Value("${ibdb.security.oauth2.access-token-expiration-in}")
    private int accessTokenExpirationInXMinutes;

    @Value("${ibdb.security.oauth2.access-token-length}")
    private int accessTokenLength;

    @Value("${ibdb.security.oauth2.refresh-token-length}")
    private int refreshTokenLength;

    private AccessTokenRepository accessTokenRepository;
    private RefreshTokenRepository refreshTokenRepository;

    public TokenService(AccessTokenRepository accessTokenRepository,
                        RefreshTokenRepository refreshTokenRepository) {
        this.accessTokenRepository = accessTokenRepository;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Transactional
    public AccessTokenResult createAccessToken(String userId) throws AuthenticationException {
        return createTokens(userId);
    }

    @Transactional
    public AccessTokenResult refreshAccessToken(String rawRefreshToken) throws AuthenticationException {
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findById(rawRefreshToken);

        if (!refreshToken.isPresent()) {
            throw new ServerException(Errors.INVALID_ACCESS_TOKEN);
        }

        String userId = refreshToken.get().getUserId();

        if(refreshToken.get().getAccessToken() != null) {
            accessTokenRepository.delete(refreshToken.get().getAccessToken());
        }

        refreshTokenRepository.delete(refreshToken.get());

        return createAccessToken(userId);
    }

    public Optional<AccessToken> getUserIdByAccessToken(String rawAccessToken) {
        return accessTokenRepository.findById(rawAccessToken);
    }

    @Transactional
    public void deleteToken(String accessToken) {
        log.info("Delete token");
        accessTokenRepository.findById(accessToken).orElseThrow(() -> new ServerException(Errors.NOT_FOUND));
        refreshTokenRepository.deleteByAccessToken_Value(accessToken);
        accessTokenRepository.deleteById(accessToken);
    }

    @Transactional
    public void deleteExpiredTokens() {
        log.info("Delete expired access tokens");
        List<RefreshToken> refreshTokens = refreshTokenRepository.findAllByAccessToken_ExpirationDateBefore(OffsetDateTime.now());

        if(refreshTokens.size() == 0) return;

        refreshTokens.forEach(refreshToken -> refreshToken.setAccessToken(null));

        refreshTokenRepository.saveAll(refreshTokens);

        accessTokenRepository.deleteAllByExpirationDateBefore(OffsetDateTime.now());
    }

    private AccessTokenResult createTokens(String userId) {
        log.info("Create token");

        AccessToken accessToken = new AccessToken();
        accessToken.setExpirationDate(OffsetDateTime.now().plusMinutes(this.accessTokenExpirationInXMinutes));
        accessToken.setUserId(userId);
        accessToken.setValue(generateRandomToken(accessTokenLength));

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUserId(userId);
        refreshToken.setValue(generateRandomToken(refreshTokenLength));
        refreshToken.setAccessToken(accessToken);

        accessTokenRepository.save(accessToken);
        refreshTokenRepository.save(refreshToken);

        return AccessTokenResult.builder()
                .accessToken(accessToken.getValue())
                .accessTokenExpiration(accessToken.getExpirationDate())
                .refreshToken(refreshToken.getValue())
                .build();
    }

}
