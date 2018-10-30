package ro.alex.learning.RecipeApplication.services;

import ro.alex.learning.RecipeApplication.command.UnitOfMeasureCommand;

import java.util.Set;

public interface UnitOfMeasureService {
    Set<UnitOfMeasureCommand> listAllUoms();
}
