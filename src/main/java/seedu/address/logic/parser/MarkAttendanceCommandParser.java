package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LESSON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECTS;

import java.util.stream.Stream;

import seedu.address.logic.commands.MarkAttendanceCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.attendance.AttendanceStatus;
import seedu.address.model.person.Name;

/**
 * Parses input arguments and creates a new {@code MarkAttendanceCommand} object.
 * Format: markattendance n/NAME s/SUBJECT l/LESSON st/STATUS
 */
public class MarkAttendanceCommandParser implements Parser<MarkAttendanceCommand> {

    @Override
    public MarkAttendanceCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                args,
                PREFIX_NAME, PREFIX_SUBJECTS, PREFIX_LESSON, PREFIX_STATUS
        );

        // Require all prefixes, no preamble allowed (same style as AddCommandParser)
        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_SUBJECTS, PREFIX_LESSON, PREFIX_STATUS)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, MarkAttendanceCommand.MESSAGE_USAGE));
        }

        // Disallow duplicates for single-value prefixes
        argMultimap.verifyNoDuplicatePrefixesFor(
                PREFIX_NAME, PREFIX_SUBJECTS, PREFIX_LESSON, PREFIX_STATUS
        );

        // Parse required fields
        Name name = ParserUtil.parseName(
                argMultimap.getValue(PREFIX_NAME).orElseThrow(() ->
                        new ParseException(String.format(
                                MESSAGE_INVALID_COMMAND_FORMAT, MarkAttendanceCommand.MESSAGE_USAGE))));

        String subject = argMultimap.getValue(PREFIX_SUBJECTS).orElseThrow(() ->
                new ParseException(String.format(
                        MESSAGE_INVALID_COMMAND_FORMAT, MarkAttendanceCommand.MESSAGE_USAGE))).trim();

        String lessonName = argMultimap.getValue(PREFIX_LESSON).orElseThrow(() ->
                new ParseException(String.format(
                        MESSAGE_INVALID_COMMAND_FORMAT, MarkAttendanceCommand.MESSAGE_USAGE))).trim();

        String rawStatus = argMultimap.getValue(PREFIX_STATUS).orElseThrow(() ->
                new ParseException(String.format(
                        MESSAGE_INVALID_COMMAND_FORMAT, MarkAttendanceCommand.MESSAGE_USAGE))).trim().toUpperCase();

        final AttendanceStatus status;
        try {
            status = AttendanceStatus.valueOf(rawStatus);
        } catch (IllegalArgumentException ex) {
            throw new ParseException("Invalid status. Use PRESENT, ABSENT, LATE, or EXCUSED.");
        }

        return new MarkAttendanceCommand(name, subject, lessonName, status);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap map, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(p -> map.getValue(p).isPresent());
    }
}
