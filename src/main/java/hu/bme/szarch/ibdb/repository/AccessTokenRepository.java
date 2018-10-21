package hu.bme.szarch.ibdb.repository;

import hu.bme.szarch.ibdb.domain.oauth.AccessToken;
import org.springframework.data.repository.CrudRepository;

import java.time.OffsetDateTime;

public interface AccessTokenRepository extends CrudRepository<AccessToken, String> {

    void deleteAllByExpirationDateBefore(OffsetDateTime date);

}
