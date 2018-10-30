package ro.alex.learning.RecipeApplication.converters;

import org.junit.Before;
import org.junit.Test;
import ro.alex.learning.RecipeApplication.command.UnitOfMeasureCommand;
import ro.alex.learning.RecipeApplication.domain.UnitOfMeasure;

import static org.junit.Assert.*;

public class UnitOfMeasureToUnitOfMeasureCommandTest {

    public static final String DESCRIPTION = "description";
    public static final Long LONG_VALUE = new Long(1L);

    UnitOfMeasureToUnitOfMeasureCommand converter;

    @Before
    public void setUp() {
        converter = new UnitOfMeasureToUnitOfMeasureCommand();
    }

    @Test
    public void testNullParameter() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(new UnitOfMeasure()));
    }

    @Test
    public void testConvert() {
        //given
        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setId(LONG_VALUE);
        uom.setDescription(DESCRIPTION);


        //when
        UnitOfMeasureCommand command = converter.convert(uom);

        // then
        assertNotNull(command);
        assertEquals(LONG_VALUE, command.getId());
        assertEquals(DESCRIPTION, command.getDescription());
    }
}