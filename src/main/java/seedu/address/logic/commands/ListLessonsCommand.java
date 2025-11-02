package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECTS;

import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.subject.Subject;

/**
 * Lists all lessons in a subject.
 */
public class ListLessonsCommand extends Command {

    public static final String COMMAND_WORD = "listlessons";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all lessons in a subject. "
            + "Parameters: "
            + PREFIX_SUBJECTS + "SUBJECT\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_SUBJECTS + "Mathematics";

    public static final String MESSAGE_SUCCESS = "Listed all lessons in subject: %1$s\n%2$s";
    public static final String MESSAGE_SUBJECT_NOT_FOUND = "Subject not found: %1$s";

    private final String subjectName;

    /**
     * Creates a ListLessonsCommand to list lessons in the specified {@code subject}
     * @param subject The subject whose lessons are to be listed
     */
    public ListLessonsCommand(String subjectName) {
        requireNonNull(subjectName);
        this.subjectName = subjectName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasSubject(subjectName)) {
            throw new CommandException(String.format(MESSAGE_SUBJECT_NOT_FOUND, subjectName));
        }

        Subject subject = model.getSubject(subjectName);
        List<Lesson> lessons = subject.getLessons().getLessons();
        String lessonListDisplay = formatLessonList(lessons);
        model.updateFilteredLessonList(lesson -> lesson.getSubject().equals(subjectName));

        return new CommandResult(String.format(MESSAGE_SUCCESS, subjectName, lessonListDisplay));
    }

    /**
     * Formats the list of lessons for display.
     * @param lessons The list of lessons to format
     * @return A formatted string representation of the lessons
     */
    private String formatLessonList(List<Lesson> lessons) {
        if (lessons.isEmpty()) {
            return "No lessons found.";
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < lessons.size(); i++) {
            sb.append(String.format("%d. %s", i + 1, lessons.get(i).getName()));
            if (i < lessons.size() - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof ListLessonsCommand
                && subjectName.equals(((ListLessonsCommand) other).subjectName));
    }
}
