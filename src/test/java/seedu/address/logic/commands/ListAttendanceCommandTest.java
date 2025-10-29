package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
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
    public void execute_success_withMathRecords() throws CommandException {
        ListAttendanceCommand cmd = new ListAttendanceCommand(JOHN, MATH);
        CommandResult result = cmd.execute(model);

        String out = result.getFeedbackToUser();
        // header + two Math lines
        assertTrue(out.startsWith("Attendance for John Tan (Math):"));
        assertTrue(out.contains("L1"));
        assertTrue(out.contains("PRESENT"));
        assertTrue(out.contains("L2"));
        assertTrue(out.contains("ABSENT"));
        // must not include Science record
        assertFalse(out.contains("Q1"));
        assertFalse(out.contains("Science"));
    }

    @Test
    public void execute_noRecordsForSubject_failure() {
        // Replace John with a student having no Math attendance yet (but still enrolled)
        Person original = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Student s = new StudentBuilder()
                .withName(JOHN.toString())
                .withStudentClass("3A")
                .withSubjects("Math,Science")
                .withEmergencyContact("98765432")
                .withPaymentStatus("Paid")
                .withAssignmentStatus("Submitted")
                .build();
        // only Science record
        s.getAttendanceList().markAttendance(new Lesson("Q1", "Science"), AttendanceStatus.EXCUSED);
        model.setPerson(original, s);

        ListAttendanceCommand cmd = new ListAttendanceCommand(JOHN, MATH);
        assertCommandFailure(cmd, model,
            String.format(Messages.MESSAGE_NO_ATTENDANCE_RECORDS, JOHN, MATH.getName()));
    }

    @Test
    public void execute_studentNotFound_failure() {
        ListAttendanceCommand cmd = new ListAttendanceCommand(new Name("Ghost"), MATH);
        assertCommandFailure(cmd, model,
                String.format(Messages.MESSAGE_STUDENT_NOT_FOUND, new Name("Ghost")));
    }

    @Test
    public void execute_subjectNotEnrolled_failure() {
        Subject history = new Subject("History");
        ListAttendanceCommand cmd = new ListAttendanceCommand(JOHN, history);
        assertCommandFailure(cmd, model,
                String.format(Messages.MESSAGE_SUBJECT_NOT_ENROLLED, JOHN, history));
    }

    @Test
    public void equals_hashcode() {
        ListAttendanceCommand a = new ListAttendanceCommand(JOHN, MATH);
        ListAttendanceCommand b = new ListAttendanceCommand(JOHN, MATH);
        ListAttendanceCommand c = new ListAttendanceCommand(JOHN, SCIENCE);
        ListAttendanceCommand d = new ListAttendanceCommand(new Name("Alice"), MATH);

        assertTrue(a.equals(a));
        assertTrue(a.equals(b));
        assertFalse(a.equals(c));
        assertFalse(a.equals(d));
        assertFalse(a.equals(null));
        assertFalse(a.equals(5));
    }

    @Test
    public void toString_containsFields() {
        ListAttendanceCommand cmd = new ListAttendanceCommand(JOHN, MATH);
        String s = cmd.toString();
        assertTrue(s.contains("John Tan"));
        assertTrue(s.contains("Math"));
    }
}
