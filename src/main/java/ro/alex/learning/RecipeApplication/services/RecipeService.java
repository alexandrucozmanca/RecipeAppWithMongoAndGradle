package ro.alex.learning.RecipeApplication.services;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ro.alex.learning.RecipeApplication.command.RecipeCommand;
import ro.alex.learning.RecipeApplication.domain.Recipe;

public interface RecipeService {
    Flux<Recipe> getRecipes();

    Mono<Recipe> findById(String l);

    Mono<RecipeCommand> saveRecipeCommand(RecipeCommand command);

    Mono<RecipeCommand> findCommandById(String l);

    Mono<Void> deleteById(String idToDelete);


}
