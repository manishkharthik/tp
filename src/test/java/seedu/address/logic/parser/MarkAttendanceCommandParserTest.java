package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.MarkAttendanceCommand;
import seedu.address.model.attendance.AttendanceStatus;
import seedu.address.model.person.Name;

/**
 * Unit tests for {@link MarkAttendanceCommandParser}.
 */
public class MarkAttendanceCommandParserTest {

    private final MarkAttendanceCommandParser parser = new MarkAttendanceCommandParser();

    @Test
    public void parse_validArgs_returnsMarkAttendanceCommand() {
        String userInput = " n/John Tan s/Math l/L1 st/PRESENT ";
        MarkAttendanceCommand expected =
                new MarkAttendanceCommand(new Name("John Tan"), "Math", "L1", AttendanceStatus.PRESENT);

        assertParseSuccess(parser, userInput, expected);
    }

    @Test
    public void parse_missingLesson_throwsParseException() {
        String userInput = "n/John Tan s/Math st/PRESENT";
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkAttendanceCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingSubject_throwsParseException() {
        String userInput = "n/John Tan l/L1 st/PRESENT";
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkAttendanceCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingStatus_throwsParseException() {
        String userInput = "n/John Tan s/Math l/L1";
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkAttendanceCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingName_throwsParseException() {
        String userInput = "s/Math l/L1 st/PRESENT";
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkAttendanceCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidStatus_throwsParseException() {
        String userInput = "n/John Tan s/Math l/L1 st/INVALID";
        assertParseFailure(parser, userInput,
                "Invalid status. Use PRESENT, ABSENT, LATE, or EXCUSED.");
    }

    @Test
    public void parse_nonEmptyPreamble_throwsParseException() {
        String userInput = "preamble n/John Tan s/Math l/L1 st/PRESENT";
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkAttendanceCommand.MESSAGE_USAGE));
    }
}
