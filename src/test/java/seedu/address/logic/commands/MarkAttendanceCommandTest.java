package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

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

/**
 * Integration and unit tests for {@code MarkAttendanceCommand}.
 */
public class MarkAttendanceCommandTest {

    private static final String SUBJECT = "Math";
    private static final String LESSON_NAME = "L1";

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validNameUnfilteredList_success() {
        Person personToMark = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Name targetName = personToMark.getName();
        Lesson lesson = new Lesson(LESSON_NAME, SUBJECT);
        AttendanceStatus status = AttendanceStatus.PRESENT;

        MarkAttendanceCommand cmd = new MarkAttendanceCommand(targetName, SUBJECT, LESSON_NAME, status);

        String expectedMessage = String.format(
                MarkAttendanceCommand.MESSAGE_SUCCESS,
                targetName, SUBJECT, LESSON_NAME, status);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Student s = (Student) expectedModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        s.getAttendanceList().markAttendance(lesson, status);

        assertCommandSuccess(cmd, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_nameNotInList_throwsCommandException() {
        Name missing = new Name("Definitely Not In List");
        MarkAttendanceCommand cmd = new MarkAttendanceCommand(missing, SUBJECT, LESSON_NAME,
                AttendanceStatus.PRESENT);

        assertCommandFailure(cmd, model, String.format(MarkAttendanceCommand.MESSAGE_STUDENT_NOT_FOUND, missing));
    }

    @Test
    public void execute_validNameFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person shown = model.getFilteredPersonList().get(0);
        Name targetName = shown.getName();
        Lesson lesson = new Lesson(LESSON_NAME, SUBJECT);
        AttendanceStatus status = AttendanceStatus.ABSENT;

        MarkAttendanceCommand cmd = new MarkAttendanceCommand(targetName, SUBJECT, LESSON_NAME, status);

        String expectedMessage = String.format(
                MarkAttendanceCommand.MESSAGE_SUCCESS,
                targetName, SUBJECT, LESSON_NAME, status);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        showPersonAtIndex(expectedModel, INDEX_FIRST_PERSON);
        Student s = (Student) expectedModel.getFilteredPersonList().get(0);
        s.getAttendanceList().markAttendance(lesson, status);

        assertCommandSuccess(cmd, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_updatesExistingRecord_success() throws CommandException {
        Person person = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Name targetName = person.getName();
        Lesson lesson = new Lesson(LESSON_NAME, SUBJECT);

        // First mark PRESENT
        new MarkAttendanceCommand(targetName, SUBJECT, LESSON_NAME, AttendanceStatus.PRESENT).execute(model);
        // Then mark ABSENT for same lesson -> should update, not add duplicate
        new MarkAttendanceCommand(targetName, SUBJECT, LESSON_NAME, AttendanceStatus.ABSENT).execute(model);

        Student s = (Student) model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        long countForLesson = s.getAttendanceList().getStudentAttendance().stream()
                .filter(r -> r.getLesson().equals(lesson))
                .count();
        AttendanceStatus finalStatus = s.getAttendanceList().getStudentAttendance().stream()
                .filter(r -> r.getLesson().equals(lesson))
                .findFirst().get().getStatus();

        assertEquals(1, countForLesson);
        assertEquals(AttendanceStatus.ABSENT, finalStatus);
    }

    @Test
    public void equals() {
        Person p1 = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person p2 = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        Name n1 = p1.getName();
        Name n2 = p2.getName();

        MarkAttendanceCommand a = new MarkAttendanceCommand(n1, "Math", "L1", AttendanceStatus.PRESENT);
        MarkAttendanceCommand b = new MarkAttendanceCommand(n1, "Math", "L1", AttendanceStatus.PRESENT);
        MarkAttendanceCommand c = new MarkAttendanceCommand(n2, "Math", "L1", AttendanceStatus.PRESENT);
        MarkAttendanceCommand d = new MarkAttendanceCommand(n1, "Physics", "L1", AttendanceStatus.PRESENT);
        MarkAttendanceCommand e = new MarkAttendanceCommand(n1, "Math", "L2", AttendanceStatus.PRESENT);
        MarkAttendanceCommand f = new MarkAttendanceCommand(n1, "Math", "L1", AttendanceStatus.ABSENT);

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
        Person person = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Name targetName = person.getName();
        MarkAttendanceCommand cmd = new MarkAttendanceCommand(targetName, SUBJECT, LESSON_NAME,
                AttendanceStatus.PRESENT);
        String expected = MarkAttendanceCommand.class.getCanonicalName()
                + "{name=" + targetName
                + ", subject=" + SUBJECT
                + ", lessonName=" + LESSON_NAME
                + ", status=" + AttendanceStatus.PRESENT + "}";
        assertEquals(expected, cmd.toString());
    }
}
