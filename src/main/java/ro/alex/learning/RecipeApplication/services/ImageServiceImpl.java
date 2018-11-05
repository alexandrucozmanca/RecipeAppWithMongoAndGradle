package ro.alex.learning.RecipeApplication.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;
import ro.alex.learning.RecipeApplication.domain.Recipe;
import ro.alex.learning.RecipeApplication.repositories.reactive.RecipeReactiveRepository;

import java.io.IOException;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    private final RecipeReactiveRepository recipeRepository;

    public ImageServiceImpl(RecipeReactiveRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Mono<Void> saveImageFile(String recipeId, MultipartFile file) {

        Mono<Recipe> recipeMono = recipeRepository.findById(recipeId)
                .map(recipe -> {
                    Byte[] imageObject = new Byte[0];
                    try {
                        imageObject = new Byte[file.getBytes().length];

                        int i = 0;

                        for (byte b : file.getBytes()) {
                            imageObject[i++] = b;
                        }

                        recipe.setImage(imageObject);

                        return recipe;
                    } catch (IOException e) {
                        log.error("Error occurred", e);
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                });

        recipeRepository.save(recipeMono.block()).block();

        return Mono.empty();
    }
}
