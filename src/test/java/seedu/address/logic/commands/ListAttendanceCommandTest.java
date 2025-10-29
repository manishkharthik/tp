package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
// import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.attendance.AttendanceStatus;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.student.Student;
import seedu.address.model.subject.Subject;
import seedu.address.testutil.StudentBuilder;

/**
 * Integration and unit tests for {@code ListAttendanceCommand}.
 *
 * Assumes ListAttendanceCommand prints:
 *   Header: "Attendance for {name} ({subject}):"
 *   Empty:  "No attendance records for this subject."
 */
public class ListAttendanceCommandTest {

    private static final Name JOHN = new Name("John Tan");
    private static final Subject MATH = new Subject("Math");
    private static final Subject SCIENCE = new Subject("Science");

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        // Ensure first person is a Student named "John Tan" taking Math & Science
        Person original = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Student student = new StudentBuilder()
                .withName(JOHN.toString())
                .withStudentClass("3A")
                .withSubjects("Math,Science")
                .withEmergencyContact("98765432")
                .withPaymentStatus("Paid")
                .withAssignmentStatus("Submitted")
                .build();

        // Seed attendance: Math L1 Present, Math L2 Absent, Science Q1 Excused
        student.getAttendanceList().markAttendance(new Lesson("L1", "Math"), AttendanceStatus.PRESENT);
        student.getAttendanceList().markAttendance(new Lesson("L2", "Math"), AttendanceStatus.ABSENT);
        student.getAttendanceList().markAttendance(new Lesson("Q1", "Science"), AttendanceStatus.EXCUSED);

        model.setPerson(original, student);
    }

    @Test
    public void execute_studentNotFound_throwsCommandException() {
        ListAttendanceCommand command = new ListAttendanceCommand("Alice", "Math");
        assertThrows(CommandException.class, () -> command.execute(model),
            String.format(ListAttendanceCommand.MESSAGE_STUDENT_NOT_FOUND, "Alice"));
    }

    @Test
    public void execute_subjectNotFound_throwsCommandException() {
        ListAttendanceCommand command = new ListAttendanceCommand("John", "Science");
        assertThrows(CommandException.class, () -> command.execute(model),
            String.format(ListAttendanceCommand.MESSAGE_SUBJECT_NOT_FOUND, "Science", "John"));
    }

    @Test
    public void execute_noLessons_throwsCommandException() {
        Student student = new Student(new Name("Mary"),
                Arrays.asList(new Subject("Chemistry"), new Subject("Physics")),
                "Class 2B",
                "98765432",
                "Unpaid",
                "Not Submitted");
        Subject emptySubject = new Subject("Physics");
        model.addPerson(student);

        ListAttendanceCommand command = new ListAttendanceCommand("Mary", "Physics");
        assertThrows(CommandException.class, () -> command.execute(model),
            String.format(ListAttendanceCommand.MESSAGE_NO_LESSONS, "Mary", "Physics"));
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        ListAttendanceCommand first = new ListAttendanceCommand("John", "Math");
        ListAttendanceCommand second = new ListAttendanceCommand("john", "MATH");
        assertEquals(first, second);
    }

    @Test
    public void equals_differentValues_returnsFalse() {
        ListAttendanceCommand first = new ListAttendanceCommand("John", "Math");
        ListAttendanceCommand second = new ListAttendanceCommand("Alice", "Science");
        assertEquals(false, first.equals(second));
    }

    @Test
    public void execute_partialNameDoesNotMatch_throwsCommandException() {
        // "John Doe" exists, but we pass only "John"
        ListAttendanceCommand command = new ListAttendanceCommand("John Doe", "Math");

        assertThrows(CommandException.class, () -> command.execute(model),
                String.format(ListAttendanceCommand.MESSAGE_STUDENT_NOT_FOUND, "John Doe"));
    }
}
