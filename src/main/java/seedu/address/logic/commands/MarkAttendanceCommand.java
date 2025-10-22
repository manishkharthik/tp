package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LESSON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECTS;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.attendance.AttendanceStatus;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.student.Student;

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
            + PREFIX_LESSON + "Lesson 5 "
            + PREFIX_STATUS + "PRESENT";

    public static final String MESSAGE_SUCCESS = "Marked attendance for %1$s: [%2$s - %3$s] → %4$s";
    public static final String MESSAGE_STUDENT_NOT_FOUND = "No student found with name: %s";
    public static final String MESSAGE_PERSON_NOT_STUDENT =
            "%s is not a student or has not been added as a student.";
    public static final String MESSAGE_SUBJECT_NOT_ENROLLED = "%s does not read subject: %s";

    private final Name name;
    private final String subject;
    private final String lessonName;
    private final AttendanceStatus status;

    /**
     * Creates a MarkAttendanceCommand to update a student's attendance record.
     */
    public MarkAttendanceCommand(Name name, String subject, String lessonName, AttendanceStatus status) {
        requireNonNull(name);
        requireNonNull(subject);
        requireNonNull(lessonName);
        requireNonNull(status);
        this.name = name;
        this.subject = subject.trim();
        this.lessonName = lessonName.trim();
        this.status = status;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        assert model != null : "Model should not be null";
        assert name != null : "Name cannot be null";
        assert subject != null : "Subject cannot be null";
        assert lessonName != null : "Lesson name cannot be null";
        assert status != null : "Status cannot be null";

        // Find student by name in the filtered list
        Person foundPerson = model.getFilteredPersonList().stream()
                .filter(p -> p.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new CommandException(String.format(MESSAGE_STUDENT_NOT_FOUND, name)));

        if (!(foundPerson instanceof Student)) {
            throw new CommandException(String.format(MESSAGE_PERSON_NOT_STUDENT, name));
        }

        Student student = (Student) foundPerson;

        // Check if the student is enrolled in the given subject
        boolean enrolled = student.getSubjects().stream().anyMatch(s -> s.equalsIgnoreCase(subject));
        if (!enrolled) {
            throw new CommandException(String.format(MESSAGE_SUBJECT_NOT_ENROLLED, student.getName(), subject));
        }

        // Create a Lesson object and mark attendance
        Lesson lesson = new Lesson(lessonName, subject);
        student.getAttendanceList().markAttendance(lesson, status);

        String feedback = String.format(
                MESSAGE_SUCCESS,
                student.getName(),
                subject,
                lessonName,
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
                && lessonName.equals(o.lessonName)
                && status.equals(o.status);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(name, subject, lessonName, status);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("subject", subject)
                .add("lessonName", lessonName)
                .add("status", status)
                .toString();
    }
}
