package ro.alex.learning.RecipeApplication.repositories;

import org.springframework.data.repository.CrudRepository;
import ro.alex.learning.RecipeApplication.domain.Category;

import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, Long> {
    Optional<Category> findByDescription(String description);

}
