package hu.bme.szarch.ibdb.repository;

import hu.bme.szarch.ibdb.domain.Book;
import hu.bme.szarch.ibdb.domain.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;

public interface BookRepository extends CrudRepository<Book, String> {

    List<Book> findAllByOrderBySoldDesc();

    List<Book> findAllByPublishedAfterOrderByViewsDesc(OffsetDateTime publishedAfter);

    List<Book> findAllByOrderByViewsDesc();

    List<Book> findAllByPublishedAfterAndCategoriesInOrderByViewsDesc(OffsetDateTime publishedAfter, Set<Category> categories);

    List<Book> findAllByPublishedAfterAndCategoriesInAndAuthorInOrderByViewsDesc(OffsetDateTime publishedAfter, Set<Category> categories, Set<String> authors);

    List<Book> findAllByTitleContains(String queryString);

    Page<Book> findAllByOrderBySoldDesc(Pageable pageable);

    Page<Book> findAllByPublishedAfterOrderByViewsDesc(Pageable pageable, OffsetDateTime publishedAfter);

    Page<Book> findAllByOrderByViewsDesc(Pageable pageable);

}
