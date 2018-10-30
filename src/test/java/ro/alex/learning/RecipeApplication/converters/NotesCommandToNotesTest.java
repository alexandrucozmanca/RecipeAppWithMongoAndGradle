package ro.alex.learning.RecipeApplication.converters;

import org.junit.Before;
import org.junit.Test;
import ro.alex.learning.RecipeApplication.command.NotesCommand;
import ro.alex.learning.RecipeApplication.domain.Notes;

import static org.junit.Assert.*;

public class NotesCommandToNotesTest {

    public static final String NOTES = "notes";
    public static final Long LONG_VALUE = new Long(1L);

    NotesCommandToNotes converter;

    @Before
    public void setUp() {
        converter = new NotesCommandToNotes();
    }

    @Test
    public void testNullParameter() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(new NotesCommand()));
    }

    @Test
    public void testConvert() {
        //given
        NotesCommand command = new NotesCommand();
        command.setId(LONG_VALUE);
        command.setNotes(NOTES);


        //when
        Notes notes = converter.convert(command);

        // then
        assertNotNull(notes);
        assertEquals(LONG_VALUE, notes.getId());
        assertEquals(NOTES, notes.getNotes());
    }
}