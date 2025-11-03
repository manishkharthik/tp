package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLASS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMERGENCY_CONTACT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAYMENT_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECTS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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

    /**
     * Set of allowed prefix strings for the AddCommand.
     */
    private static final Set<String> ALLOWED_PREFIX_STRS = Set.of(
        PREFIX_NAME.getPrefix(),
        PREFIX_CLASS.getPrefix(),
        PREFIX_SUBJECTS.getPrefix(),
        PREFIX_EMERGENCY_CONTACT.getPrefix(),
        PREFIX_PAYMENT_STATUS.getPrefix(),
        PREFIX_ASSIGNMENT_STATUS.getPrefix(),
        PREFIX_TAG.getPrefix()
    );

    @Override
    public AddCommand parse(String args) throws ParseException {
        String processedArgs = args;
        String nameValue = null;
        if (args.contains("n/\"")) {
            int nameStart = args.indexOf("n/\"");
            int quoteStart = nameStart + 3;
            int quoteEnd = args.indexOf("\"", quoteStart);
            if (quoteEnd == -1) {
                throw new ParseException("Missing closing quote for name.");
            }
            nameValue = args.substring(quoteStart, quoteEnd);
            processedArgs = args.substring(0, nameStart) + "n/QUOTEDNAME" + args.substring(quoteEnd + 1);
        } else if (args.contains("n/")) {
            throw new ParseException("Name must be enclosed in quotes. Example: add n/\"John Tan\"");
        }
        assertNoUnknownPrefixes(processedArgs);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                processedArgs,
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

        Name name;
        if (nameValue != null) {
            name = ParserUtil.parseName(nameValue);
        } else {
            name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).orElseThrow(() ->
                    new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE))));
        }

        String studentClass = requireNonBlank(argMultimap, PREFIX_CLASS, "Class");

        List<String> subjects = argMultimap.getAllValues(PREFIX_SUBJECTS);
        List<Subject> subjectList;
        if (!subjects.isEmpty() && subjects.size() == 1 && subjects.get(0).trim().isEmpty()) {
            throw new ParseException(Subject.MESSAGE_CONSTRAINTS);
        }
        subjectList = ParserUtil.parseSubjects(subjects);
        if (subjectList.isEmpty()) {
            throw new ParseException(Subject.MESSAGE_CONSTRAINTS);
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
                : "Incomplete";

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

    /**
     * Retrieves the value associated with the given prefix from the ArgumentMultimap.
     * Throws a ParseException if the value is missing or blank.
     */
    private static String requireNonBlank(ArgumentMultimap m, Prefix p, String fieldName) throws ParseException {
        String value = m.getValue(p).orElseThrow(() ->
                new ParseException(String.format("Missing required field: %s.", fieldName)));

        if (value.trim().isEmpty()) {
            throw new ParseException(
                String.format("The field '%s' cannot be empty.", fieldName, p.getPrefix()));
        }

        return value.trim();
    }

    /**
     * Scans the input arguments for unknown prefixes and throws a ParseException if any are found.
     */
    private static void assertNoUnknownPrefixes(String args) throws ParseException {
        Pattern p = Pattern.compile("(?<!\\S)([A-Za-z]+)/");
        Matcher m = p.matcher(args);

        while (m.find()) {
            String seen = m.group(1) + "/";

            if (!ALLOWED_PREFIX_STRS.contains(seen)) {
                throw new ParseException("Unknown parameter: " + seen);
            }
        }
    }
}
