package ro.alex.learning.RecipeApplication.repositories.reactive;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;
import ro.alex.learning.RecipeApplication.domain.UnitOfMeasure;


public interface UnitOfMeasureReactiveRepository extends ReactiveMongoRepository<UnitOfMeasure, String> {

  Mono<UnitOfMeasure> findByDescription(String description);

}
