package ro.alex.learning.RecipeApplication.services;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ro.alex.learning.RecipeApplication.command.RecipeCommand;
import ro.alex.learning.RecipeApplication.domain.Recipe;

public interface RecipeService {
    Flux<Recipe> getRecipes();

    Mono<Recipe> findById(String l);

    RecipeCommand saveRecipeCommand(RecipeCommand command);

    RecipeCommand findCommandById(String l);

    void deleteById(String idToDelete);


}
