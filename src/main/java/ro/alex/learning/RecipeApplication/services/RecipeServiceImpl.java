package ro.alex.learning.RecipeApplication.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ro.alex.learning.RecipeApplication.command.RecipeCommand;
import ro.alex.learning.RecipeApplication.converters.RecipeCommandToRecipe;
import ro.alex.learning.RecipeApplication.converters.RecipeToRecipeCommand;
import ro.alex.learning.RecipeApplication.domain.Recipe;
import ro.alex.learning.RecipeApplication.exceptions.NotFoundException;
import ro.alex.learning.RecipeApplication.repositories.RecipeRepository;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeCommandToRecipe recipeCommandToRecipe, RecipeToRecipeCommand recipeToRecipeCommand) {
        this.recipeRepository = recipeRepository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    @Override
    public Set<Recipe> getRecipes() {

        log.debug("Get recipes by service");
        Set<Recipe> recipeSet = new TreeSet<>();
        recipeRepository.findAll().iterator().forEachRemaining(recipeSet::add);
        return recipeSet;
    }

    @Override
    public Recipe findById(Long l){

        Optional<Recipe> recipeOptional = recipeRepository.findById(l);

        if(!recipeOptional.isPresent()){
            throw new NotFoundException("Recipe not found for ID value: " + l);
        }

        return recipeOptional.get();
    }

    @Override
    @Transactional
    public RecipeCommand findCommandById(Long l){
        return recipeToRecipeCommand.convert(findById(l));
    }

    @Override
    @Transactional
    public RecipeCommand saveRecipeCommand(RecipeCommand command) {
        Recipe detachedRecipe = recipeCommandToRecipe.convert(command);

        Recipe savedRecipe = recipeRepository.save(detachedRecipe);
        log.debug("Saved RecipeID = " + savedRecipe.getId());
        return recipeToRecipeCommand.convert(savedRecipe);
    }

    @Override
    public void deleteById(Long idToDelete)    {
        recipeRepository.deleteById(idToDelete);
    }

}
