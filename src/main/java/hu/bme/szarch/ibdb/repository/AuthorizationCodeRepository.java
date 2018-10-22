package hu.bme.szarch.ibdb.repository;

import hu.bme.szarch.ibdb.domain.oauth.AuthorizationCode;
import org.springframework.data.repository.CrudRepository;

import java.time.OffsetDateTime;
import java.util.Optional;

public interface AuthorizationCodeRepository extends CrudRepository<AuthorizationCode, String> {

    Optional<AuthorizationCode> findByCodeAndClientIdAndRedirectUri(String code, String clientId, String redirectUri);

    void deleteAllByExpirationDateBefore(OffsetDateTime date);
}
