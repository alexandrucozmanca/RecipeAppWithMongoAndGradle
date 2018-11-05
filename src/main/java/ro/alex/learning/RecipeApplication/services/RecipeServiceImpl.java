package ro.alex.learning.RecipeApplication.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ro.alex.learning.RecipeApplication.command.RecipeCommand;
import ro.alex.learning.RecipeApplication.converters.RecipeCommandToRecipe;
import ro.alex.learning.RecipeApplication.converters.RecipeToRecipeCommand;
import ro.alex.learning.RecipeApplication.domain.Recipe;
import ro.alex.learning.RecipeApplication.exceptions.NotFoundException;
import ro.alex.learning.RecipeApplication.repositories.reactive.RecipeReactiveRepository;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeReactiveRepository recipeRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    public RecipeServiceImpl(RecipeReactiveRepository recipeRepository, RecipeCommandToRecipe recipeCommandToRecipe, RecipeToRecipeCommand recipeToRecipeCommand) {
        this.recipeRepository = recipeRepository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    @Override
    public Flux<Recipe> getRecipes() {

        return recipeRepository.findAll();
    }

    @Override
    public Mono<Recipe> findById(String l){

       Recipe recipe = recipeRepository.findById(l).block();

        if(recipe == null){
            throw new NotFoundException("Recipe not found for ID value: " + l);
        }

        return Mono.just(recipe);
    }

    @Override
    @Transactional
    public RecipeCommand findCommandById(String l){
        return recipeToRecipeCommand.convert(findById(l).block());
    }

    @Override
    @Transactional
    public RecipeCommand saveRecipeCommand(RecipeCommand command) {
        Recipe detachedRecipe = recipeCommandToRecipe.convert(command);

        Recipe savedRecipe = recipeRepository.save(detachedRecipe).block();
        log.debug("Saved RecipeID = " + savedRecipe.getId());
        return recipeToRecipeCommand.convert(savedRecipe);
    }

    @Override
    public void deleteById(String idToDelete)    {
        recipeRepository.deleteById(idToDelete);
    }

}
