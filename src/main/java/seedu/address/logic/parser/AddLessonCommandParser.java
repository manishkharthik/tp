package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECTS;

import seedu.address.logic.commands.AddLessonCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.lesson.Lesson;

/**
 * Parses input arguments and creates a new AddLessonCommand object
 */
public class AddLessonCommandParser implements Parser<AddLessonCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddLessonCommand
     * and returns an AddLessonCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddLessonCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_SUBJECTS, PREFIX_NAME);

        if (!argMultimap.arePrefixesPresent(PREFIX_SUBJECTS, PREFIX_NAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddLessonCommand.MESSAGE_USAGE));
        }

        if (argMultimap.getAllValues(PREFIX_SUBJECTS).size() != 1
                || argMultimap.getAllValues(PREFIX_NAME).size() != 1) {
            throw new ParseException("Only one subject and lesson is allowed.");
        }

        String subject = argMultimap.getValue(PREFIX_SUBJECTS).get().trim();
        String lessonName = argMultimap.getValue(PREFIX_NAME).get().trim();

        if (subject.isEmpty() || lessonName.isEmpty()) {
            throw new ParseException(Lesson.MESSAGE_CONSTRAINTS);
        }

        Lesson lesson = new Lesson(lessonName, subject);

        return new AddLessonCommand(lesson);
    }
}
