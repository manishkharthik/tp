package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ListArchiveCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parse the ListArchive command.
 */
public class ListArchiveCommandParser implements Parser<ListArchiveCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ListArchiveCommand
     * and returns a ListArchiveCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    @Override
    public ListArchiveCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        if (!trimmedArgs.isEmpty()) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ListArchiveCommand.MESSAGE_USAGE));
        }

        return new ListArchiveCommand();
    }
}
