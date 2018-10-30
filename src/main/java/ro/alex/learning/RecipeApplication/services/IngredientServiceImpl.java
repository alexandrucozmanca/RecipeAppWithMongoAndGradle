package ro.alex.learning.RecipeApplication.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ro.alex.learning.RecipeApplication.command.IngredientCommand;
import ro.alex.learning.RecipeApplication.converters.IngredientCommandToIngredient;
import ro.alex.learning.RecipeApplication.converters.IngredientToIngredientCommand;
import ro.alex.learning.RecipeApplication.domain.Ingredient;
import ro.alex.learning.RecipeApplication.domain.Recipe;
import ro.alex.learning.RecipeApplication.repositories.RecipeRepository;
import ro.alex.learning.RecipeApplication.repositories.UnitOfMeasureRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;


    public IngredientServiceImpl(IngredientCommandToIngredient ingredientCommandToIngredient, IngredientToIngredientCommand ingredientToIngredientCommand,
                                 RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {

        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if(!recipeOptional.isPresent()){
            log.error("Recipe ID not found, ID: " + recipeId);
        }

        Recipe recipe = recipeOptional.get();

        Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .map(ingredient -> ingredientToIngredientCommand.convert(ingredient)).findFirst();

        if(!ingredientCommandOptional.isPresent()){
            log.error("Ingredient id not found: " + ingredientId);
        }

        return ingredientCommandOptional.get();
    }

    @Override
    @Transactional
    public IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand) {

        Long id = ingredientCommand.getId();

        Optional<Recipe> recipeOptional = recipeRepository.findById(ingredientCommand.getRecipeId());
        if (!recipeOptional.isPresent()){
            log.error("Recipe not found for id: " + ingredientCommand.getRecipeId());
            return new IngredientCommand();
        } else {
            Recipe recipe = recipeOptional.get();

            Optional<Ingredient> ingredientOptional = recipe
                    .getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
                    .findFirst();

            if (ingredientOptional.isPresent()){
                Ingredient ingredientFound = ingredientOptional.get();
                ingredientFound.setDescription(ingredientCommand.getDescription());
                ingredientFound.setAmount(ingredientCommand.getAmount());
                ingredientFound.setUom(unitOfMeasureRepository.findById(ingredientCommand.getUom().getId())
                .orElseThrow(() -> new RuntimeException("UOM not Found")));
            } else {
                Ingredient ingredient = ingredientCommandToIngredient.convert(ingredientCommand);
                ingredient.setRecipe(recipe);
                recipe.addIngredient(ingredient);
            }

            Recipe savedRecipe = recipeRepository.save(recipe);

            Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients().stream()
                    .filter(recipeIngredients -> recipeIngredients.getId().equals(id))
                    .findFirst();

            if(!savedIngredientOptional.isPresent()){
                savedIngredientOptional = savedRecipe.getIngredients().stream()
                        .filter(recipeIngredients -> recipeIngredients.getDescription().equals(ingredientCommand.getDescription()))
                        .filter(recipeIngredients -> recipeIngredients.getAmount().equals(ingredientCommand.getAmount()))
                        .filter(recipeIngredients -> recipeIngredients.getUom().getId().equals(ingredientCommand.getUom().getId()))
                        .findFirst();
            }

            return ingredientToIngredientCommand.convert(savedIngredientOptional.get());
        }

    }

    @Override
    public void deleteByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {

        log.debug("Deleting ingredient: " + recipeId + ":" + ingredientId);

        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if(recipeOptional.isPresent()){
            Recipe recipe = recipeOptional.get();
            log.debug("found recipe");

            Optional<Ingredient> ingredientOptional = recipe
                    .getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientId))
                    .findFirst();

            if(ingredientOptional.isPresent()){
                log.debug("found Ingredient");
                Ingredient ingredientToDelete = ingredientOptional.get();
                ingredientToDelete.setRecipe(null);
                recipe.getIngredients().remove(ingredientOptional.get());
                recipeRepository.save(recipe);
            }
        } else {
            log.debug("Recipe Id Not found. Id:" + recipeId);
        }

        /*
        if(!recipeRepository.findById(recipeId).isPresent()){
            log.error("Recipe not found for id: " + recipeId);
        } else{
           Set<Ingredient> ingredients = new TreeSet<>(recipeRepository.findById(recipeId).get().getIngredients());


           ingredients.stream().forEach(ingredient ->
            {if (ingredient.getId().equals(ingredientId))
            { System.out.println("Removing id: " + ingredientId);
                Recipe recipe = recipeRepository.findById(recipeId).get();
                ingredient.setRecipe(null);
                recipe.getIngredients().remove(ingredient);
                recipeRepository.save(recipe);}});

            /*
            Set<Ingredient> ingredients = recipeRepository.findById(recipeId).get().getIngredients();

            Iterator<Ingredient> iterator = ingredients.iterator();

            while(iterator.hasNext()){
                if(Long.valueOf(iterator.next().getId()).equals(ingredientId))
                {
                    iterator.remove();
                    System.out.println("Atempting to delete ing: " + ingredientId);
                }
            }

           recipeRepository.findById(recipeId).get().setIngredients(ingredients);
        }
           */




    }
}