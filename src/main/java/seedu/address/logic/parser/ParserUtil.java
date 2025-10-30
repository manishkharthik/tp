package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// Note: removed java.time.LocalDateTime
// Note: removed AttendanceList import
// Keep AttendanceStatus only if you add parseAttendanceStatus below
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.attendance.AttendanceStatus; // keep if used
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.subject.Subject;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser
 * classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading
     * and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the specified index is invalid (not non-zero
     *                        unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /** Parses comma-separated subjects into {@code List<String>}. */
    public static List<Subject> parseSubjects(String csvSubjects) throws ParseException {
        requireNonNull(csvSubjects);
        String trimmed = csvSubjects.trim();
        if (trimmed.isEmpty()) {
            return new ArrayList<>();
        }
        String[] parts = trimmed.split(",");
        List<Subject> subjects = new ArrayList<>();
        for (String part : parts) {
            String s = part.trim();
            if (!s.isEmpty()) {
                subjects.add(new Subject(s));
            }
        }
        return subjects;
    }

    /**
     * Returns true if all of the specified prefixes contain non-empty values in the given ArgumentMultimap.
     */
    public static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Arrays.stream(prefixes).allMatch(p -> argumentMultimap.getValue(p).isPresent());
    }

    /** Parses class string (non-empty). */
    public static String parseStudentClass(String studentClass) throws ParseException {
        requireNonNull(studentClass);
        String trimmed = studentClass.trim();
        if (trimmed.isEmpty()) {
            throw new ParseException("Class cannot be empty.");
        }
        return trimmed;
    }

    /** Parses emergency contact, basic digit check. */
    public static String parseEmergencyContact(String contact) throws ParseException {
        requireNonNull(contact);
        String trimmed = contact.trim();
        // Tighten to match Student's assertion
        if (!trimmed.matches("\\d{8}")) {
            throw new ParseException("Emergency contact must be exactly 8 digits.");
        }
        return trimmed;
    }

    // === New small helpers for markattendance ===

    /** Non-empty lesson name. */
    public static String parseLessonName(String name) throws ParseException {
        requireNonNull(name);
        String trimmed = name.trim();
        if (trimmed.isEmpty()) {
            throw new ParseException("Lesson name cannot be empty.");
        }
        return trimmed;
    }

    /** Non-empty subject string (used by markattendance; not the CSV parser). */
    public static String parseSingleSubject(String subject) throws ParseException {
        requireNonNull(subject);
        String trimmed = subject.trim();
        if (trimmed.isEmpty()) {
            throw new ParseException("Subject cannot be empty.");
        }
        return trimmed;
    }

    /** Maps free-form string to AttendanceStatus enum. */
    public static AttendanceStatus parseAttendanceStatus(String status) throws ParseException {
        requireNonNull(status);
        String upper = status.trim().toUpperCase();
        try {
            return AttendanceStatus.valueOf(upper);
        } catch (IllegalArgumentException e) {
            throw new ParseException("Invalid attendance status. Use PRESENT, ABSENT, LATE, or EXCUSED.");
        }
    }

    /**
     * Parses a {@code String paymentStatus} into a valid payment status.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code paymentStatus} is invalid.
     */
    public static String parsePaymentStatus(String paymentStatus) throws ParseException {
        requireNonNull(paymentStatus);
        String trimmed = paymentStatus.trim();

        if (!trimmed.equalsIgnoreCase("Paid") && !trimmed.equalsIgnoreCase("Unpaid")) {
            throw new ParseException("Payment status must be either 'Paid' or 'Unpaid'");
        }

        return trimmed.substring(0, 1).toUpperCase() + trimmed.substring(1).toLowerCase();
    }

    /**
     * Parses a {@code String assignmentStatus} into a valid assignment status.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code assignmentStatus} is invalid.
     */
    public static String parseAssignmentStatus(String assignmentStatus) throws ParseException {
        requireNonNull(assignmentStatus);
        String trimmed = assignmentStatus.trim();

        if (!trimmed.equalsIgnoreCase("Completed") && !trimmed.equalsIgnoreCase("Uncompleted")) {
            throw new ParseException("Assignment status must be either 'Completed' or 'Uncompleted'");
        }

        return trimmed.substring(0, 1).toUpperCase() + trimmed.substring(1).toLowerCase();
    }

    /** Parses optional free-form status. */
    public static String parseOptionalStatus(String status) throws ParseException {
        requireNonNull(status);
        return status.trim();
    }
}
