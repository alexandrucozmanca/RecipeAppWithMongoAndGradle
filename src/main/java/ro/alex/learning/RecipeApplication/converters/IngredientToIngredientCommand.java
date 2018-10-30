package ro.alex.learning.RecipeApplication.converters;

import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import ro.alex.learning.RecipeApplication.command.IngredientCommand;
import ro.alex.learning.RecipeApplication.domain.Ingredient;

@Component
public class IngredientToIngredientCommand implements Converter<Ingredient,IngredientCommand> {


    UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

    public IngredientToIngredientCommand(UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand) {
        this.unitOfMeasureToUnitOfMeasureCommand = unitOfMeasureToUnitOfMeasureCommand;
    }

    @Synchronized
    @Nullable
    @Override
    public IngredientCommand convert(Ingredient ingredient) {
        if(ingredient == null)
        return null;

        final IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setAmount(ingredient.getAmount());
        ingredientCommand.setDescription(ingredient.getDescription());
        ingredientCommand.setId(ingredient.getId());
        ingredientCommand.setUom(unitOfMeasureToUnitOfMeasureCommand.convert(ingredient.getUom()));

        if(ingredient.getRecipe() != null)
        ingredientCommand.setRecipeId(ingredient.getRecipe().getId());

        return ingredientCommand;
    }
}
