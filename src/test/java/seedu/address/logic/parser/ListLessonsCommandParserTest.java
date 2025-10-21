package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECTS;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ListLessonsCommand;

public class ListLessonsCommandParserTest {

    private ListLessonsCommandParser parser = new ListLessonsCommandParser();

    @Test
    public void parse_validArgs_returnsListLessonsCommand() {
        // no leading and trailing whitespaces
        assertParseSuccess(parser, " " + PREFIX_SUBJECTS + "Math",
                new ListLessonsCommand("Math"));

        // with leading and trailing whitespaces
        assertParseSuccess(parser, "  " + PREFIX_SUBJECTS + "Math  ",
                new ListLessonsCommand("Math"));
    }

    @Test
    public void parse_missingSubjectPrefix_throwsParseException() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListLessonsCommand.MESSAGE_USAGE);

        // no prefix
        assertParseFailure(parser, "Math", expectedMessage);
    }

    @Test
    public void parse_emptySubject_throwsParseException() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListLessonsCommand.MESSAGE_USAGE);

        // empty subject
        assertParseFailure(parser, " " + PREFIX_SUBJECTS + "", expectedMessage);

        // whitespace only
        assertParseFailure(parser, " " + PREFIX_SUBJECTS + "   ", expectedMessage);
    }
}
