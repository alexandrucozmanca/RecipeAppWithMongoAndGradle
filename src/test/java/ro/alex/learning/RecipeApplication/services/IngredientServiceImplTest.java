package ro.alex.learning.RecipeApplication.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ro.alex.learning.RecipeApplication.command.IngredientCommand;
import ro.alex.learning.RecipeApplication.converters.IngredientCommandToIngredient;
import ro.alex.learning.RecipeApplication.converters.IngredientToIngredientCommand;
import ro.alex.learning.RecipeApplication.converters.UnitOfMeasureCommandToUnitOfMeasure;
import ro.alex.learning.RecipeApplication.converters.UnitOfMeasureToUnitOfMeasureCommand;
import ro.alex.learning.RecipeApplication.domain.Ingredient;
import ro.alex.learning.RecipeApplication.domain.Recipe;
import ro.alex.learning.RecipeApplication.repositories.RecipeRepository;
import ro.alex.learning.RecipeApplication.repositories.UnitOfMeasureRepository;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class IngredientServiceImplTest {

    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;


    @Mock
    RecipeRepository recipeRepository;

    @Mock
    IngredientService ingredientService;

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    public IngredientServiceImplTest() {
        this.ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
        this.ingredientCommandToIngredient = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
    };


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        ingredientService = new IngredientServiceImpl(ingredientCommandToIngredient, ingredientToIngredientCommand, recipeRepository, unitOfMeasureRepository);
    }

    @Test
    public void findByRecipeIdAndIngredientId() throws Exception{

    }

    @Test
    public void findByRecipeIdAndIngredientIdHappyPath() throws Exception{
        //given
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(2l);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(3l);

        Ingredient ingredient3 = new Ingredient();
        ingredient3.setId(4l);

        recipe.addIngredient(ingredient1);
        recipe.addIngredient(ingredient2);
        recipe.addIngredient(ingredient3);

        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        // then
        IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId(1L, 3L);

        // when
        assertEquals(Long.valueOf(3L), ingredientCommand.getId());
        assertEquals(Long.valueOf(1L), ingredientCommand.getRecipeId());
        verify(recipeRepository, times(1)).findById(anyLong());
    }

    @Test
    public void testSavedRecipeCommand() throws Exception{
        // given
        IngredientCommand command = new IngredientCommand();
        command.setId(3L);
        command.setRecipeId(2L);

        Optional<Recipe> recipeOptional = Optional.of(new Recipe());

        Recipe savedRecipe = new Recipe();
        savedRecipe.addIngredient(new Ingredient());
        savedRecipe.getIngredients().iterator().next().setId(3L);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
        when(recipeRepository.save(any())).thenReturn(savedRecipe);

        // when
        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);

        // then
        assertEquals(Long.valueOf(3L), savedCommand.getId());
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, times(1)).save(any(Recipe.class));

    }

    @Test
    public void testDeleteByRecipeIdAndIngredientId() throws Exception{
        // given
        Recipe recipe = new Recipe();
        Ingredient ingredient = new Ingredient();
        ingredient.setId(3L);
        recipe.addIngredient(ingredient);
        ingredient.setRecipe(recipe);

        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        // when
        ingredientService.deleteByRecipeIdAndIngredientId(1L, 3L);

        // then
        verify(recipeRepository,times(1)).findById(anyLong());
        verify(recipeRepository,times(1)).save(any(Recipe.class));
    }
}

