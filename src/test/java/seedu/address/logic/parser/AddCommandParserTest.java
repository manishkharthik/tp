package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;

/**
 * Tests for AddCommandParser with Student-specific fields.
 */
public class AddCommandParserTest {

    private static final AddCommandParser parser = new AddCommandParser();
    private static final String NAME = "John Doe";
    private static final String CLASS = "3B";
    private static final String SUBJECTS = "Math s/Science";
    private static final String EMERGENCY_CONTACT = "91234567";
    private static final String PAYMENT_STATUS = "Paid";
    private static final String ASSIGNMENT_STATUS = "Completed";

    private static final String VALID_FULL_COMMAND =
            NAME + CLASS + SUBJECTS + EMERGENCY_CONTACT + PAYMENT_STATUS + ASSIGNMENT_STATUS;

    @Test
    public void parse_missingCompulsoryFields_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name
        assertParseFailure(parser, "add" + CLASS + SUBJECTS + EMERGENCY_CONTACT, expectedMessage);

        // missing class
        assertParseFailure(parser, "add" + NAME + SUBJECTS + EMERGENCY_CONTACT, expectedMessage);

        // missing subjects
        assertParseFailure(parser, "add" + NAME + CLASS + EMERGENCY_CONTACT, expectedMessage);

        // missing emergency contact
        assertParseFailure(parser, "add" + NAME + CLASS + SUBJECTS, expectedMessage);
    }

    @Test
    public void parse_nonEmptyPreamble_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);
        assertParseFailure(parser,
                "randomtext add" + NAME + CLASS + SUBJECTS + EMERGENCY_CONTACT,
                expectedMessage);
    }
}
