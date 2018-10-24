package hu.bme.szarch.ibdb.repository;

import hu.bme.szarch.ibdb.domain.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ReviewRepository extends CrudRepository<Review, String> {

    Page<Review> findAllByBook_Id(Pageable pageable, String bookId);

    Page<Review> findAllByUser_Id(Pageable pageable, String userId);

    Optional<Review> findByIdAndUser_Id(String reviewId, String userId);

}
