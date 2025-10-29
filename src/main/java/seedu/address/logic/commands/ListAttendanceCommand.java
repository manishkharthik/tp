package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECTS;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.student.Student;
import seedu.address.model.subject.Subject;

/**
 * Lists a student's attendance records for a given subject.
 * Example: listattendance n/John s/Math
 */
public class ListAttendanceCommand extends Command {

    public static final String COMMAND_WORD = "listattendance";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists attendance for a student. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_SUBJECTS + "SUBJECT\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Tan "
            + PREFIX_SUBJECTS + "Math";

    private final Name name;
    private final Subject subject;

    /**
     * Constructs a ListAttendanceCommand to list attendance for the specified student and subject.
     * Example: listattendance n/John Tan s/Math
     */
    public ListAttendanceCommand(Name name, Subject subject) {
        requireNonNull(name);
        requireNonNull(subject);
        this.name = name;
        this.subject = subject;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Find the student
        Person foundPerson = model.getFilteredPersonList().stream()
                .filter(p -> p.getName().equals(name))
                .findFirst()
                .orElseThrow(() ->
                        new CommandException(String.format(Messages.MESSAGE_STUDENT_NOT_FOUND, name)));

        if (!(foundPerson instanceof Student)) {
            throw new CommandException(String.format(Messages.MESSAGE_PERSON_NOT_STUDENT, name));
        }

        Student student = (Student) foundPerson;

        // Check subject enrollment
        boolean enrolled = student.getSubjects().stream()
                .anyMatch(s -> s.equalsIgnoreCase(subject.getName()));
        if (!enrolled) {
            throw new CommandException(String.format(
                    Messages.MESSAGE_SUBJECT_NOT_ENROLLED, student.getName(), subject));
        }

        // Gather attendance records for that subject
        StringBuilder sb = new StringBuilder();
        student.getAttendanceList().getStudentAttendance().stream()
                .filter(r -> r.getLesson().getSubject().equalsIgnoreCase(subject.getName()))
                .forEach(r -> sb.append(String.format("%s %s\n",
                        r.getLesson().getName(), r.getStatus())));

        // If no records exist
        if (sb.length() == 0) {
            throw new CommandException(String.format(Messages.MESSAGE_NO_ATTENDANCE_RECORDS, subject.getName()));
        }

        // Return formatted result
        return new CommandResult(String.format(
                Messages.MESSAGE_LIST_ATTENDANCE_HEADER,
                student.getName(),
                subject.getName(),
                sb.toString().trim()
        ));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ListAttendanceCommand)) {
            return false;
        }

        ListAttendanceCommand o = (ListAttendanceCommand) other;
        return name.equals(o.name) && subject.equals(o.subject);
    }

    @Override
    public String toString() {
        return String.format("%s{name=%s, subject=%s}", getClass().getSimpleName(), name, subject);
    }
}
