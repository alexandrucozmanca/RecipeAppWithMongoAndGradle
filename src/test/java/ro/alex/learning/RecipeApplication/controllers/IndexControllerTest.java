package ro.alex.learning.RecipeApplication.controllers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import reactor.core.publisher.Flux;
import ro.alex.learning.RecipeApplication.domain.Recipe;
import ro.alex.learning.RecipeApplication.services.RecipeService;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class IndexControllerTest {


    IndexController indexController;

    @Mock
    RecipeService recipeService;
    @Mock
    Model model;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        indexController = new IndexController(recipeService);
    }


    @Test
    public void testMockMVC() throws Exception{
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("Index"));
    }

    @Test
    public void getIndexPage() {

        Recipe recipe = new Recipe();
        recipe.setId("7");

        Flux<Recipe> recipeFlux = Flux.just(new Recipe(), recipe);

        when(recipeService.getRecipes()).thenReturn(recipeFlux);
        ArgumentCaptor<Flux<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Flux.class);


        //when
        String viewName = indexController.getIndexPage(model);

        //then
        assertEquals("Index" , viewName);
        verify(recipeService, times(1)).getRecipes();
        verify(model, times(1)).addAttribute(eq("recipes"),argumentCaptor.capture());
        Flux<Recipe> fluxInController = argumentCaptor.getValue();

        assertEquals(recipeFlux.count().block(), fluxInController.count().block());
    }
}
