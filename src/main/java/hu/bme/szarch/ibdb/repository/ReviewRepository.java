package hu.bme.szarch.ibdb.repository;

import hu.bme.szarch.ibdb.domain.Review;
import org.springframework.data.repository.CrudRepository;

public interface ReviewRepository extends CrudRepository<Review, String> {
}
