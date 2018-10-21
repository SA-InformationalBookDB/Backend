package hu.bme.szarch.ibdb.repository;

import hu.bme.szarch.ibdb.domain.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, String> {
}
