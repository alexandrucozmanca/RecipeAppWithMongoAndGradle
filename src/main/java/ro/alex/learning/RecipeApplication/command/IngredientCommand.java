package ro.alex.learning.RecipeApplication.command;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class IngredientCommand implements Comparable<IngredientCommand>{
    private Long id;
    private Long recipeId;
    private String description;
    private BigDecimal amount;
    private UnitOfMeasureCommand uom;

    @Override
    public int compareTo(IngredientCommand other) {
        if(other == null || other.getId() == null)
            return 1;

        if(this == null || this.getId() == null)
            return -1;

        return Long.compare(this.getId(), other.getId());
    }
}
