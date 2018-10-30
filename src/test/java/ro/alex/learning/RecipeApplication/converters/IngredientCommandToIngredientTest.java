package ro.alex.learning.RecipeApplication.converters;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import ro.alex.learning.RecipeApplication.command.IngredientCommand;
import ro.alex.learning.RecipeApplication.command.UnitOfMeasureCommand;
import ro.alex.learning.RecipeApplication.domain.Ingredient;
import ro.alex.learning.RecipeApplication.repositories.RecipeRepository;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class IngredientCommandToIngredientTest {

    public static final String DESCRIPTION = "description";
    public static final Long ID_VALUE = new Long(1L);
    public static final BigDecimal AMOUNT = new BigDecimal(2);
    public static final Long UOM_ID_VALUE = new Long(2L);
    public static final UnitOfMeasureCommand UNIT_OF_MEASURE_COMMAND = new UnitOfMeasureCommand();

    IngredientCommandToIngredient converter;

    @Mock
    RecipeRepository recipeRepository;

    @Before
    public void setUp() {
        converter = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
    }

    @Test
    public void testNullParameter() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(new IngredientCommand()));
    }

    @Test
    public void testConvertWithNullUOM() {

        //given
        IngredientCommand command = new IngredientCommand();
        command.setId(ID_VALUE);
        command.setDescription(DESCRIPTION);
        command.setAmount(AMOUNT);

        // when
        Ingredient ingredient = converter.convert(command);

        //then
        assertNotNull(ingredient);
        assertNull(ingredient.getUom());
        assertEquals(ID_VALUE, ingredient.getId());
        assertEquals(DESCRIPTION, ingredient.getDescription());
        assertEquals(AMOUNT, ingredient.getAmount());


    }

    @Test
    public void testConvert() {
        //given
        IngredientCommand command = new IngredientCommand();
        command.setId(ID_VALUE);
        command.setDescription(DESCRIPTION);
        command.setAmount(AMOUNT);
        UNIT_OF_MEASURE_COMMAND.setId(UOM_ID_VALUE);
        command.setUom(UNIT_OF_MEASURE_COMMAND);

        //when
        Ingredient ingredient = converter.convert(command);

        // then
        assertNotNull(ingredient);
        assertNotNull(ingredient.getUom());
        assertEquals(ID_VALUE, ingredient.getId());
        assertEquals(DESCRIPTION, ingredient.getDescription());
        assertEquals(AMOUNT, ingredient.getAmount());
        assertEquals(UOM_ID_VALUE, ingredient.getUom().getId());
    }
}