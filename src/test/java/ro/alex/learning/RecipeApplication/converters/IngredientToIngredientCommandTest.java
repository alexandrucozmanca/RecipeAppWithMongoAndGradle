package ro.alex.learning.RecipeApplication.converters;

import org.junit.Before;
import org.junit.Test;
import ro.alex.learning.RecipeApplication.command.IngredientCommand;
import ro.alex.learning.RecipeApplication.domain.Ingredient;
import ro.alex.learning.RecipeApplication.domain.UnitOfMeasure;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class IngredientToIngredientCommandTest {



    public static final String DESCRIPTION = "description";
    public static final Long ID_VALUE = new Long(1L);
    public static final BigDecimal AMOUNT = new BigDecimal(2);
    public static final Long UOM_ID_VALUE = new Long(2L);
    public static final UnitOfMeasure UNIT_OF_MEASURE = new UnitOfMeasure();


    IngredientToIngredientCommand converter;

    @Before
    public void setUp() {
        converter = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
    }

    @Test
    public void testNullParameter() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(new Ingredient()));
    }

    @Test
    public void testConvertWithNullUOM() {

        //given
        Ingredient ingredient = new Ingredient();
        ingredient.setId(ID_VALUE);
        ingredient.setDescription(DESCRIPTION);
        ingredient.setAmount(AMOUNT);

        // when
        IngredientCommand command = converter.convert(ingredient);

        //then
        assertNotNull(command);
        assertNull(command.getUom());
        assertEquals(ID_VALUE, command.getId());
        assertEquals(DESCRIPTION, command.getDescription());
        assertEquals(AMOUNT, command.getAmount());
    }


    @Test
    public void convert() {
        //given
        Ingredient ingredient = new Ingredient();
        ingredient.setId(ID_VALUE);
        ingredient.setDescription(DESCRIPTION);
        ingredient.setAmount(AMOUNT);
        UNIT_OF_MEASURE.setId(UOM_ID_VALUE);
        ingredient.setUom(UNIT_OF_MEASURE);

        //when
        IngredientCommand command = converter.convert(ingredient);

        // then
        assertNotNull(command);
        assertNotNull(command.getUom());
        assertEquals(ID_VALUE, command.getId());
        assertEquals(DESCRIPTION, command.getDescription());
        assertEquals(AMOUNT, command.getAmount());
        //???? assertEquals(new UnitOfMeasureToUnitOfMeasureCommand().convert(UNIT_OF_MEASURE), command.getUom());
        assertEquals(UOM_ID_VALUE, command.getUom().getId());
    }
}