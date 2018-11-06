package ro.alex.learning.RecipeApplication.services;

import reactor.core.publisher.Flux;
import ro.alex.learning.RecipeApplication.command.UnitOfMeasureCommand;

public interface UnitOfMeasureService {
    Flux<UnitOfMeasureCommand> listAllUoms();
}
