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
    private static final Lesson LESSON = new Lesson(LESSON_NAME, SUBJECT.getName());

    private Model model;

    private void ensureSubjectAndLessonExist(Model m) {
        if (!m.hasLesson(LESSON)) {
            m.addLesson(LESSON);
        }
    }

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        // Ensure the first person is a Student
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

        // Ensure the second person is also a Student
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

        // Seed subject/lesson so execute() validation passes
        ensureSubjectAndLessonExist(model);
    }

    @Test
    public void execute_validStudentUnfilteredList_success() {
        Person personToMark = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Name nameOfPersonToMark = personToMark.getName();
        AttendanceStatus status = AttendanceStatus.PRESENT;

        MarkAttendanceCommand cmd = new MarkAttendanceCommand(nameOfPersonToMark, SUBJECT, LESSON, status);

        String expectedMessage = String.format(
                Messages.MESSAGE_SUCCESS, nameOfPersonToMark, SUBJECT, LESSON_NAME, status);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        ensureSubjectAndLessonExist(expectedModel); // keep expected in sync

        Student expectedStudent = (Student) expectedModel.getFilteredPersonList()
                .get(INDEX_FIRST_PERSON.getZeroBased());
        expectedStudent.getAttendanceList().markAttendance(LESSON, status);

        assertCommandSuccess(cmd, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidStudentUnfilteredList_throwsCommandException() {
        Name invalidName = INVALID_PERSON.getName();

        MarkAttendanceCommand cmd = new MarkAttendanceCommand(invalidName, SUBJECT, LESSON,
                AttendanceStatus.PRESENT);

        assertCommandFailure(cmd, model, String.format(Messages.MESSAGE_STUDENT_NOT_FOUND, invalidName));
    }

    @Test
    public void execute_validStudentFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToMark = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Name nameOfPersonToMark = personToMark.getName();
        AttendanceStatus status = AttendanceStatus.ABSENT;

        MarkAttendanceCommand cmd = new MarkAttendanceCommand(nameOfPersonToMark, SUBJECT, LESSON, status);

        String expectedMessage = String.format(
                Messages.MESSAGE_SUCCESS, nameOfPersonToMark, SUBJECT, LESSON_NAME, status);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        ensureSubjectAndLessonExist(expectedModel); // keep expected in sync

        showPersonAtIndex(expectedModel, INDEX_FIRST_PERSON);
        Student expectedStudent = (Student) expectedModel.getFilteredPersonList().get(0);
        expectedStudent.getAttendanceList().markAttendance(LESSON, status);

        assertCommandSuccess(cmd, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidStudentFilteredList_throwsCommandException() {
        Name invalidName = INVALID_PERSON.getName();

        MarkAttendanceCommand cmd = new MarkAttendanceCommand(invalidName, SUBJECT, LESSON,
                AttendanceStatus.PRESENT);

        assertCommandFailure(cmd, model, String.format(Messages.MESSAGE_STUDENT_NOT_FOUND, invalidName));
    }

    @Test
    public void execute_updatesExistingRecord_success() throws CommandException {
        Person p = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Name name = p.getName();

        // First mark PRESENT
        new MarkAttendanceCommand(name, SUBJECT, LESSON, AttendanceStatus.PRESENT).execute(model);

        // Then mark ABSENT for same lesson -> should update, not add a duplicate
        new MarkAttendanceCommand(name, SUBJECT, LESSON, AttendanceStatus.ABSENT).execute(model);

        Student studentNow = (Student) model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        long countForLesson = studentNow.getAttendanceList().getStudentAttendance().stream()
                .filter(r -> r.getLesson().equals(LESSON))
                .count();
        AttendanceStatus finalStatus = studentNow.getAttendanceList().getStudentAttendance().stream()
                .filter(r -> r.getLesson().equals(LESSON))
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

        Subject math = SUBJECT;
        Subject physics = new Subject("Physics");

        Lesson l1Math = new Lesson("L1", "Math");
        Lesson l2Math = new Lesson("L2", "Math");
        Lesson l1Physics = new Lesson("L1", "Physics");

        MarkAttendanceCommand a = new MarkAttendanceCommand(name1, math, l1Math, AttendanceStatus.PRESENT);
        MarkAttendanceCommand b = new MarkAttendanceCommand(name1, math, l1Math, AttendanceStatus.PRESENT);
        MarkAttendanceCommand c = new MarkAttendanceCommand(name2, math, l1Math, AttendanceStatus.PRESENT);
        MarkAttendanceCommand d = new MarkAttendanceCommand(name1, physics, l1Physics, AttendanceStatus.PRESENT);
        MarkAttendanceCommand e = new MarkAttendanceCommand(name1, math, l2Math, AttendanceStatus.PRESENT);
        MarkAttendanceCommand f = new MarkAttendanceCommand(name1, math, l1Math, AttendanceStatus.ABSENT);

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
