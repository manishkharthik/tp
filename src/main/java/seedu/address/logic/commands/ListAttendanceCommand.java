package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.LessonList;
import seedu.address.model.student.Student;
import seedu.address.model.subject.Subject;

/**
 * Lists all attendance records for a given student and subject.
 */
public class ListAttendanceCommand extends Command {

    public static final String COMMAND_WORD = "listAttendance";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists attendance records for a student in a subject.\n"
            + "Parameters: n/STUDENT_NAME s/SUBJECT\n"
            + "Example: " + COMMAND_WORD + " n/John s/Math";

    public static final String MESSAGE_SUCCESS = "Attendance for %1$s in %2$s:\n%3$s";
    public static final String MESSAGE_STUDENT_NOT_FOUND = "Student '%1$s' not found.";
    public static final String MESSAGE_SUBJECT_NOT_FOUND = "Subject '%1$s' not found for student '%2$s'.";
    public static final String MESSAGE_NO_LESSONS = "No lessons found for %1$s in %2$s.";

    private final String studentName;
    private final String subjectName;

    public ListAttendanceCommand(String studentName, String subjectName) {
        requireNonNull(studentName);
        requireNonNull(subjectName);
        this.studentName = studentName.trim();
        this.subjectName = subjectName.trim();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Find student (case-insensitive)
        Optional<Student> studentOpt = model.getFilteredPersonList().stream()
                .filter(p -> p instanceof Student
                        && ((Student) p).getName().fullName.equalsIgnoreCase(studentName))
                .map(p -> (Student) p)
                .findFirst();

        if (studentOpt.isEmpty()) {
            throw new CommandException(String.format(MESSAGE_STUDENT_NOT_FOUND, studentName));
        }

        Student student = studentOpt.get();

        // Find subject (case-insensitive)
        Optional<Subject> subjectOpt = student.getSubjects().stream()
                .filter(s -> s.getName().equalsIgnoreCase(subjectName))
                .findFirst();

        if (subjectOpt.isEmpty()) {
            throw new CommandException(String.format(MESSAGE_SUBJECT_NOT_FOUND, subjectName, studentName));
        }

        Subject subject = subjectOpt.get();
        LessonList lessonList = subject.getLessons();
        String formatted = lessonList.getFormattedAttendance();

        // Check if lessons exist
        if (lessonList.getLessons().isEmpty()) {
            throw new CommandException(String.format(MESSAGE_NO_LESSONS, studentName, subjectName));
        }

        // Build output like "L1 Absent"
        StringBuilder sb = new StringBuilder();
        for (Lesson lesson : lessonList.getLessons()) {
            sb.append(lesson.getName())
              .append(" ")
              .append(lesson.getAttendanceStatus().toString())
              .append("\n");
        }

        return new CommandResult(String.format(
            MESSAGE_SUCCESS, studentName, subjectName, formatted
        ));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof ListAttendanceCommand
                && studentName.equalsIgnoreCase(((ListAttendanceCommand) other).studentName)
                && subjectName.equalsIgnoreCase(((ListAttendanceCommand) other).subjectName));
    }
}
