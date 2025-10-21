package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECTS;

import seedu.address.logic.commands.DeleteLessonCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.lesson.Lesson;

/**
 * Parses input arguments and creates a new DeleteLessonCommand object.
 */
public class DeleteLessonCommandParser implements Parser<DeleteLessonCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteLessonCommand
     * and returns a DeleteLessonCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public DeleteLessonCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_SUBJECTS, PREFIX_NAME);

        // Check that both required prefixes are present and there’s no extra preamble text
        if (!argMultimap.arePrefixesPresent(PREFIX_SUBJECTS, PREFIX_NAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteLessonCommand.MESSAGE_USAGE));
        }

        // Extract the subject and lesson name from the prefixes
        String subject = argMultimap.getValue(PREFIX_SUBJECTS).get().trim();
        String lessonName = argMultimap.getValue(PREFIX_NAME).get().trim();

        if (subject.isEmpty() || lessonName.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteLessonCommand.MESSAGE_USAGE));
        }

        // Construct the lesson to be deleted and wrap it in a DeleteLessonCommand
        Lesson lessonToDelete = new Lesson(lessonName, subject);
        return new DeleteLessonCommand(lessonToDelete);
    }
}
