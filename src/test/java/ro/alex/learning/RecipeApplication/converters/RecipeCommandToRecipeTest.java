package ro.alex.learning.RecipeApplication.converters;

import org.junit.Before;
import org.junit.Test;
import ro.alex.learning.RecipeApplication.command.CategoryCommand;
import ro.alex.learning.RecipeApplication.command.IngredientCommand;
import ro.alex.learning.RecipeApplication.command.NotesCommand;
import ro.alex.learning.RecipeApplication.command.RecipeCommand;
import ro.alex.learning.RecipeApplication.domain.Difficulty;
import ro.alex.learning.RecipeApplication.domain.Recipe;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class RecipeCommandToRecipeTest {

    private final String RECIPE_ID = new String("1");
    private final String DESCRIPTION = "description";
    private final Integer PREP_TIME = new Integer (10);
    private final Integer COOK_TIME = new Integer(20);
    private final Integer SERVINGS = new Integer(8);
    private final String URL = "www.url.com";
    private final String DIRECTIONS = "directions";
    private final String SOURCE = "source";
    private final Difficulty DIFFICULTY = Difficulty.EASY;
    private final String NOTES_ID = new String("2");
    private final NotesCommand NOTES_COMMAND = new NotesCommand();
    private final String CAT_ID_1 = new String("3");
    private final String CAT_ID_2 = new String("4");
    private final Set<CategoryCommand> CATEGORIES = new HashSet<>();
    private final String ING_ID_1 = new String("5");
    private final String ING_ID_2 = new String("6");
    private final Set<IngredientCommand> INGREDIENTS = new HashSet<>();


    private final CategoryCommandToCategory categoryConverter = new CategoryCommandToCategory();
    private final IngredientCommandToIngredient ingredientConverter = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
    private final NotesCommandToNotes notesConverter = new NotesCommandToNotes();
    RecipeCommandToRecipe converter;


    @Before
    public void setUp() {
        converter =
                new RecipeCommandToRecipe(categoryConverter, ingredientConverter, notesConverter);
    }

    @Test
    public void testNullParameter() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(new RecipeCommand()));
    }

    @Test
    public void testConvert() {
        //given
        RecipeCommand command = new RecipeCommand();
        command.setId(RECIPE_ID);
        command.setDescription(DESCRIPTION);
        command.setPrepTime(PREP_TIME);
        command.setCookTime(COOK_TIME);
        command.setServings(SERVINGS);
        command.setSource(SOURCE);
        command.setUrl(URL);
        command.setDirections(DIRECTIONS);
        command.setDifficulty(DIFFICULTY);

        NotesCommand notesCommand = new NotesCommand();
        notesCommand.setId(NOTES_ID);
        command.setNotes(notesCommand);

        CategoryCommand categoryCommand1 = new CategoryCommand();
        categoryCommand1.setId(CAT_ID_1);


        CategoryCommand categoryCommand2 = new CategoryCommand();
        categoryCommand2.setId(CAT_ID_2);

        command.getCategories().add(categoryCommand1);
        command.getCategories().add(categoryCommand2);

        IngredientCommand ingredientCommand1 = new IngredientCommand();
        ingredientCommand1.setId(ING_ID_1);


        IngredientCommand ingredientCommand2 = new IngredientCommand();
        ingredientCommand2.setId(ING_ID_2);

        command.getIngredients().add(ingredientCommand1);
        command.getIngredients().add(ingredientCommand2);

        //when
        Recipe recipe = converter.convert(command);

        // then
        assertNotNull(recipe);
        assertEquals(RECIPE_ID, recipe.getId());
        assertEquals(DESCRIPTION, recipe.getDescription());
        assertEquals(PREP_TIME, recipe.getPrepTime());
        assertEquals(COOK_TIME, recipe.getCookTime());
        assertEquals(SERVINGS, recipe.getServings());
        assertEquals(SOURCE, recipe.getSource());
        assertEquals(URL, recipe.getUrl());
        assertEquals(DIRECTIONS, recipe.getDirections());
        assertEquals(DIFFICULTY, recipe.getDifficulty());
        assertEquals(NOTES_ID, recipe.getNotes().getId());
        assertEquals(2, recipe.getCategories().size());
        assertEquals(2, recipe.getIngredients().size());
    }
}