package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECTS;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Lists all lessons in a subject.
 */
public class ListLessonsCommand extends Command {

    public static final String COMMAND_WORD = "ListLessons";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all lessons in a subject. "
            + "Parameters: "
            + PREFIX_SUBJECTS + "SUBJECT\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_SUBJECTS + "Mathematics";

    public static final String MESSAGE_SUCCESS = "Listed all lessons in subject: %1$s";
    public static final String MESSAGE_SUBJECT_NOT_FOUND = "Subject not found: %1$s";

    private final String subject;

    /**
     * Creates a ListLessonsCommand to list lessons in the specified {@code subject}
     * @param subject The subject whose lessons are to be listed
     */
    public ListLessonsCommand(String subject) {
        requireNonNull(subject);
        this.subject = subject;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasSubject(subject)) {
            throw new CommandException(String.format(MESSAGE_SUBJECT_NOT_FOUND, subject));
        }

        model.updateFilteredLessonList(lesson -> lesson.getSubject().equals(subject));
        return new CommandResult(String.format(MESSAGE_SUCCESS, subject));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ListLessonsCommand // instanceof handles nulls
                && subject.equals(((ListLessonsCommand) other).subject));
    }
}
