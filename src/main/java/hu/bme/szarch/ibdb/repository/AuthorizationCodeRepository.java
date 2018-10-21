package hu.bme.szarch.ibdb.repository;

import hu.bme.szarch.ibdb.service.dto.oauth.AuthorizationCode;
import org.springframework.data.repository.CrudRepository;

import java.time.OffsetDateTime;

public interface AuthorizationCodeRepository extends CrudRepository<AuthorizationCode, String> {
    void deleteAllByExpirationDateBefore(OffsetDateTime date);
}
