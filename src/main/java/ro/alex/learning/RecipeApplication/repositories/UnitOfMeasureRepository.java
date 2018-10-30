package ro.alex.learning.RecipeApplication.repositories;

import org.springframework.data.repository.CrudRepository;
import ro.alex.learning.RecipeApplication.domain.UnitOfMeasure;

import java.util.Optional;

public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure, Long> {
    Optional<UnitOfMeasure> findByDescription(String description);
}
