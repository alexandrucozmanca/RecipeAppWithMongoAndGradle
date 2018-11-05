package ro.alex.learning.RecipeApplication.services;

import reactor.core.publisher.Mono;
import ro.alex.learning.RecipeApplication.command.IngredientCommand;

public interface IngredientService {

    Mono<IngredientCommand> findByRecipeIdAndIngredientId(String recipeId, String ingredientId);

    Mono<IngredientCommand>saveIngredientCommand(IngredientCommand ingredientCommand);

    Mono<Object> deleteByRecipeIdAndIngredientId(String recipeId, String ingredientID);
}
