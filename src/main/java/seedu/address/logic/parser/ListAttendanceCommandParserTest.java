package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ListAttendanceCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class ListAttendanceCommandParserTest {

    private final ListAttendanceCommandParser parser = new ListAttendanceCommandParser();

    @Test
    public void parse_validArgs_returnsListAttendanceCommand() throws Exception {
        // Typical input
        String input = " n/John s/Math";
        ListAttendanceCommand command = parser.parse(input);

        assertEquals(new ListAttendanceCommand("John", "Math"), command);
    }

    @Test
    public void parse_missingStudentPrefix_throwsParseException() {
        // Missing "n/"
        String input = " John s/Math";
        assertThrows(ParseException.class, () -> parser.parse(input));
    }

    @Test
    public void parse_missingSubjectPrefix_throwsParseException() {
        // Missing "s/"
        String input = " n/John Math";
        assertThrows(ParseException.class, () -> parser.parse(input));
    }

    @Test
    public void parse_missingBothPrefixes_throwsParseException() {
        String input = " John Math";
        assertThrows(ParseException.class, () -> parser.parse(input));
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(""));
    }
}
