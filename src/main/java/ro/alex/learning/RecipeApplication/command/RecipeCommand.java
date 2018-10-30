package ro.alex.learning.RecipeApplication.command;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;
import ro.alex.learning.RecipeApplication.domain.Difficulty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

@Getter
@Setter
@NoArgsConstructor
public class RecipeCommand implements Comparable<RecipeCommand>{
    private Long id;

    @NotBlank
    @Size(min = 3, max = 255)
    private String description;

    @Min(1)
    @Max(999)
    private Integer prepTime;

    @Min(1)
    @Max(999)
    private Integer cookTime;

    @Min(1)
    @Max(100)
    private Integer servings;
    private String source;

    @URL
    private String url;

    @NotBlank
    private String directions;
    private Difficulty difficulty;
    private NotesCommand notes;
    private Set<CategoryCommand> categories = new HashSet<>();
    private Set<IngredientCommand> ingredients = new TreeSet<>();
    private Byte[] image;



    @Override
    public int compareTo(RecipeCommand other) {
        if(other == null || other.getId() == null)
            return 1;

        if (this == null || this.getId() == null)
            return -1;

        return Long.compare(this.getId(), other.getId());
    }
}
