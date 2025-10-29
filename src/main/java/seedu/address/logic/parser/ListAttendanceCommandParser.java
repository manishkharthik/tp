package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECTS;

import seedu.address.logic.commands.ListAttendanceCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;
import seedu.address.model.subject.Subject;

/**
 * Parses input arguments and creates a new {@code ListAttendanceCommand} object.
 * Format: listattendance n/NAME s/SUBJECT
 */
public class ListAttendanceCommandParser implements Parser<ListAttendanceCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ListAttendanceCommand
     * and returns a ListAttendanceCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format.
     */
    @Override
    public ListAttendanceCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_SUBJECTS);

        // Ensure both prefixes are present and no preamble text
        if (!argMultimap.arePrefixesPresent(PREFIX_NAME, PREFIX_SUBJECTS)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, ListAttendanceCommand.MESSAGE_USAGE));
        }

        // Extract values
        String nameString = argMultimap.getValue(PREFIX_NAME).get().trim();
        String subjectString = argMultimap.getValue(PREFIX_SUBJECTS).get().trim();

        // Validate inputs
        if (nameString.isEmpty() || subjectString.isEmpty()) {
            throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, ListAttendanceCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(nameString);
        Subject subject = new Subject(subjectString);

        return new ListAttendanceCommand(name, subject);
    }
}
