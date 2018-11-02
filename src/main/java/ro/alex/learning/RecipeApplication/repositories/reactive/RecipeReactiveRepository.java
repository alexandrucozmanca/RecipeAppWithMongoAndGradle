package ro.alex.learning.RecipeApplication.repositories.reactive;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;
import ro.alex.learning.RecipeApplication.domain.Recipe;



public interface RecipeReactiveRepository extends ReactiveMongoRepository<Recipe, String> {

    Mono<Recipe> findById(String id);
}
