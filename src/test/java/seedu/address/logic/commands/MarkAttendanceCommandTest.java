package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.INVALID_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
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
 * Integration and unit tests for {@code MarkAttendanceCommand}.
 */
public class MarkAttendanceCommandTest {

    private static final Subject SUBJECT = new Subject("Math");
    private static final String LESSON_NAME = "L1";

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        // Ensure the first person is a Student so we never hit ClassCastException
        Person original = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Student student0 = new StudentBuilder()
                .withName(original.getName().toString())
                .withStudentClass("3A")
                .withSubjects("Math,Science")
                .withEmergencyContact("98765432")
                .withPaymentStatus("Paid")
                .withAssignmentStatus("Submitted")
                .build();
        model.setPerson(original, student0);

        // Also ensure the second person is a Student (for equals() variety checks)
        Person original2 = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        Student student1 = new StudentBuilder()
                .withName(original2.getName().toString())
                .withStudentClass("3B")
                .withSubjects("Math,Physics")
                .withEmergencyContact("98765431")
                .withPaymentStatus("Paid")
                .withAssignmentStatus("Submitted")
                .build();
        model.setPerson(original2, student1);
    }

    @Test
    public void execute_validStudentUnfilteredList_success() {
        Person personToMark = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Name nameOfPersonToMark = personToMark.getName();
        Lesson lesson = new Lesson(LESSON_NAME, SUBJECT.getName());
        AttendanceStatus status = AttendanceStatus.PRESENT;

        MarkAttendanceCommand cmd = new MarkAttendanceCommand(nameOfPersonToMark, SUBJECT, LESSON_NAME, status);

        String expectedMessage = String.format(
                Messages.MESSAGE_SUCCESS, nameOfPersonToMark, SUBJECT, LESSON_NAME, status);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        Student expectedStudent = (Student) expectedModel.getFilteredPersonList()
                .get(INDEX_FIRST_PERSON.getZeroBased());
        expectedStudent.getAttendanceList().markAttendance(lesson, status);

        assertCommandSuccess(cmd, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidStudentUnfilteredList_throwsCommandException() {
        Name invalidName = INVALID_PERSON.getName();
        MarkAttendanceCommand cmd = new MarkAttendanceCommand(invalidName, SUBJECT, LESSON_NAME,
                AttendanceStatus.PRESENT);

        assertCommandFailure(cmd, model, String.format(Messages.MESSAGE_STUDENT_NOT_FOUND, invalidName));
    }

    @Test
    public void execute_validStudentFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToMark = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Name nameOfPersonToMark = personToMark.getName();
        Lesson lesson = new Lesson(LESSON_NAME, SUBJECT.getName());
        AttendanceStatus status = AttendanceStatus.ABSENT;

        MarkAttendanceCommand cmd = new MarkAttendanceCommand(nameOfPersonToMark, SUBJECT, LESSON_NAME, status);

        String expectedMessage = String.format(
                Messages.MESSAGE_SUCCESS, nameOfPersonToMark, SUBJECT, LESSON_NAME, status);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        showPersonAtIndex(expectedModel, INDEX_FIRST_PERSON);
        Student expectedStudent = (Student) expectedModel.getFilteredPersonList().get(0);
        expectedStudent.getAttendanceList().markAttendance(lesson, status);

        assertCommandSuccess(cmd, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidStudentFilteredList_throwsCommandException() {
        Name invalidName = INVALID_PERSON.getName();
        MarkAttendanceCommand cmd = new MarkAttendanceCommand(invalidName, SUBJECT, LESSON_NAME,
                AttendanceStatus.PRESENT);

        assertCommandFailure(cmd, model, String.format(Messages.MESSAGE_STUDENT_NOT_FOUND, invalidName));
    }

    @Test
    public void execute_updatesExistingRecord_success() throws CommandException {
        Person p = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Name name = p.getName();
        Lesson lesson = new Lesson(LESSON_NAME, SUBJECT.getName());

        // First mark PRESENT
        new MarkAttendanceCommand(name, SUBJECT, LESSON_NAME, AttendanceStatus.PRESENT).execute(model);

        // Then mark ABSENT for same lesson -> should update, not add a duplicate
        new MarkAttendanceCommand(name, SUBJECT, LESSON_NAME, AttendanceStatus.ABSENT).execute(model);

        Student studentNow = (Student) model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        long countForLesson = studentNow.getAttendanceList().getStudentAttendance().stream()
                .filter(r -> r.getLesson().equals(lesson))
                .count();
        AttendanceStatus finalStatus = studentNow.getAttendanceList().getStudentAttendance().stream()
                .filter(r -> r.getLesson().equals(lesson))
                .findFirst().get().getStatus();

        assertEquals(1, countForLesson);
        assertEquals(AttendanceStatus.ABSENT, finalStatus);
    }

    @Test
    public void equals() {
        Student student1 = (Student) model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Name name1 = student1.getName();
        Student student2 = (Student) model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        Name name2 = student2.getName();

        MarkAttendanceCommand a = new MarkAttendanceCommand(name1, new Subject("Math"),
                "L1", AttendanceStatus.PRESENT);
        MarkAttendanceCommand b = new MarkAttendanceCommand(name1, new Subject("Math"),
                "L1", AttendanceStatus.PRESENT);
        MarkAttendanceCommand c = new MarkAttendanceCommand(name2, new Subject("Math"),
                "L1", AttendanceStatus.PRESENT);
        MarkAttendanceCommand d = new MarkAttendanceCommand(name1, new Subject("Physics"),
                "L1", AttendanceStatus.PRESENT);
        MarkAttendanceCommand e = new MarkAttendanceCommand(name1, new Subject("Math"),
                "L2", AttendanceStatus.PRESENT);
        MarkAttendanceCommand f = new MarkAttendanceCommand(name1, new Subject("Math"),
                "L1", AttendanceStatus.ABSENT);

        assertTrue(a.equals(a));
        assertTrue(a.equals(b));
        assertFalse(a.equals(1));
        assertFalse(a.equals(null));
        assertFalse(a.equals(c));
        assertFalse(a.equals(d));
        assertFalse(a.equals(e));
        assertFalse(a.equals(f));
    }
}
