package ro.alex.learning.RecipeApplication.repositories;

import org.springframework.data.repository.CrudRepository;
import ro.alex.learning.RecipeApplication.domain.Recipe;

import java.util.Optional;

public interface RecipeRepository extends CrudRepository<Recipe,Long> {
    Optional<Recipe> findByDescription(String description);

    Optional<Recipe> findById(Long id);
}
