package ro.alex.learning.RecipeApplication.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode(exclude = {"recipe"})
@Entity
public class Ingredient implements Comparable<Ingredient>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private BigDecimal amount;

    @OneToOne
    private UnitOfMeasure uom;

    @ManyToOne
    private Recipe recipe;

    public Ingredient() {
    }

    public Ingredient(String description, BigDecimal amount, UnitOfMeasure uom) {
        this.description = description;
        this.amount = amount;
        this.uom = uom;
    }

    public Ingredient(String description, BigDecimal amount, UnitOfMeasure uom, Recipe recipe) {
        this.description = description;
        this.amount = amount;
        this.uom = uom;
        this.recipe = recipe;
    }


    @Override
    public int compareTo(Ingredient other){
        if(other == null || other.getId() == null)
            return 1;

        if (this == null || this.getId() == null)
            return -1;

        return Long.compare(this.getId(), other.getId());
    }

}
