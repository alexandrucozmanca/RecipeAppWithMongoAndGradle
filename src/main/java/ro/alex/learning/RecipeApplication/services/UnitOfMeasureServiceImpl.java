package ro.alex.learning.RecipeApplication.services;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import ro.alex.learning.RecipeApplication.command.UnitOfMeasureCommand;
import ro.alex.learning.RecipeApplication.converters.UnitOfMeasureToUnitOfMeasureCommand;
import ro.alex.learning.RecipeApplication.domain.UnitOfMeasure;
import ro.alex.learning.RecipeApplication.repositories.UnitOfMeasureRepository;
import ro.alex.learning.RecipeApplication.repositories.reactive.UnitOfMeasureReactiveRepository;

import java.util.HashSet;
import java.util.Set;

@Service
public class UnitOfMeasureServiceImpl  implements  UnitOfMeasureService{

    UnitOfMeasureReactiveRepository uomRepository;
    UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

    public UnitOfMeasureServiceImpl(UnitOfMeasureReactiveRepository uomRepository, UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand) {
        this.uomRepository = uomRepository;
        this.unitOfMeasureToUnitOfMeasureCommand = unitOfMeasureToUnitOfMeasureCommand;
    }

    @Override
    public Flux<UnitOfMeasureCommand> listAllUoms(){

        return uomRepository
                .findAll()
                .map(unitOfMeasureToUnitOfMeasureCommand::convert);
    }
}
