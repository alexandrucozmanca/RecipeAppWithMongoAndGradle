package ro.alex.learning.RecipeApplication.services;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import ro.alex.learning.RecipeApplication.command.UnitOfMeasureCommand;
import ro.alex.learning.RecipeApplication.converters.UnitOfMeasureToUnitOfMeasureCommand;
import ro.alex.learning.RecipeApplication.repositories.reactive.UnitOfMeasureReactiveRepository;

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
