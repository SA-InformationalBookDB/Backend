package hu.bme.szarch.ibdb.repository;

import hu.bme.szarch.ibdb.domain.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, String> {
}
