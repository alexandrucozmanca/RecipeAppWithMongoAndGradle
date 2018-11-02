package ro.alex.learning.RecipeApplication.repositories.reactive;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;
import ro.alex.learning.RecipeApplication.domain.Category;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@DataMongoTest
public class CategoryReactiveRepositoryTest {

    @Autowired
    CategoryReactiveRepository categoryReactiveRepository;

    @Before
    public void setUp() throws Exception{
        categoryReactiveRepository.deleteAll().block();
    }

    @Test
    public void testSave() throws Exception{
        Category category = new Category();
        category.setDescription("category");

        categoryReactiveRepository.save(category).block();

        Long count = new Long(1L);

        assertEquals(count, categoryReactiveRepository.count().block());
    }

    @Test
    public void testFindByDescription() throws Exception{
        Category category = new Category();
        category.setDescription("category");

        categoryReactiveRepository.save(category).then().block();

        Category fetchedCategory = categoryReactiveRepository.findByDescription("category").block();

        assertNotNull(fetchedCategory.getId());
    }
}
