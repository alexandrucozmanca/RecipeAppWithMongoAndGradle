package ro.alex.learning.RecipeApplication.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;
import ro.alex.learning.RecipeApplication.domain.Recipe;
import ro.alex.learning.RecipeApplication.repositories.RecipeRepository;
import ro.alex.learning.RecipeApplication.repositories.reactive.RecipeReactiveRepository;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ImageServiceImplTest {

    @Mock
    RecipeReactiveRepository recipeRepository;

    ImageService imageService;

    @Before
    public void before() throws Exception {
        MockitoAnnotations.initMocks(this);

        imageService = new ImageServiceImpl(recipeRepository);
    }


    @Test
    public void testSaveImageFile() throws Exception {
        //given
        String id = new String("1");
        MultipartFile multipartFile = new MockMultipartFile("imagefile", "testing.txt", "text/plain",
                "ro.Alex.learning".getBytes());

        Recipe recipe = new Recipe();
        recipe.setId(id);

        when(recipeRepository.findById(anyString())).thenReturn(Mono.just(recipe));
        when(recipeRepository.save(any(Recipe.class))).thenReturn(Mono.just(recipe));

        ArgumentCaptor<Recipe> argumentCaptor = ArgumentCaptor.forClass(Recipe.class);

        // when
        imageService.saveImageFile(id, multipartFile);

        // then
        verify(recipeRepository,times(1)).save(argumentCaptor.capture());
        Recipe savedRecipe = argumentCaptor.getValue();
        assertEquals(multipartFile.getBytes().length, savedRecipe.getImage().length);
    }
} 
