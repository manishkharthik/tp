package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ATTENDANCE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLASS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMERGENCY_CONTACT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAYMENT_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECTS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.attendance.AttendanceList;
import seedu.address.model.attendance.AttendanceStatus;
import seedu.address.model.person.Name;
import seedu.address.model.student.Student;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object.
 */
public class AddCommandParser implements Parser<AddCommand> {

    @Override
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                args,
                PREFIX_NAME, PREFIX_CLASS, PREFIX_SUBJECTS, PREFIX_EMERGENCY_CONTACT,
                PREFIX_ATTENDANCE, PREFIX_PAYMENT_STATUS, PREFIX_ASSIGNMENT_STATUS, PREFIX_TAG
        );

        // Ensure all required prefixes are present
        if (!arePrefixesPresent(argMultimap,
                PREFIX_NAME, PREFIX_CLASS, PREFIX_SUBJECTS, PREFIX_EMERGENCY_CONTACT)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        // Prevent duplicates (except subjects, which can appear multiple times)
        argMultimap.verifyNoDuplicatePrefixesFor(
                PREFIX_NAME, PREFIX_CLASS, PREFIX_EMERGENCY_CONTACT,
                PREFIX_ATTENDANCE, PREFIX_PAYMENT_STATUS, PREFIX_ASSIGNMENT_STATUS
        );

        // Parse mandatory fields
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME)
                .orElseThrow(() ->
                        new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE))));

        String studentClass = argMultimap.getValue(PREFIX_CLASS)
                .orElseThrow(() ->
                        new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE)));

        List<String> subjects = argMultimap.getAllValues(PREFIX_SUBJECTS);
        String emergencyContact = argMultimap.getValue(PREFIX_EMERGENCY_CONTACT)
                .orElseThrow(() ->
                        new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE)));

        // Parse attendance (optional, defaults to empty list)
        AttendanceList attendanceList = new AttendanceList();
        if (argMultimap.getValue(PREFIX_ATTENDANCE).isPresent()) {
            String val = argMultimap.getValue(PREFIX_ATTENDANCE).get().trim().toUpperCase();
            try {
                AttendanceStatus status = AttendanceStatus.valueOf(val);
                attendanceList.markToday(status);
            } catch (IllegalArgumentException e) {
                throw new ParseException("Invalid attendance status. Use PRESENT or ABSENT.");
            }
        }

        // Parse optional status fields
        String paymentStatus = argMultimap.getValue(PREFIX_PAYMENT_STATUS).orElse("");
        String assignmentStatus = argMultimap.getValue(PREFIX_ASSIGNMENT_STATUS).orElse("");

        // Optional tags (if your Student still supports tags)
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        // Construct and return AddCommand
        Student student = new Student(
                name,
                subjects,
                studentClass,
                emergencyContact,
                attendanceList,
                paymentStatus,
                assignmentStatus
        );

        return new AddCommand(student);
    }

    /**
     * Returns true if all the specified prefixes are present (non-empty) in the ArgumentMultimap.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
