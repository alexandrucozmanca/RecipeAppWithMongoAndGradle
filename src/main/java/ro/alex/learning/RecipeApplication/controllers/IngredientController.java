package ro.alex.learning.RecipeApplication.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ro.alex.learning.RecipeApplication.command.IngredientCommand;
import ro.alex.learning.RecipeApplication.command.RecipeCommand;
import ro.alex.learning.RecipeApplication.command.UnitOfMeasureCommand;
import ro.alex.learning.RecipeApplication.services.IngredientService;
import ro.alex.learning.RecipeApplication.services.RecipeService;
import ro.alex.learning.RecipeApplication.services.UnitOfMeasureService;


@Slf4j
@Controller
public class IngredientController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService, UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }


    @GetMapping
    @RequestMapping("/recipe/{recipeId}/ingredients")
    public String listIngredients(@PathVariable String recipeId, Model model){
        log.debug("Getting ingredients for recipe id: " + recipeId);

        model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(recipeId)));

        return "recipe/ingredient/list";
    }

    @GetMapping
    @RequestMapping("/recipe/{recipeId}/ingredient/{ingredientId}/show")
    public String showRecipeIngredient(@PathVariable String recipeId,
                                 @PathVariable String ingredientId,
                                 Model model){
        log.debug("Getting ingredient: " + ingredientId);

        model.addAttribute("ingredient",
                ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(ingredientId)));

        return "recipe/ingredient/show";
    }


    @GetMapping
    @RequestMapping("recipe/{recipeId}/ingredient/new")
    public String newRecipeIngredient(@PathVariable String recipeId, Model model){
        RecipeCommand recipeCommand = recipeService.findCommandById(Long.valueOf(recipeId));

        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setRecipeId(Long.valueOf(recipeId));

        model.addAttribute("ingredient", ingredientCommand);

        ingredientCommand.setUom(new UnitOfMeasureCommand());

        model.addAttribute("uomList", unitOfMeasureService.listAllUoms());
        return "recipe/ingredient/ingredientform";
    }

    @GetMapping
    @RequestMapping("recipe/{recipeId}/ingredient/{ingredientId}/update")
    public String updateRecipeIngredient(@PathVariable String recipeId,
                                         @PathVariable String ingredientId, Model model){
        model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(ingredientId)));

        model.addAttribute("uomList", unitOfMeasureService.listAllUoms());
        return "recipe/ingredient/ingredientform";
    }

    @PutMapping
    @RequestMapping("recipe/{recipeId}/ingredient")
    public String saveOrUpdate(@ModelAttribute IngredientCommand ingredientCommand){
        IngredientCommand savedIngredient = ingredientService.saveIngredientCommand(ingredientCommand);

        log.debug("Saved recipe id: " + savedIngredient.getRecipeId());
        log.debug("Saved ingredient id: " + savedIngredient.getId());
        return "redirect:/recipe/" + savedIngredient.getRecipeId() +"/ingredient/" + savedIngredient.getId() + "/show/";
    }

    @GetMapping
    @RequestMapping("recipe/{recipeId}/ingredient/{ingredientId}/delete")
    public String deleteIngredient(@PathVariable String recipeId, @PathVariable String ingredientId){
        log.debug("Deleting ingredient id:" + ingredientId);

        ingredientService.deleteByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(ingredientId));

        return "redirect:/recipe/" + recipeId + "/ingredients/";
    }
}
