package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LESSON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECTS;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.attendance.AttendanceStatus;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.student.Student;
import seedu.address.model.subject.Subject;

/**
 * Marks a student's attendance for a specified lesson.
 */
public class MarkAttendanceCommand extends Command {

    public static final String COMMAND_WORD = "markattendance";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks attendance for a student. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_SUBJECTS + "SUBJECT "
            + PREFIX_LESSON + "LESSON "
            + PREFIX_STATUS + "STATUS (PRESENT/ABSENT/LATE/EXCUSED)\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Tan "
            + PREFIX_SUBJECTS + "Math "
            + PREFIX_LESSON + "Algebra "
            + PREFIX_STATUS + "PRESENT";

    private final Name name;
    private final Subject subject;
    private final Lesson lesson;
    private final AttendanceStatus status;

    /**
     * Constructs a MarkAttendanceCommand to mark attendance for the specified student, subject, lesson, and status.
     */
    public MarkAttendanceCommand(Name name, Subject subject, Lesson lesson, AttendanceStatus status) {
        requireNonNull(name);
        requireNonNull(subject);
        requireNonNull(lesson);
        requireNonNull(status);
        this.name = name;
        this.subject = subject;
        this.lesson = lesson;
        this.status = status;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        assert model != null : "Model should not be null";
        assert name != null : "Name cannot be null";
        assert subject != null : "Subject cannot be null";
        assert lesson != null : "Lesson name cannot be null";
        assert status != null : "Status cannot be null";

        // Find student by name
        Person foundPerson = model.getFilteredPersonList().stream()
                .filter(p -> p.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new CommandException(String.format(Messages.MESSAGE_STUDENT_NOT_FOUND, name)));

        if (!(foundPerson instanceof Student)) {
            throw new CommandException(String.format(Messages.MESSAGE_PERSON_NOT_STUDENT, name));
        }

        Student student = (Student) foundPerson;

        // Check the student is enrolled in the subject
        boolean enrolled = student.getSubjects().stream()
                .anyMatch(s -> s.equalsIgnoreCase(subject.getName()));
        if (!enrolled) {
            throw new CommandException(
                    String.format(Messages.MESSAGE_SUBJECT_NOT_ENROLLED, student.getName(), subject));
        }

        // Check the lesson exists in the model
        if (!model.hasLesson(new Lesson(lesson.getName(), subject.getName()))) {
            throw new CommandException(String.format(
                Messages.MESSAGE_LESSON_NOT_FOUND, lesson.getName(), subject.getName()));
        }

        // Mark attendance
        student.getAttendanceList().markAttendance(lesson, status);

        // Feedback
        String feedback = String.format(
                Messages.MESSAGE_SUCCESS,
                student.getName(),
                subject.getName(),
                lesson.getName(),
                status
        );

        return new CommandResult(feedback);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof MarkAttendanceCommand)) {
            return false;
        }

        MarkAttendanceCommand o = (MarkAttendanceCommand) other;
        return name.equals(o.name)
                && subject.equals(o.subject)
                && lesson.equals(o.lesson)
                && status.equals(o.status);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(name, subject, lesson, status);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("subject", subject)
                .add("lesson", lesson)
                .add("status", status)
                .toString();
    }
}
