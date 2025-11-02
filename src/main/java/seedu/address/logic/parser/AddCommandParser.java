package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLASS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMERGENCY_CONTACT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAYMENT_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECTS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;
import seedu.address.model.student.Student;
import seedu.address.model.subject.Subject;
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
                PREFIX_PAYMENT_STATUS, PREFIX_ASSIGNMENT_STATUS, PREFIX_TAG
        );

        // Ensure required prefixes are present
        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_CLASS, PREFIX_SUBJECTS, PREFIX_EMERGENCY_CONTACT)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        // Disallow duplicates for single-value prefixes (attendance removed)
        argMultimap.verifyNoDuplicatePrefixesFor(
                PREFIX_NAME, PREFIX_CLASS, PREFIX_EMERGENCY_CONTACT,
                PREFIX_PAYMENT_STATUS, PREFIX_ASSIGNMENT_STATUS
        );

        // Parse mandatory fields
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).orElseThrow(() ->
                new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE))));

        String studentClass = argMultimap.getValue(PREFIX_CLASS).orElseThrow(() ->
                new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE)));

        List<String> subjects = argMultimap.getAllValues(PREFIX_SUBJECTS);
        List<Subject> subjectList = new ArrayList<>();

        if (!subjects.isEmpty() && (!(subjects.size() == 1) || !subjects.get(0).trim().isEmpty())) {
            subjectList = ParserUtil.parseSubjects(subjects);
        }

        String emergencyContact = ParserUtil.parseEmergencyContact(
                argMultimap.getValue(PREFIX_EMERGENCY_CONTACT).orElseThrow(() ->
                        new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE))));

        // Optional status fields
        String paymentStatus = argMultimap.getValue(PREFIX_PAYMENT_STATUS).isPresent()
                ? ParserUtil.parsePaymentStatus(argMultimap.getValue(PREFIX_PAYMENT_STATUS).get())
                : "Unpaid";

        String assignmentStatus = argMultimap.getValue(PREFIX_ASSIGNMENT_STATUS).isPresent()
                ? ParserUtil.parseAssignmentStatus(argMultimap.getValue(PREFIX_ASSIGNMENT_STATUS).get())
                : "Uncompleted";

        // Optional tags (if still supported)
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        // Construct Student (AttendanceList is created internally by Student)
        Student student = new Student(
                name,
                subjectList,
                studentClass,
                emergencyContact,
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
