package seedu.address.logic.parser;

import seedu.address.logic.commands.ListArchiveCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parse the ListArchive command.
 */
public class ListArchiveCommandParser implements Parser<ListArchiveCommand> {
    @Override
    public ListArchiveCommand parse(String args) throws ParseException {
        return new ListArchiveCommand();
    }
}
