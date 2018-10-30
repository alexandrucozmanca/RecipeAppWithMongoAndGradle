package ro.alex.learning.RecipeApplication.repositories;

import org.springframework.data.repository.CrudRepository;
import ro.alex.learning.RecipeApplication.domain.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient,Long> {
}
