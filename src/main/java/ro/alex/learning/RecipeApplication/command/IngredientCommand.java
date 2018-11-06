package ro.alex.learning.RecipeApplication.command;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class IngredientCommand implements Comparable<IngredientCommand>{
    private String id;
    private String recipeId;

    @NotBlank
    private String description;

    @NotNull
    @Min(1)
    private BigDecimal amount;

    @NotNull
    private UnitOfMeasureCommand uom;

    @Override
    public int compareTo(IngredientCommand other) {
        if(other == null || other.getId() == null)
            return 1;

        if(this == null || this.getId() == null)
            return -1;

        return this.getId().compareTo(other.getId());
    }
}
