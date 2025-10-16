package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ListArchiveCommand;

/**
 * Contains unit tests for ListArchiveCommandParser.
 */
public class ListArchiveCommandParserTest {

    private ListArchiveCommandParser parser = new ListArchiveCommandParser();

    @Test
    public void parse_emptyArg_returnsListArchiveCommand() {
        assertParseSuccess(parser, "", new ListArchiveCommand());
    }

    @Test
    public void parse_whitespaceArg_returnsListArchiveCommand() {
        assertParseSuccess(parser, "     ", new ListArchiveCommand());
    }

    @Test
    public void parse_tabsAndNewlines_returnsListArchiveCommand() {
        assertParseSuccess(parser, "\t\n  \t", new ListArchiveCommand());
    }

    @Test
    public void parse_noArguments_returnsListArchiveCommand() {
        // Test that parser creates command successfully with no args
        ListArchiveCommand expectedCommand = new ListArchiveCommand();
        assertParseSuccess(parser, "", expectedCommand);
    }

    @Test
    public void parse_multipleWhitespaces_returnsListArchiveCommand() {
        // Multiple spaces should still work
        assertParseSuccess(parser, "        ", new ListArchiveCommand());
    }
}
