package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.MarkAttendanceCommand;
import seedu.address.model.attendance.AttendanceStatus;

/**
 * Unit tests for {@link MarkAttendanceCommandParser}.
 */
public class MarkAttendanceCommandParserTest {

    private final MarkAttendanceCommandParser parser = new MarkAttendanceCommandParser();

    @Test
    public void parse_validArgs_returnsMarkAttendanceCommand() {
        // Example input: "1 s/Math l/L1 a/PRESENT"
        String userInput = "1 s/Math l/L1 a/PRESENT";
        MarkAttendanceCommand expectedCommand =
                new MarkAttendanceCommand(INDEX_FIRST_PERSON, "Math", "L1", AttendanceStatus.PRESENT);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingLesson_throwsParseException() {
        // Missing lesson prefix (l/)
        String userInput = "1 s/Math a/PRESENT";
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkAttendanceCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingSubject_throwsParseException() {
        // Missing subject prefix (s/)
        String userInput = "1 l/L1 a/PRESENT";
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkAttendanceCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingStatus_throwsParseException() {
        // Missing attendance prefix (a/)
        String userInput = "1 s/Math l/L1";
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkAttendanceCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        String userInput = "x s/Math l/L1 a/PRESENT";
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkAttendanceCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidStatus_throwsParseException() {
        String userInput = "1 s/Math l/L1 a/INVALIDSTATUS";
        assertParseFailure(parser, userInput,
                "Invalid attendance status. Must be one of: PRESENT, ABSENT, LATE, EXCUSED");
    }

    @Test
    public void parse_extraArguments_stillParsesCorrectly() {
        String userInput = "1 s/Math l/L1 a/ABSENT extra";
        MarkAttendanceCommand expectedCommand =
                new MarkAttendanceCommand(INDEX_FIRST_PERSON, "Math", "L1", AttendanceStatus.ABSENT);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
