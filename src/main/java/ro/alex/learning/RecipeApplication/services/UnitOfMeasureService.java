package ro.alex.learning.RecipeApplication.services;

import reactor.core.publisher.Flux;
import ro.alex.learning.RecipeApplication.command.UnitOfMeasureCommand;

import java.util.Set;

public interface UnitOfMeasureService {
    Flux<UnitOfMeasureCommand> listAllUoms();
}
