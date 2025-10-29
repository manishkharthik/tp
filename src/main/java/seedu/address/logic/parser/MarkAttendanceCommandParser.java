package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LESSON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECTS;

import java.util.stream.Stream;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.MarkAttendanceCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.attendance.AttendanceStatus;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.person.Name;
import seedu.address.model.subject.Subject;

/**
 * Parses input arguments and creates a new {@code MarkAttendanceCommand} object.
 * Format: markattendance n/NAME s/SUBJECT l/LESSON st/STATUS
 */
public class MarkAttendanceCommandParser implements Parser<MarkAttendanceCommand> {

    @Override
    public MarkAttendanceCommand parse(String args) throws ParseException {
        ArgumentMultimap m = ArgumentTokenizer.tokenize(
                args, PREFIX_NAME, PREFIX_SUBJECTS, PREFIX_LESSON, PREFIX_STATUS);

        if (!arePrefixesPresent(m, PREFIX_NAME, PREFIX_SUBJECTS, PREFIX_LESSON, PREFIX_STATUS)
                || !m.getPreamble().isEmpty()) {
            throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, MarkAttendanceCommand.MESSAGE_USAGE));
        }

        m.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_SUBJECTS, PREFIX_LESSON, PREFIX_STATUS);

        Name name = ParserUtil.parseName(m.getValue(PREFIX_NAME).get().trim());
        Subject subject = new Subject(m.getValue(PREFIX_SUBJECTS).get().trim());

        String lessonName = m.getValue(PREFIX_LESSON).get().trim();
        Lesson lesson = new Lesson(lessonName, subject.getName());

        String rawStatus = m.getValue(PREFIX_STATUS).get().trim().toUpperCase();
        final AttendanceStatus status;
        try {
            status = AttendanceStatus.valueOf(rawStatus);
        } catch (IllegalArgumentException ex) {
            throw new ParseException(Messages.MESSAGE_INVALID_STATUS);
        }

        return new MarkAttendanceCommand(name, subject, lesson, status);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap map, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(p -> map.getValue(p).isPresent());
    }
}
