package hu.bme.szarch.ibdb.repository;

import hu.bme.szarch.ibdb.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, String> {

    Optional<User> findByEmail(String email);

    List<User> findAll();

}
