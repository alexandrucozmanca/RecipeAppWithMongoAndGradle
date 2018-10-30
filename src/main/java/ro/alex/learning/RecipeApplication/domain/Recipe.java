package ro.alex.learning.RecipeApplication.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

@Getter
@Setter
@Entity
public class Recipe implements Comparable<Recipe>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;

    @Lob
    private String directions;

    @Enumerated(value = EnumType.STRING)
    private Difficulty difficulty;

    @ManyToMany
    @JoinTable(name = "recipe_category",
        joinColumns = @JoinColumn(name = "recipe_id"),
        inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe", orphanRemoval = true)
    private Set<Ingredient> ingredients = new TreeSet<>();

    @Lob
    private Byte[] image;

    @OneToOne(cascade = CascadeType.ALL)
    private Notes notes;

    public Recipe(){

    }

    public Recipe(Recipe recipe) {
        this.setDifficulty(recipe.getDifficulty());
        this.setPrepTime(recipe.getPrepTime());
        this.setCookTime(recipe.getCookTime());
        this.setDescription(recipe.getDescription());
        this.setNotes(recipe.getNotes());
        this.setDirections(recipe.getDirections());
        this.setUrl(recipe.getUrl());
        this.setSource(recipe.getSource());
        this.setServings(recipe.getServings());
        this.setIngredients(recipe.getIngredients());
        this.setImage(recipe.getImage());
        this.setCategories(recipe.getCategories());
    }

    public void setNotes(Notes notes) {

        if(notes != null) {
            this.notes = notes;
            notes.setRecipe(this);
        }
    }



    public Recipe addIngredient(Ingredient ingredient) {
        ingredient.setRecipe(this);
        this.ingredients.add(ingredient);
        return this;
    }

    @Override
    public int compareTo(Recipe other) {
        if(other == null || other.getId() == null)
            return 1;

        if (this == null || this.getId() == null)
            return -1;

        return Long.compare(this.getId(), other.getId());
    }
}

