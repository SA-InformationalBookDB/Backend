package hu.bme.szarch.ibdb.repository;

import hu.bme.szarch.ibdb.domain.Book;
import hu.bme.szarch.ibdb.domain.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.time.OffsetDateTime;
import java.util.List;

public interface BookRepository extends CrudRepository<Book, String> {

    Page<Book> findAllByOrderBySoldDesc(Pageable pageable);

    Page<Book> findAllByPublishedAfterAndOrderByViewsDesc(Pageable pageable, OffsetDateTime publishedAfter);

    Page<Book> findAllByOrderByViewsDesc(Pageable pageable);

    Page<Book> findAllByCategoriesInAnAndPublishedAfterAndAuthorIsInAndOrderByViewDesc(Pageable pageable, List<Category> categories, OffsetDateTime publishedAfter, List<String> authors);

    Page<Book> findAllByTitleContains(Pageable pageable, String queryString);

}
