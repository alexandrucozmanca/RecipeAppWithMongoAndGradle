package ro.alex.learning.RecipeApplication.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ro.alex.learning.RecipeApplication.command.RecipeCommand;
import ro.alex.learning.RecipeApplication.converters.RecipeCommandToRecipe;
import ro.alex.learning.RecipeApplication.converters.RecipeToRecipeCommand;
import ro.alex.learning.RecipeApplication.domain.Recipe;
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

       return  recipeRepository.findById(l);
    }

    @Override
    public Mono<RecipeCommand> findCommandById(String id){

        return recipeRepository.findById(id)
                .map(recipe -> {
                    RecipeCommand command = recipeToRecipeCommand.convert(recipe);

                    command.getIngredients().forEach(rc ->{
                        rc.setRecipeId(command.getId());
                    });

                    return command;
                });
    }

    @Override
    public Mono<RecipeCommand> saveRecipeCommand(RecipeCommand command) {

        return recipeRepository.save(recipeCommandToRecipe.convert(command))
                .map(recipeToRecipeCommand::convert);
    }

    @Override
    public Mono<Void> deleteById(String idToDelete)    {
        recipeRepository.deleteById(idToDelete).block();

        return Mono.empty();
    }

}
