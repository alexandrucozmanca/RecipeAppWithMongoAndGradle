package ro.alex.learning.RecipeApplication.services;

import ro.alex.learning.RecipeApplication.command.RecipeCommand;
import ro.alex.learning.RecipeApplication.domain.Recipe;

import java.util.Set;

public interface RecipeService {
    Set<Recipe> getRecipes();

    Recipe findById(Long l);

    RecipeCommand saveRecipeCommand(RecipeCommand command);

    RecipeCommand findCommandById(Long l);

    void deleteById(Long idToDelete);


}
