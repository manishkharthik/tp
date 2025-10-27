package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECTS;

import seedu.address.logic.commands.ListAttendanceCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ListAttendanceCommand object.
 * Example command: listAttendance n/John s/Math
 */
public class ListAttendanceCommandParser implements Parser<ListAttendanceCommand> {

    @Override
    public ListAttendanceCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_SUBJECTS);

        // Check both prefixes are present
        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_SUBJECTS)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format("Invalid command format!\n%1$s",
                    ListAttendanceCommand.MESSAGE_USAGE));
        }

        String studentName = argMultimap.getValue(PREFIX_NAME)
                .orElseThrow(() -> new ParseException("Missing student name!")).trim();

        String subjectName = argMultimap.getValue(PREFIX_SUBJECTS)
                .orElseThrow(() -> new ParseException("Missing subject name!")).trim();

        return new ListAttendanceCommand(studentName, subjectName);
    }
}
