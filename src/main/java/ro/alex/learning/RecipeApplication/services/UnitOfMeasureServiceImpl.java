package ro.alex.learning.RecipeApplication.services;

import org.springframework.stereotype.Service;
import ro.alex.learning.RecipeApplication.command.UnitOfMeasureCommand;
import ro.alex.learning.RecipeApplication.converters.UnitOfMeasureToUnitOfMeasureCommand;
import ro.alex.learning.RecipeApplication.domain.UnitOfMeasure;
import ro.alex.learning.RecipeApplication.repositories.UnitOfMeasureRepository;

import java.util.HashSet;
import java.util.Set;

@Service
public class UnitOfMeasureServiceImpl  implements  UnitOfMeasureService{

    UnitOfMeasureRepository uomRepository;
    UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

    public UnitOfMeasureServiceImpl(UnitOfMeasureRepository uomRepository, UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand) {
        this.uomRepository = uomRepository;
        this.unitOfMeasureToUnitOfMeasureCommand = unitOfMeasureToUnitOfMeasureCommand;
    }

    @Override
    public Set<UnitOfMeasureCommand> listAllUoms(){
        Set<UnitOfMeasureCommand> uomSet = new HashSet<>();

        for(UnitOfMeasure unitOfMeasure : uomRepository.findAll())
            uomSet.add(unitOfMeasureToUnitOfMeasureCommand.convert(unitOfMeasure));

        return uomSet;
    }
}
