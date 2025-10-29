package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ListAttendanceCommand;
import seedu.address.model.person.Name;
import seedu.address.model.subject.Subject;

/**
 * Unit tests for {@link ListAttendanceCommandParser}.
 */
public class ListAttendanceCommandParserTest {

    private final ListAttendanceCommandParser parser = new ListAttendanceCommandParser();

    @Test
    public void parse_validArgs_success() {
        String input = " n/John Tan s/Math ";
        ListAttendanceCommand expected =
                new ListAttendanceCommand(new Name("John Tan"), new Subject("Math"));
        assertParseSuccess(parser, input, expected);
    }

    @Test
    public void parse_missingName_failure() {
        String input = " s/Math ";
        assertParseFailure(parser, input,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListAttendanceCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingSubject_failure() {
        String input = " n/John Tan ";
        assertParseFailure(parser, input,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListAttendanceCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_nonEmptyPreamble_failure() {
        String input = " blah n/John Tan s/Math ";
        assertParseFailure(parser, input,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListAttendanceCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_duplicatePrefixes_failure() {
        String input = " n/John Tan n/Jane s/Math ";
        assertParseFailure(parser, input,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListAttendanceCommand.MESSAGE_USAGE));
    }
}
