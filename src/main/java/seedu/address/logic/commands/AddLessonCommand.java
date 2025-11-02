package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECTS;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.lesson.Lesson;

/**
 * Adds a lesson to a subject.
 */
public class AddLessonCommand extends Command {

    public static final String COMMAND_WORD = "addlesson";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a lesson to a subject. "
            + "Parameters: "
            + PREFIX_SUBJECTS + "SUBJECT "
            + PREFIX_NAME + "LESSON_NAME\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_SUBJECTS + "Mathematics "
            + PREFIX_NAME + "Algebra";

    public static final String MESSAGE_SUCCESS = "New %2$s - %1$s lesson added";
    public static final String MESSAGE_DUPLICATE_LESSON = "This lesson already exists in this subject";

    private final Lesson toAdd;

    /**
     * Creates an AddLessonCommand to add the specified {@code Lesson}
     */
    public AddLessonCommand(Lesson lesson) {
        requireNonNull(lesson);
        toAdd = lesson;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasLesson(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_LESSON);
        }

        model.addLesson(toAdd);

        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd.getName(), toAdd.getSubject()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof AddLessonCommand // instanceof handles nulls
                && toAdd.equals(((AddLessonCommand) other).toAdd));
    }

    @Override
    public int hashCode() {
        return toAdd.hashCode();
    }
}
