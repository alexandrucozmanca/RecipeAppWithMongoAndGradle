package ro.alex.learning.RecipeApplication.converters;

import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import ro.alex.learning.RecipeApplication.command.RecipeCommand;
import ro.alex.learning.RecipeApplication.domain.Category;
import ro.alex.learning.RecipeApplication.domain.Ingredient;
import ro.alex.learning.RecipeApplication.domain.Recipe;

@Component
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand> {

    private final CategoryToCategoryCommand categoryConveter;
    private final IngredientToIngredientCommand ingredientConverter;
    private final NotesToNotesCommand notesConverter;

    public RecipeToRecipeCommand(CategoryToCategoryCommand categoryConveter, IngredientToIngredientCommand ingredientConverter,
                                 NotesToNotesCommand notesConverter) {
        this.categoryConveter = categoryConveter;
        this.ingredientConverter = ingredientConverter;
        this.notesConverter = notesConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public RecipeCommand convert(Recipe recipe) {
        if(recipe == null)
            return null;


        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(recipe.getId());
        recipeCommand.setDescription(recipe.getDescription());
        recipeCommand.setPrepTime(recipe.getPrepTime());
        recipeCommand.setCookTime(recipe.getCookTime());
        recipeCommand.setServings(recipe.getServings());
        recipeCommand.setSource(recipe.getSource());
        recipeCommand.setUrl(recipe.getUrl());
        recipeCommand.setDirections(recipe.getDirections());
        recipeCommand.setDifficulty(recipe.getDifficulty());
        recipeCommand.setNotes(notesConverter.convert(recipe.getNotes()));
        recipeCommand.setImage(recipe.getImage());

        if(recipe.getCategories() != null && recipe.getCategories().size() > 0)
            for(Category category: recipe.getCategories())
                recipeCommand.getCategories().add(categoryConveter
                        .convert(category));

        if(recipe.getIngredients() != null && recipe.getIngredients().size() > 0)
            for(Ingredient ingredient: recipe.getIngredients())
                recipeCommand.getIngredients().add(ingredientConverter
                         .convert(ingredient));

        return recipeCommand;
    }
}
