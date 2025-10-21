package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.UnarchiveCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new UnarchiveCommand object
 */
public class UnarchiveCommandParser implements Parser<UnarchiveCommand> {

    private static final Logger logger = LogsCenter.getLogger(UnarchiveCommandParser.class);

    /**
     * Parses the given {@code String} of arguments in the context of the UnarchiveCommand
     * and returns an UnarchiveCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UnarchiveCommand parse(String args) throws ParseException {
        assert args != null : "Arguments should not be null";

        logger.fine("Parsing unarchive command with args: " + args);

        try {
            Index index = ParserUtil.parseIndex(args);
            assert index != null : "Parsed index should not be null";

            logger.fine("Successfully parsed index: " + index.getOneBased());
            return new UnarchiveCommand(index);
        } catch (ParseException pe) {
            logger.warning("Failed to parse unarchive command: " + pe.getMessage());
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnarchiveCommand.MESSAGE_USAGE), pe);
        }
    }
}
