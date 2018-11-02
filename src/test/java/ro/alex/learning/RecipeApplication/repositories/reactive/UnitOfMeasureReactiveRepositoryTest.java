package ro.alex.learning.RecipeApplication.repositories.reactive;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;
import ro.alex.learning.RecipeApplication.domain.UnitOfMeasure;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataMongoTest
public class UnitOfMeasureReactiveRepositoryTest {

    public static final String UNIT_OF_MEASURE = "unitOfMeasure";
    @Autowired
    UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;

    @Before
    public void setUp() throws Exception{
        unitOfMeasureReactiveRepository.deleteAll().block();
    }


    @Test
    public void testSaveUom() throws Exception
    {
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setDescription(UNIT_OF_MEASURE);

        unitOfMeasureReactiveRepository.save(unitOfMeasure).block();

        Long count = new Long(1L);

        assertEquals(count, unitOfMeasureReactiveRepository.count().block());
    }

    @Test
    public void testFindByDescription() throws Exception{

        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setDescription(UNIT_OF_MEASURE);

        unitOfMeasureReactiveRepository.save(unitOfMeasure).block();

        UnitOfMeasure fetchedUOM = unitOfMeasureReactiveRepository.findByDescription("unitOfMeasure").block();

        assertEquals(UNIT_OF_MEASURE, fetchedUOM.getDescription());

    }
}
