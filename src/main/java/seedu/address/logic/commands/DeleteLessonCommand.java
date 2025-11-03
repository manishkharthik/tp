package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECTS;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.lesson.Lesson;

/**
 * Deletes a lesson from a subject.
 */
public class DeleteLessonCommand extends Command {

    public static final String COMMAND_WORD = "deletelesson";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a lesson from a subject. "
            + "Parameters: "
            + PREFIX_SUBJECTS + "SUBJECT "
            + PREFIX_NAME + "LESSON_NAME\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_SUBJECTS + "Mathematics "
            + PREFIX_NAME + "Algebra";

    public static final String MESSAGE_SUCCESS = "Deleted lesson: %1$s";
    public static final String MESSAGE_LESSON_NOT_FOUND = "This lesson does not exist in the subject";

    private final Lesson toDelete;

    /**
     * Creates a DeleteLessonCommand to delete the specified {@code Lesson}
     */
    public DeleteLessonCommand(Lesson lesson) {
        requireNonNull(lesson);
        this.toDelete = lesson;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasLesson(toDelete)) {
            throw new CommandException(MESSAGE_LESSON_NOT_FOUND);
        }

        model.deleteLesson(toDelete);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toDelete.getName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof DeleteLessonCommand
                && toDelete.equals(((DeleteLessonCommand) other).toDelete));
    }

    @Override
    public int hashCode() {
        return toDelete.hashCode();
    }
}
