package hu.bme.szarch.ibdb.repository;

import hu.bme.szarch.ibdb.domain.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface ReviewRepository extends CrudRepository<Review, String> {

    Page<Review> findAllByBook_Id(Pageable pageable, String bookId);

}
