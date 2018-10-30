package ro.alex.learning.RecipeApplication.converters;

import org.junit.Before;
import org.junit.Test;
import ro.alex.learning.RecipeApplication.command.CategoryCommand;
import ro.alex.learning.RecipeApplication.domain.Category;

import static org.junit.Assert.*;

public class CategoryCommandToCategoryTest {

    public static final String DESCRIPTION = "description";
    public static final Long LONG_VALUE = new Long(1L);

    CategoryCommandToCategory converter;

    @Before
    public void setUp() {
        converter = new CategoryCommandToCategory();
    }

    @Test
    public void testNullParameter() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(new CategoryCommand()));
    }

    @Test
    public void testConvert() {
        //given
        CategoryCommand command = new CategoryCommand();
        command.setId(LONG_VALUE);
        command.setDescription(DESCRIPTION);

        //when
        Category category = converter.convert(command);

        // then
        assertNotNull(category);
        assertEquals(LONG_VALUE, category.getId());
        assertEquals(DESCRIPTION, category.getDescription());
    }
}