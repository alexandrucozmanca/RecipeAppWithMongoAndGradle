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

    private final Long RECIPE_ID = new Long(1);
    private final String DESCRIPTION = "description";
    private final Integer PREP_TIME = new Integer (10);
    private final Integer COOK_TIME = new Integer(20);
    private final Integer SERVINGS = new Integer(8);
    private final String URL = "www.url.com";
    private final String DIRECTIONS = "directions";
    private final String SOURCE = "source";
    private final Difficulty DIFFICULTY = Difficulty.EASY;
    private final Long NOTES_ID = new Long(2);
    private final NotesCommand NOTES_COMMAND = new NotesCommand();
    private final Long CAT_ID_1 = new Long(3);
    private final Long CAT_ID_2 = new Long(4);
    private final Set<CategoryCommand> CATEGORIES = new HashSet<>();
    private final Long ING_ID_1 = new Long(5);
    private final Long ING_ID_2 = new Long(6);
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

        NOTES_COMMAND.setId(NOTES_ID);
        command.setNotes(NOTES_COMMAND);

        CategoryCommand categoryCommand1 = new CategoryCommand();
        categoryCommand1.setId(CAT_ID_1);
        CATEGORIES.add(categoryCommand1);

        CategoryCommand categoryCommand2 = new CategoryCommand();
        categoryCommand2.setId(CAT_ID_2);
        CATEGORIES.add(categoryCommand2);
        command.setCategories(CATEGORIES);

        IngredientCommand ingredientCommand1 = new IngredientCommand();
        ingredientCommand1.setId(ING_ID_1);
        INGREDIENTS.add(ingredientCommand1);

        IngredientCommand ingredientCommand2 = new IngredientCommand();
        ingredientCommand2.setId(ING_ID_2);
        INGREDIENTS.add(ingredientCommand2);
        command.setIngredients(INGREDIENTS);

        //when
        Recipe recipe = converter.convert(command);

        // then
        assertNotNull(recipe);
        assertNotNull(recipe.getNotes());
        assertEquals(RECIPE_ID, recipe.getId());
        assertEquals(DESCRIPTION, recipe.getDescription());
        assertEquals(PREP_TIME, recipe.getPrepTime());
        assertEquals(COOK_TIME, recipe.getCookTime());
        assertEquals(SERVINGS, recipe.getServings());
        assertEquals(SOURCE, recipe.getSource());
        assertEquals(URL, recipe.getUrl());
        assertEquals(DIRECTIONS, recipe.getDirections());
        assertEquals(DIFFICULTY, recipe.getDifficulty());
        assertEquals(
                notesConverter.convert(NOTES_COMMAND)
                ,recipe.getNotes());
        assertEquals(CATEGORIES.size(), recipe.getCategories().size());
        assertEquals(INGREDIENTS.size(), recipe.getIngredients().size());
    }
}