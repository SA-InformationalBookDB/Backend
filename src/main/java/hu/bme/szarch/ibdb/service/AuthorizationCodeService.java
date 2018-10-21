package hu.bme.szarch.ibdb.service;

import hu.bme.szarch.ibdb.error.Errors;
import hu.bme.szarch.ibdb.error.ServerException;
import hu.bme.szarch.ibdb.repository.AuthorizationCodeRepository;
import hu.bme.szarch.ibdb.service.dto.oauth.AuthorizationCode;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Optional;

@Service
public class AuthorizationCodeService extends TokenGenerator {

    private AuthorizationCodeRepository authorizationCodeRepository;

    public AuthorizationCodeService(AuthorizationCodeRepository authorizationCodeRepository) {
        this.authorizationCodeRepository = authorizationCodeRepository;
    }

    public AuthorizationCode createAuthorizationCode(String userId, String clientId, String redirectUri) {
        AuthorizationCode authorizationCode = new AuthorizationCode();

        authorizationCode.setClientId(clientId);
        authorizationCode.setCode(generateRandomToken(12));
        authorizationCode.setExpirationDate(OffsetDateTime.now().plusMinutes(5));
        authorizationCode.setRedirectUri(redirectUri);
        authorizationCode.setUserId(userId);

        authorizationCodeRepository.save(authorizationCode);

        return authorizationCode;
    }

    public String consumeAuthorizationCode(String code, String clientId, String redirectUri) {
        Optional<AuthorizationCode> authorizationCode = authorizationCodeRepository.findById(code);

        if(!authorizationCode.isPresent()) {
            throw new ServerException(Errors.INVALID_AUTHORIZATION_CODE);
        }

        String userId = authorizationCode.get().getUserId();

        authorizationCodeRepository.delete(authorizationCode.get());

        return userId;
    }

    public void deleteExpiredCodes() {
        authorizationCodeRepository.deleteAllByExpirationDateBefore(OffsetDateTime.now());
    }

}
