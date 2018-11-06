package ro.alex.learning.RecipeApplication.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Document
public class Recipe implements Comparable<Recipe>{

    @Id
    private String id;
    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;
    private String directions;
    private Difficulty difficulty;
    private Set<Category> categories = new HashSet<>();
    private  Set<Ingredient> ingredients = new HashSet<>();
    private Byte[] image;
    private Notes notes;

    public Recipe(){

    }

//    public Recipe(Recipe recipe) {
//        this.setDifficulty(recipe.getDifficulty());
//        this.setPrepTime(recipe.getPrepTime());
//        this.setCookTime(recipe.getCookTime());
//        this.setDescription(recipe.getDescription());
//        this.setNotes(recipe.getNotes());
//        this.setDirections(recipe.getDirections());
//        this.setUrl(recipe.getUrl());
//        this.setSource(recipe.getSource());
//        this.setServings(recipe.getServings());
//        this.setIngredients(recipe.getIngredients());
//        this.setImage(recipe.getImage());
//        this.setCategories(recipe.getCategories());
//    }

    public void setNotes(Notes notes) {

        if(notes != null) {
            this.notes = notes;
        }
    }



    public Recipe addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
        return this;
    }

    @Override
    public int compareTo(Recipe other) {
        if(other == null || other.getId() == null)
            return 1;

        if (this == null || this.getId() == null)
            return -1;

        return this.getId().compareTo(other.getId());
    }
}

