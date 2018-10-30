package ro.alex.learning.RecipeApplication.converters;

import org.junit.Before;
import org.junit.Test;
import ro.alex.learning.RecipeApplication.command.CategoryCommand;
import ro.alex.learning.RecipeApplication.domain.Category;

import static org.junit.Assert.*;

public class CategoryToCategoryCommandTest {

    public static final String DESCRIPTION = "description";
    public static final Long LONG_VALUE = new Long(1L);

    CategoryToCategoryCommand converter;

    @Before
    public void setUp() {
        converter = new CategoryToCategoryCommand();
    }

    @Test
    public void testNullParameter() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(new Category()));
    }

    @Test
    public void testConvert() {
        //given
        Category category = new Category();
        category.setId(LONG_VALUE);
        category.setDescription(DESCRIPTION);

        //when
        CategoryCommand command = converter.convert(category);

        // then
        assertNotNull(command);
        assertEquals(LONG_VALUE, command.getId());
        assertEquals(DESCRIPTION, command.getDescription());
    }
}