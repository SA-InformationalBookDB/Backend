package hu.bme.szarch.ibdb.repository;

import hu.bme.szarch.ibdb.domain.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

public interface CategoryRepository extends CrudRepository<Category, String> {

    List<Category> findAllByIdIn(Set<String> ids);

    List<Category> findAll();

}
