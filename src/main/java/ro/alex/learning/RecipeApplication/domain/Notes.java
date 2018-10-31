package ro.alex.learning.RecipeApplication.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(exclude = {"recipe"})
public class Notes {

    private String id;
    private String notes;
    private Recipe recipe;

}
