package hu.bme.szarch.ibdb.repository;

import hu.bme.szarch.ibdb.domain.oauth.RefreshToken;
import org.springframework.data.repository.CrudRepository;

import java.time.OffsetDateTime;
import java.util.List;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {

    void deleteByAccessToken_Value(String accessToken);

    List<RefreshToken> findAllByAccessToken_ExpirationDateBefore(OffsetDateTime dateTime);

}
