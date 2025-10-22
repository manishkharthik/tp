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

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.attendance.AttendanceStatus;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.person.Person;
import seedu.address.model.person.Name;
import seedu.address.model.student.Student;

/**
 * Integration and unit tests for {@code MarkAttendanceCommand}.
 */
public class MarkAttendanceCommandTest {

    private static final String SUBJECT = "Math";
    private static final String LESSON_NAME = "L1";

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validStudentUnfilteredList_success() {
        Person personToMark = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Name nameOfPersonToMark = personToMark.getName();
        Lesson lesson = new Lesson(LESSON_NAME, SUBJECT);
        AttendanceStatus status = AttendanceStatus.PRESENT;

        MarkAttendanceCommand cmd = new MarkAttendanceCommand(nameOfPersonToMark, SUBJECT, LESSON_NAME, status);

        String expectedMessage = String.format(
                Messages.MESSAGE_SUCCESS,
                Messages.format(personToMark), status, LESSON_NAME, SUBJECT);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        // mutate expected model to reflect the same mark
        Person p = expectedModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Student s = (Student) p;
        s.getAttendanceList().markAttendance(lesson, status);

        assertCommandSuccess(cmd, model, expectedMessage, expectedModel);
    }

    @Test //RELOOK NEEDED
    public void execute_invalidStudentUnfilteredList_throwsCommandException() {
        Name invalidName = INVALID_PERSON.getName();
        MarkAttendanceCommand cmd = new MarkAttendanceCommand(invalidName, SUBJECT, LESSON_NAME,
                AttendanceStatus.PRESENT);

        assertCommandFailure(cmd, model, Messages.MESSAGE_STUDENT_NOT_FOUND);
    }

    @Test
    public void execute_validStudentFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToMark = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Name nameOfPersonToMark = personToMark.getName();
        Lesson lesson = new Lesson(LESSON_NAME, SUBJECT);
        AttendanceStatus status = AttendanceStatus.ABSENT;

        MarkAttendanceCommand cmd = new MarkAttendanceCommand(nameOfPersonToMark, SUBJECT, LESSON_NAME, status);

        String expectedMessage = String.format(
                Messages.MESSAGE_SUCCESS,
                Messages.format(personToMark), status, LESSON_NAME, SUBJECT);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        showPersonAtIndex(expectedModel, INDEX_FIRST_PERSON);
        Student student = (Student) expectedModel.getFilteredPersonList().get(0);
        student.getAttendanceList().markAttendance(lesson, status);

        assertCommandSuccess(cmd, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidStudentFilteredList_throwsCommandException() {
        Name invalidName = INVALID_PERSON.getName();
        MarkAttendanceCommand cmd = new MarkAttendanceCommand(invalidName, SUBJECT, LESSON_NAME,
                AttendanceStatus.PRESENT);

        assertCommandFailure(cmd, model, Messages.MESSAGE_STUDENT_NOT_FOUND);
    }

    @Test
    public void execute_updatesExistingRecord_success() throws CommandException {
        Lesson lesson = new Lesson(LESSON_NAME, SUBJECT);
        Student student = (Student) model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Name nameOfPersonToMark = student.getName();

        // First mark PRESENT
        MarkAttendanceCommand first = new MarkAttendanceCommand(nameOfPersonToMark, SUBJECT, LESSON_NAME,
                AttendanceStatus.PRESENT);
        first.execute(model);

        // Then mark ABSENT for same lesson -> should update, not add a duplicate
        MarkAttendanceCommand second = new MarkAttendanceCommand(nameOfPersonToMark, SUBJECT, LESSON_NAME,
                AttendanceStatus.ABSENT);
        second.execute(model);

       
        // verify only one record for that lesson and it has ABSENT
        long countForLesson = student.getAttendanceList().getStudentAttendance().stream()
                .filter(r -> r.getLesson().equals(lesson))
                .count();
        AttendanceStatus finalStatus = student.getAttendanceList().getStudentAttendance().stream()
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

        MarkAttendanceCommand a = new MarkAttendanceCommand(name1, "Math", "L1",
                AttendanceStatus.PRESENT);
        MarkAttendanceCommand b = new MarkAttendanceCommand(name1, "Math", "L1",
                AttendanceStatus.PRESENT);
        MarkAttendanceCommand c = new MarkAttendanceCommand(name2, "Math", "L1",
                AttendanceStatus.PRESENT);
        MarkAttendanceCommand d = new MarkAttendanceCommand(name1, "Physics", "L1",
                AttendanceStatus.PRESENT);
        MarkAttendanceCommand e = new MarkAttendanceCommand(name1, "Math", "L2",
                AttendanceStatus.PRESENT);
        MarkAttendanceCommand f = new MarkAttendanceCommand(name1, "Math", "L1",
                AttendanceStatus.ABSENT);

        assertTrue(a.equals(a));
        assertTrue(a.equals(b));
        assertFalse(a.equals(1));
        assertFalse(a.equals(null));
        assertFalse(a.equals(c));
        assertFalse(a.equals(d));
        assertFalse(a.equals(e));
        assertFalse(a.equals(f));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        Student student = (Student) model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Name name = student.getName();
        MarkAttendanceCommand cmd = new MarkAttendanceCommand(name, SUBJECT, LESSON_NAME,
                AttendanceStatus.PRESENT);
        String expected = MarkAttendanceCommand.class.getCanonicalName()
                + "{targetIndex=" + targetIndex
                + ", subject=" + SUBJECT
                + ", lessonName=" + LESSON_NAME
                + ", status=" + AttendanceStatus.PRESENT + "}";
        assertEquals(expected, cmd.toString());
    }
}
