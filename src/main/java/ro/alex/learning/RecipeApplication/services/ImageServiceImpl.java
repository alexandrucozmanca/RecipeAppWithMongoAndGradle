package ro.alex.learning.RecipeApplication.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ro.alex.learning.RecipeApplication.domain.Recipe;
import ro.alex.learning.RecipeApplication.repositories.RecipeRepository;

import javax.transaction.Transactional;
import java.io.IOException;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    private final RecipeRepository recipeRepository;

    public ImageServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    @Transactional
    public void saveImageFile(Long recipeId, MultipartFile file) {

        try {
            log.debug("Received a file for recipe id: " + recipeId);

            Recipe recipe = recipeRepository.findById(recipeId).get();

            Byte[] imageObject = new Byte[file.getBytes().length];

            int i = 0;

            for (byte b : file.getBytes()){
                imageObject[i++] = b;
            }

            recipe.setImage(imageObject);

            recipeRepository.save(recipe);
        }

        catch (IOException e){
            log.error("Error occurred", e);

            e.printStackTrace();
        }
    }
}
