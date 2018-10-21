package hu.bme.szarch.ibdb.service;

import hu.bme.szarch.ibdb.domain.oauth.AccessToken;
import hu.bme.szarch.ibdb.domain.oauth.RefreshToken;
import hu.bme.szarch.ibdb.error.Errors;
import hu.bme.szarch.ibdb.error.ServerException;
import hu.bme.szarch.ibdb.repository.AccessTokenRepository;
import hu.bme.szarch.ibdb.repository.RefreshTokenRepository;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Optional;

@Service
public class TokenService extends TokenGenerator implements AuthorizationServerTokenServices {

    private AccessTokenRepository accessTokenRepository;
    private RefreshTokenRepository refreshTokenRepository;

    public TokenService(AccessTokenRepository accessTokenRepository, RefreshTokenRepository refreshTokenRepository) {
        this.accessTokenRepository = accessTokenRepository;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Override
    public OAuth2AccessToken createAccessToken(OAuth2Authentication oAuth2Authentication) throws AuthenticationException {


        AccessToken accessToken = createNewAccessToken();

        accessTokenRepository.save(accessToken);

        return accessToken;
    }

    @Override
    public OAuth2AccessToken refreshAccessToken(String s, TokenRequest tokenRequest) throws AuthenticationException {
        Optional<AccessToken> accessToken = accessTokenRepository.findById(s);

        if (!accessToken.isPresent()) {
            throw new ServerException(Errors.INVALID_ACCESS_TOKEN);
        }

        accessTokenRepository.delete(accessToken.get());

        AccessToken newAccessToken = createNewAccessToken();

        accessTokenRepository.save(newAccessToken);

        return newAccessToken;
    }

    @Override
    public OAuth2AccessToken getAccessToken(OAuth2Authentication oAuth2Authentication) {
        return new AccessToken();
    }

    private AccessToken createNewAccessToken() {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setValue(generateRandomToken(16));

        AccessToken accessToken = new AccessToken();
        accessToken.setExpirationDate(OffsetDateTime.now().plusMinutes(5));
        accessToken.setRefreshToken(refreshToken);
        accessToken.setUserId(""); // TODO: Set valid id
        accessToken.setValue(generateRandomToken(16));

        return accessToken;
    }

    public void deleteExpiredTokens() {
        accessTokenRepository.deleteAllByExpirationDateBefore(OffsetDateTime.now());
    }

}
