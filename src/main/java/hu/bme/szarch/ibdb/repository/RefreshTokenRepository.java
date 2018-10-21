package hu.bme.szarch.ibdb.repository;

import hu.bme.szarch.ibdb.domain.oauth.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
}
