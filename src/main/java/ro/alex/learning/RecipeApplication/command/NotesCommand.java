package ro.alex.learning.RecipeApplication.command;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class NotesCommand {
    private String id;
    private String notes;
}
