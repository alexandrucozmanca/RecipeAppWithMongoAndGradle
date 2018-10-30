package ro.alex.learning.RecipeApplication.converters;

import org.junit.Before;
import org.junit.Test;
import ro.alex.learning.RecipeApplication.command.RecipeCommand;
import ro.alex.learning.RecipeApplication.domain.*;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class RecipeToRecipeCommandTest {

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
    private final Notes NOTES = new Notes();
    private final Long CAT_ID_1 = new Long(3);
    private final Long CAT_ID_2 = new Long(4);
    private final Set<Category> CATEGORIES = new HashSet<>();
    private final Long ING_ID_1 = new Long(5);
    private final Long ING_ID_2 = new Long(6);
    private final Set<Ingredient> INGREDIENTS = new HashSet<>();


    private final CategoryToCategoryCommand categoryConverter = new CategoryToCategoryCommand();
    private final IngredientToIngredientCommand ingredientConverter = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
    private final NotesToNotesCommand notesConverter = new NotesToNotesCommand();
    RecipeToRecipeCommand converter;


    @Before
    public void setUp() {
        converter =
                new RecipeToRecipeCommand(categoryConverter, ingredientConverter, notesConverter);
    }

    @Test
    public void testNullParameter() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(new Recipe()));
    }

    @Test
    public void testConvert() {
        //given
        Recipe recipe = new Recipe();
        recipe.setId(RECIPE_ID);
        recipe.setDescription(DESCRIPTION);
        recipe.setPrepTime(PREP_TIME);
        recipe.setCookTime(COOK_TIME);
        recipe.setServings(SERVINGS);
        recipe.setSource(SOURCE);
        recipe.setUrl(URL);
        recipe.setDirections(DIRECTIONS);
        recipe.setDifficulty(DIFFICULTY);

        NOTES.setId(NOTES_ID);
        recipe.setNotes(NOTES);

        Category category1 = new Category();
        category1.setId(CAT_ID_1);
        CATEGORIES.add(category1);

        Category category2 = new Category();
        category2.setId(CAT_ID_2);
        CATEGORIES.add(category2);
        recipe.setCategories(CATEGORIES);

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(ING_ID_1);
        INGREDIENTS.add(ingredient1);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(ING_ID_2);
        INGREDIENTS.add(ingredient2);
        recipe.setIngredients(INGREDIENTS);

        //when
        RecipeCommand command = converter.convert(recipe);

        // then
        assertNotNull(command);
        assertNotNull(command.getNotes());
        assertEquals(RECIPE_ID, command.getId());
        assertEquals(DESCRIPTION, command.getDescription());
        assertEquals(PREP_TIME, command.getPrepTime());
        assertEquals(COOK_TIME, command.getCookTime());
        assertEquals(SERVINGS, command.getServings());
        assertEquals(SOURCE, command.getSource());
        assertEquals(URL, command.getUrl());
        assertEquals(DIRECTIONS, command.getDirections());
        assertEquals(DIFFICULTY, command.getDifficulty());
        assertEquals(NOTES.getId(), command.getNotes().getId());
        assertEquals(CATEGORIES.size(), command.getCategories().size());
        assertEquals(INGREDIENTS.size(), command.getIngredients().size());
    }
}