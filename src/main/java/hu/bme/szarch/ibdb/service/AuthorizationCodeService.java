package hu.bme.szarch.ibdb.service;

import hu.bme.szarch.ibdb.error.Errors;
import hu.bme.szarch.ibdb.error.ServerException;
import hu.bme.szarch.ibdb.repository.AuthorizationCodeRepository;
import hu.bme.szarch.ibdb.domain.oauth.AuthorizationCode;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Optional;

@Service
public class AuthorizationCodeService extends TokenGenerator {

    private AuthorizationCodeRepository authorizationCodeRepository;

    public AuthorizationCodeService(AuthorizationCodeRepository authorizationCodeRepository) {
        this.authorizationCodeRepository = authorizationCodeRepository;
    }

    public String createAuthorizationCode(String userId, String clientId, String redirectUri) {
        AuthorizationCode authorizationCode = new AuthorizationCode();

        authorizationCode.setClientId(clientId);
        authorizationCode.setCode(generateRandomToken(12));
        authorizationCode.setExpirationDate(OffsetDateTime.now().plusMinutes(5));
        authorizationCode.setRedirectUri(redirectUri);
        authorizationCode.setUserId(userId);

        authorizationCodeRepository.save(authorizationCode);

        return authorizationCode.getCode();
    }

    public String consumeAuthorizationCode(String code, String clientId, String redirectUri) {
        Optional<AuthorizationCode> authorizationCode = authorizationCodeRepository.findByCodeAndClientIdAndRedirectUri(code, clientId, redirectUri);

        if(!authorizationCode.isPresent() || authorizationCode.get().getExpirationDate().isBefore(OffsetDateTime.now())) {
            throw new ServerException(Errors.INVALID_AUTHORIZATION_CODE);
        }

        authorizationCodeRepository.delete(authorizationCode.get());

        return authorizationCode.get().getUserId();
    }

    public void deleteExpiredCodes() {
        authorizationCodeRepository.deleteAllByExpirationDateBefore(OffsetDateTime.now());
    }

}
