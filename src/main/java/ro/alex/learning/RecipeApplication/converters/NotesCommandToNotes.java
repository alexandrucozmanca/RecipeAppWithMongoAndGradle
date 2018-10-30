package ro.alex.learning.RecipeApplication.converters;

import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import ro.alex.learning.RecipeApplication.command.NotesCommand;
import ro.alex.learning.RecipeApplication.domain.Notes;

@Component
public class NotesCommandToNotes implements Converter<NotesCommand, Notes> {

    @Synchronized
    @Nullable
    @Override
    public Notes convert(NotesCommand notesCommand) {
        if(notesCommand == null)
            return null;

        final Notes notes = new Notes();
        notes.setNotes(notesCommand.getNotes());
        notes.setId(notesCommand.getId());

        return notes;
    }
}
