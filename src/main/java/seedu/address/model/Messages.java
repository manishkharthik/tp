package seedu.address.model;

import java.util.Arrays;
import java.util.stream.Collectors;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.student.Student;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_DUPLICATE_FIELDS = "Multiple instances of the same field found: ";
    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";

    /**
     * Returns an error message for duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... prefixes) {
        String duplicates = Arrays.stream(prefixes)
                .map(Prefix::toString)
                .collect(Collectors.joining(" "));
        return MESSAGE_DUPLICATE_FIELDS + duplicates;
    }

    /**
     * Returns a formatted string for displaying a student's information.
     */
    public static String format(Student student) {
        StringBuilder sb = new StringBuilder();
        sb.append(student.getName()).append("\n")
                .append("Class: ").append(student.getStudentClass()).append("\n")
                .append("Subjects: ")
                .append(student.getSubjects().stream()
                        .map(s -> s.getName().toString())
                        .collect(Collectors.joining(", ")))
                .append("\n")
                .append("Emergency Contact: ").append(student.getEmergencyContact()).append("\n")
                .append("Payment Status: ").append(student.getPaymentStatus()).append("\n")
                .append("Assignment Status: ").append(student.getAssignmentStatus());
        return sb.toString();
    }
}
