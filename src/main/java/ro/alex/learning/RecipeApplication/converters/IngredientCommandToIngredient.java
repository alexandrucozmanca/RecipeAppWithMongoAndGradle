package ro.alex.learning.RecipeApplication.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import ro.alex.learning.RecipeApplication.command.IngredientCommand;
import ro.alex.learning.RecipeApplication.domain.Ingredient;
import ro.alex.learning.RecipeApplication.domain.Recipe;

@Component
public class IngredientCommandToIngredient implements Converter<IngredientCommand, Ingredient> {

    UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureCommandToUnitOfMeasure;


    public IngredientCommandToIngredient(@Nullable UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureCommandToUnitOfMeasure) {
        this.unitOfMeasureCommandToUnitOfMeasure = unitOfMeasureCommandToUnitOfMeasure;
    }


    @Nullable
    @Override
    public Ingredient convert(IngredientCommand ingredientCommand) {
        if (ingredientCommand == null)
        return null;

        final Ingredient ingredient = new Ingredient();
        ingredient.setId(ingredientCommand.getId());

        if(ingredientCommand.getRecipeId() != null){
            Recipe recipe = new Recipe();
            recipe.setId(ingredientCommand.getRecipeId());
            ingredient.setRecipe(recipe);
            recipe.addIngredient(ingredient);
        }

        ingredient.setUom(unitOfMeasureCommandToUnitOfMeasure.convert(ingredientCommand.getUom()));
        ingredient.setAmount(ingredientCommand.getAmount());
        ingredient.setDescription(ingredientCommand.getDescription());

        return ingredient;
    }
}
