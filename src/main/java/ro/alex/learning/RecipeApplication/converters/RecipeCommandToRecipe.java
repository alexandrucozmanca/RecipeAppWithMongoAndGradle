package ro.alex.learning.RecipeApplication.converters;

import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import ro.alex.learning.RecipeApplication.command.CategoryCommand;
import ro.alex.learning.RecipeApplication.command.IngredientCommand;
import ro.alex.learning.RecipeApplication.command.RecipeCommand;
import ro.alex.learning.RecipeApplication.domain.Recipe;

@Component
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {


    private final CategoryCommandToCategory categoryConverter;
    private final IngredientCommandToIngredient ingredientConverter;
    private final NotesCommandToNotes notesConverter;

    public RecipeCommandToRecipe(CategoryCommandToCategory categoryConverter, IngredientCommandToIngredient ingredientConverter, NotesCommandToNotes notesConverter) {
        this.categoryConverter = categoryConverter;
        this.ingredientConverter = ingredientConverter;
        this.notesConverter = notesConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public Recipe convert(RecipeCommand recipeCommand) {
        if(recipeCommand == null)
            return null;

        Recipe recipe = new Recipe();

        recipe.setId(recipeCommand.getId());
        recipe.setDescription(recipeCommand.getDescription());
        recipe.setPrepTime(recipeCommand.getPrepTime());
        recipe.setCookTime(recipeCommand.getCookTime());
        recipe.setServings(recipeCommand.getServings());
        recipe.setSource(recipeCommand.getSource());
        recipe.setUrl(recipeCommand.getUrl());
        recipe.setDirections(recipeCommand.getDirections());
        recipe.setDifficulty(recipeCommand.getDifficulty());
        recipe.setNotes(notesConverter
                .convert(recipeCommand.getNotes()));

        recipe.setImage(recipeCommand.getImage());

        if(recipeCommand.getCategories() != null && recipeCommand.getCategories().size() > 0)
             for(CategoryCommand categoryCommand: recipeCommand.getCategories())
                 recipe.getCategories().add(categoryConverter
                    .convert(categoryCommand));

        if(recipeCommand.getIngredients() != null && recipeCommand.getIngredients().size() > 0)
            for(IngredientCommand ingredientCommand: recipeCommand.getIngredients())
                recipe.getIngredients().add(ingredientConverter
                    .convert(ingredientCommand));

        return recipe;
    }
}
