package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteLessonCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.lesson.Lesson;

public class DeleteLessonCommandParserTest {

    private final DeleteLessonCommandParser parser = new DeleteLessonCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteLessonCommand() throws Exception {
        DeleteLessonCommand expected = new DeleteLessonCommand(new Lesson("Algebra", "Mathematics"));
        DeleteLessonCommand result = parser.parse(" s/Mathematics n/Algebra");
        assertEquals(expected, result);
    }

    @Test
    public void parse_missingPrefixes_throwsParseException() {
        try {
            parser.parse(" Algebra");
        } catch (ParseException e) {
            assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteLessonCommand.MESSAGE_USAGE), e.getMessage());
        }
    }
}
