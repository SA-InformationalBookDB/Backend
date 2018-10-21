package hu.bme.szarch.ibdb.repository;

import hu.bme.szarch.ibdb.domain.Favourite;
import org.springframework.data.repository.CrudRepository;

public interface FavouriteRepository extends CrudRepository<Favourite, String> {
}
