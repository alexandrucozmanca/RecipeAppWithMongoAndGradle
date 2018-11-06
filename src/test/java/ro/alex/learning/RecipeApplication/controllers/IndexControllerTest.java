package ro.alex.learning.RecipeApplication.controllers;

import jdk.nashorn.internal.ir.annotations.Immutable;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import reactor.core.publisher.Flux;
import ro.alex.learning.RecipeApplication.domain.Recipe;
import ro.alex.learning.RecipeApplication.services.RecipeService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@Ignore
@RunWith(SpringRunner.class)
@WebFluxTest
@Import(IndexController.class)
public class IndexControllerTest {

    WebTestClient webTestClient;

    @Autowired
    ApplicationContext applicationContext;

    @MockBean
    RecipeService recipeService;

    @Mock
    Model model;

    @Autowired
    IndexController indexController;

    @Before
    public void setUp() throws Exception{

        webTestClient = WebTestClient.bindToController(indexController).build();
    }

    @Test
    public void testMockMVC() throws Exception{
        //MockMvc mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();

        when(recipeService.getRecipes()).thenReturn(Flux.empty());

//        mockMvc.perform(get("/"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("Index"));

        webTestClient.get().uri("/")
                .exchange()
                .expectStatus().isOk()
                .expectBody();
    }

    @Test
    public void getIndexPage() {

        Set<Recipe> recipes = new HashSet<>();
        recipes.add(new Recipe());

        Recipe recipe = new Recipe();
        recipe.setId("7");
        recipes.add(recipe);

        when(recipeService.getRecipes()).thenReturn( Flux.fromIterable(recipes));
        ArgumentCaptor<List<Recipe>> argumentCaptor = ArgumentCaptor.forClass(List.class);


        //when
        String viewName = indexController.getIndexPage(model);

        //then
        assertEquals("Index" , viewName);
        verify(recipeService, times(1)).getRecipes();
        verify(model, times(1)).addAttribute(eq("recipes"),argumentCaptor.capture());


        List<Recipe> recipeList = argumentCaptor.getValue();
        assertEquals(2, recipeList.size());
    }
}
