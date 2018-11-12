package hu.bme.szarch.ibdb.repository;

import hu.bme.szarch.ibdb.domain.Review;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends CrudRepository<Review, String> {

    List<Review> findAllByBook_Id(String bookId);

    List<Review> findAllByUser_Id(String userId);

    Optional<Review> findByIdAndUser_Id(String reviewId, String userId);

}
