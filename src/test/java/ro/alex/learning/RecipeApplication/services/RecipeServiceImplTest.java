package ro.alex.learning.RecipeApplication.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ro.alex.learning.RecipeApplication.command.RecipeCommand;
import ro.alex.learning.RecipeApplication.converters.RecipeCommandToRecipe;
import ro.alex.learning.RecipeApplication.converters.RecipeToRecipeCommand;
import ro.alex.learning.RecipeApplication.domain.Recipe;
import ro.alex.learning.RecipeApplication.exceptions.NotFoundException;
import ro.alex.learning.RecipeApplication.repositories.RecipeRepository;
import ro.alex.learning.RecipeApplication.repositories.reactive.RecipeReactiveRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class RecipeServiceImplTest {

    RecipeService recipeService;

    @Mock
    RecipeReactiveRepository recipeRepository;

    @Mock
    RecipeCommandToRecipe recipeCommandToRecipe;

    @Mock
    RecipeToRecipeCommand recipeToRecipeCommand;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        recipeService = new RecipeServiceImpl(
                recipeRepository, recipeCommandToRecipe, recipeToRecipeCommand);
    }

    @Test
    public void getRecipeByIdTest() {
        Recipe recipe = new Recipe();
        recipe.setId("1");

        when(recipeRepository.findById(anyString())).thenReturn(Mono.just(recipe));

        Recipe recipeReturned = recipeService.findById("1").block();

        assertNotNull("Null recipe returned", recipeReturned);
        verify(recipeRepository,times(1)).findById(anyString());
        verify(recipeRepository, never()).findAll();
    }

    @Test
    public void getRecipeCommandByIdTest() throws Exception{
        Recipe recipe = new Recipe();
        recipe.setId("1");


        when(recipeRepository.findById(anyString())).thenReturn(Mono.just(recipe));
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId("1");

        when(recipeToRecipeCommand.convert(any())).thenReturn(recipeCommand);

        Mono<RecipeCommand> commandById = recipeService.findCommandById("1");

        assertNotNull("Null recipe returned", commandById.block());
        verify(recipeRepository, times(1)).findById(anyString());
        verify(recipeRepository, never()).findAll();
    }

    @Test
    public void getRecipesTest() {

        Recipe recipe = new Recipe();
        Flux<Recipe> recipesData = Flux.just(recipe);

        when(recipeService.getRecipes()).thenReturn(recipesData);

        Flux<Recipe> recipes = recipeService.getRecipes();

        assertEquals(recipes.count().block(), recipesData.count().block());
        verify(recipeRepository, times(1)).findAll();
    }

    @Test
    public void deleteByIdTest() throws Exception{
        String idToDelete = new String("2");

        when(recipeRepository.deleteById(anyString())).thenReturn(Mono.empty());

        recipeService.deleteById(idToDelete);

        verify(recipeRepository, times(1)).deleteById(anyString());
    }
}