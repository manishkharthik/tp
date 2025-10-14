package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ArchiveCommand;

/**
 * Contains unit tests for {@code ArchiveCommandParser}.
 */
public class ArchiveCommandParserTest {

    private ArchiveCommandParser parser = new ArchiveCommandParser();

    @Test
    public void parse_validArgs_returnsArchiveCommand() {
        assertParseSuccess(parser, "1", new ArchiveCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ArchiveCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ArchiveCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_whitespaceArgs_throwsParseException() {
        assertParseFailure(parser, "   ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ArchiveCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_negativeIndex_throwsParseException() {
        assertParseFailure(parser, "-1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ArchiveCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_zeroIndex_throwsParseException() {
        assertParseFailure(parser, "0", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ArchiveCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_multipleArguments_throwsParseException() {
        assertParseFailure(parser, "1 2", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ArchiveCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidFormat_throwsParseException() {
        assertParseFailure(parser, "1a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ArchiveCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_leadingWhitespace_success() {
        assertParseSuccess(parser, "   1", new ArchiveCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_trailingWhitespace_success() {
        assertParseSuccess(parser, "1   ", new ArchiveCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_leadingAndTrailingWhitespace_success() {
        assertParseSuccess(parser, "   1   ", new ArchiveCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_largeValidIndex_success() {
        assertParseSuccess(parser, "999999", new ArchiveCommand(Index.fromOneBased(999999)));
    }

    @Test
    public void parse_specialCharacters_throwsParseException() {
        assertParseFailure(parser, "@1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ArchiveCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_decimal_throwsParseException() {
        assertParseFailure(parser, "1.5", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ArchiveCommand.MESSAGE_USAGE));
    }
}
