package ro.alex.learning.RecipeApplication.converters;

import org.junit.Before;
import org.junit.Test;
import ro.alex.learning.RecipeApplication.command.NotesCommand;
import ro.alex.learning.RecipeApplication.domain.Notes;

import static org.junit.Assert.*;

public class NotesToNotesCommandTest {


    public static final String NOTES = "notes";
    public static final String ID_VALUE = new String("1");

    NotesToNotesCommand converter;

    @Before
    public void setUp() {
        converter = new NotesToNotesCommand();
    }

    @Test
    public void testNullParameter() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(new Notes()));
    }

    @Test
    public void testConvert() {
        //given
        Notes notes = new Notes();
        notes.setId(ID_VALUE);
        notes.setNotes(NOTES);


        //when
        NotesCommand command = converter.convert(notes);

        // then
        assertNotNull(command);
        assertEquals(ID_VALUE, command.getId());
        assertEquals(NOTES, command.getNotes());
    }
}