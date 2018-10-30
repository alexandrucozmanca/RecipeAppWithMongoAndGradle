package ro.alex.learning.RecipeApplication.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ro.alex.learning.RecipeApplication.command.UnitOfMeasureCommand;
import ro.alex.learning.RecipeApplication.converters.UnitOfMeasureToUnitOfMeasureCommand;
import ro.alex.learning.RecipeApplication.domain.UnitOfMeasure;
import ro.alex.learning.RecipeApplication.repositories.UnitOfMeasureRepository;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class UnitOfMeasureServiceImplTest {

    UnitOfMeasureService unitOfMeasureService;

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    @Mock
    UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;



    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);

        unitOfMeasureToUnitOfMeasureCommand = new UnitOfMeasureToUnitOfMeasureCommand();

        unitOfMeasureService= new UnitOfMeasureServiceImpl(unitOfMeasureRepository, unitOfMeasureToUnitOfMeasureCommand);
    }

    @Test
    public void listAllUoms() throws Exception{
        // given
        Set<UnitOfMeasure> unitOfMeasureSet = new HashSet<>();

        UnitOfMeasure unitOfMeasure1 = new UnitOfMeasure();
        unitOfMeasure1.setId(1L);
        unitOfMeasureSet.add(unitOfMeasure1);

        UnitOfMeasure unitOfMeasure2 = new UnitOfMeasure();
        unitOfMeasure2.setId(2L);
        unitOfMeasureSet.add(unitOfMeasure2);

        when(unitOfMeasureRepository.findAll()).thenReturn(unitOfMeasureSet);

        //when
        Set<UnitOfMeasureCommand> commands = unitOfMeasureService.listAllUoms();

        //then
        assertEquals(2, commands.size());
        verify(unitOfMeasureRepository, times(1)).findAll();
    }
}
