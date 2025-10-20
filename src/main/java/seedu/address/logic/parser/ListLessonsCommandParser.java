package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECTS;

import seedu.address.logic.commands.ListLessonsCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ListLessonsCommand object
 */
public class ListLessonsCommandParser implements Parser<ListLessonsCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ListLessonsCommand
     * and returns a ListLessonsCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListLessonsCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_SUBJECTS);

        if (!argMultimap.arePrefixesPresent(PREFIX_SUBJECTS)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ListLessonsCommand.MESSAGE_USAGE));
        }

        String subject = argMultimap.getValue(PREFIX_SUBJECTS).get().trim();

        if (subject.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ListLessonsCommand.MESSAGE_USAGE));
        }

        return new ListLessonsCommand(subject);
    }
}
