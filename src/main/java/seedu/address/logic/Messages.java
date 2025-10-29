package seedu.address.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.person.Person;
import seedu.address.model.student.Student;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
        "Multiple values specified for the following single-valued field(s): ";

    public static final String MESSAGE_SUCCESS = "Marked attendance for %1$s: [%2$s - %3$s] → %4$s";
    public static final String MESSAGE_STUDENT_NOT_FOUND = "No student found with name: %s";
    public static final String MESSAGE_PERSON_NOT_STUDENT = "%s is not a student or has not been added as a student.";
    public static final String MESSAGE_SUBJECT_NOT_ENROLLED = "%s does not read subject: %s";
    public static final String MESSAGE_SUBJECT_NOT_FOUND = "Subject '%s' not found.";
    public static final String MESSAGE_LESSON_NOT_FOUND =
        "Lesson '%1$s' does not exist in subject '%2$s'. Please add it first using addlesson.";
    public static final String MESSAGE_INVALID_STATUS = "Invalid status. Use PRESENT, ABSENT, LATE, or EXCUSED.";

    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields = Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Formats the {@code person} for display to the user.
     */
    public static String format(Person person) {
        final StringBuilder builder = new StringBuilder();

        if (person instanceof Student) {
            Student student = (Student) person;
            builder.append(student.getName())
                    .append("; Class: ")
                    .append(student.getStudentClass())
                    .append("; Subjects: ")
                    .append(student.getSubjectNames())
                    .append("; Emergency Contact: ")
                    .append(student.getEmergencyContact())
                    .append("; Payment Status: ")
                    .append(student.getPaymentStatus())
                    .append("; Assignment Status: ")
                    .append(student.getAssignmentStatus());
        } else {
            builder.append(person.getName())
                    .append("; Phone: ")
                    .append(person.getPhone())
                    .append("; Email: ")
                    .append(person.getEmail())
                    .append("; Address: ")
                    .append(person.getAddress())
                    .append("; Tags: ");
            person.getTags().forEach(builder::append);
        }

        return builder.toString();
    }
}
