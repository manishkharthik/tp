package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLASS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMERGENCY_CONTACT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAYMENT_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECTS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

//import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
//import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
//import seedu.address.model.subject.Subject;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditCommand object.
 */
public class EditCommandParser implements Parser<EditCommand> {

    private static final Logger LOGGER = Logger.getLogger(EditCommandParser.class.getName());

    @Override
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args, "args must not be null");
        LOGGER.finer(() -> "Parsing EditCommand with args: " + args);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                args,
                PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG,
                PREFIX_CLASS, PREFIX_SUBJECTS, PREFIX_EMERGENCY_CONTACT,
                PREFIX_PAYMENT_STATUS, PREFIX_ASSIGNMENT_STATUS
        );
        assert argMultimap != null : "ArgumentMultimap must not be null";

        final Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            LOGGER.fine(() -> "Invalid index/preamble for edit command: '" + argMultimap.getPreamble() + "'");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }
        assert index != null : "Index must be parsed";

        // Reject duplicate single-occurrence prefixes early (subjects/tags can repeat)
        argMultimap.verifyNoDuplicatePrefixesFor(
                PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                PREFIX_CLASS, PREFIX_EMERGENCY_CONTACT,
                PREFIX_PAYMENT_STATUS, PREFIX_ASSIGNMENT_STATUS
        );

        EditPersonDescriptor descriptor = new EditPersonDescriptor();

        parseAndSet(argMultimap, PREFIX_NAME, ParserUtil::parseName, descriptor::setName);
        parseAndSet(argMultimap, PREFIX_PHONE, ParserUtil::parsePhone, descriptor::setPhone);
        parseAndSet(argMultimap, PREFIX_EMAIL, ParserUtil::parseEmail, descriptor::setEmail);
        parseAndSet(argMultimap, PREFIX_ADDRESS, ParserUtil::parseAddress, descriptor::setAddress);

        parseTags(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(descriptor::setTags);

        parseAndSet(argMultimap, PREFIX_CLASS, ParserUtil::parseStudentClass, descriptor::setStudentClass);
        List<String> subjects = argMultimap.getAllValues(PREFIX_SUBJECTS);
        if (!subjects.isEmpty()) {
            if (subjects.size() == 1 && subjects.get(0).trim().isEmpty()) {
                descriptor.setSubjects(Collections.emptyList());
            } else {
                descriptor.setSubjects(ParserUtil.parseSubjects(subjects));
            }
        }
        parseAndSet(argMultimap, PREFIX_EMERGENCY_CONTACT, ParserUtil::parseEmergencyContact,
                descriptor::setEmergencyContact);
        parseAndSet(argMultimap, PREFIX_PAYMENT_STATUS, ParserUtil::parsePaymentStatus, descriptor::setPaymentStatus);
        parseAndSet(argMultimap, PREFIX_ASSIGNMENT_STATUS, ParserUtil::parseAssignmentStatus,
                descriptor::setAssignmentStatus);

        if (!descriptor.isAnyFieldEdited()) {
            LOGGER.fine("EditCommand parsed with no fields edited");
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        LOGGER.finer(() -> "EditCommand parsed successfully for index " + index.getOneBased());
        return new EditCommand(index, descriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if non-empty.
     * If {@code tags} contain only one element which is an empty string, it is treated as an empty set.
     */
    private Optional<Set<Tag>> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags, "tags must not be null");
        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> normalized = (tags.size() == 1 && tags.contains(""))
                ? Collections.emptySet()
                : tags;
        return Optional.of(ParserUtil.parseTags(normalized));
    }

    @FunctionalInterface
    private interface ParserFunc<T> {
        T parse(String input) throws ParseException;
    }

    /**
     * If {@code prefix} is present in {@code map}, parses it using {@code parser} and applies {@code setter}.
     * Propagates the original {@link ParseException} (preserving constraint messages).
     */
    private <T> void parseAndSet(ArgumentMultimap map,
                                 Prefix prefix,
                                 ParserFunc<T> parser,
                                 java.util.function.Consumer<T> setter) throws ParseException {
        Optional<String> valueOpt = map.getValue(prefix);
        if (valueOpt.isEmpty()) {
            return;
        }
        T parsed = parser.parse(valueOpt.get()); // may throw ParseException with field constraint message
        setter.accept(parsed);
    }
}
