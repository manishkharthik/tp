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

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.attendance.AttendanceStatus;
import seedu.address.model.lesson.Lesson;
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
    public void execute_validIndexUnfilteredList_success() {
        Person personToMark = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Lesson lesson = new Lesson(LESSON_NAME, SUBJECT);
        AttendanceStatus status = AttendanceStatus.PRESENT;

        MarkAttendanceCommand cmd = new MarkAttendanceCommand(INDEX_FIRST_PERSON, SUBJECT, LESSON_NAME, status);

        String expectedMessage = String.format(
                MarkAttendanceCommand.MESSAGE_SUCCESS,
                Messages.format(personToMark), status, LESSON_NAME, SUBJECT);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        // mutate expected model to reflect the same mark
        Person p = expectedModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Student s = (Student) p;
        s.getAttendanceList().markAttendance(lesson, status);

        assertCommandSuccess(cmd, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        MarkAttendanceCommand cmd = new MarkAttendanceCommand(outOfBoundIndex, SUBJECT, LESSON_NAME,
                AttendanceStatus.PRESENT);

        assertCommandFailure(cmd, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToMark = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Lesson lesson = new Lesson(LESSON_NAME, SUBJECT);
        AttendanceStatus status = AttendanceStatus.ABSENT;

        MarkAttendanceCommand cmd = new MarkAttendanceCommand(INDEX_FIRST_PERSON, SUBJECT, LESSON_NAME, status);

        String expectedMessage = String.format(
                MarkAttendanceCommand.MESSAGE_SUCCESS,
                Messages.format(personToMark), status, LESSON_NAME, SUBJECT);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        showPersonAtIndex(expectedModel, INDEX_FIRST_PERSON);
        Student s = (Student) expectedModel.getFilteredPersonList().get(0);
        s.getAttendanceList().markAttendance(lesson, status);

        assertCommandSuccess(cmd, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON; // still within address book size

        MarkAttendanceCommand cmd = new MarkAttendanceCommand(outOfBoundIndex, SUBJECT, LESSON_NAME,
                AttendanceStatus.PRESENT);

        assertCommandFailure(cmd, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_updatesExistingRecord_success() throws CommandException {
        Lesson lesson = new Lesson(LESSON_NAME, SUBJECT);

        // First mark PRESENT
        MarkAttendanceCommand first = new MarkAttendanceCommand(INDEX_FIRST_PERSON, SUBJECT, LESSON_NAME,
                AttendanceStatus.PRESENT);
        first.execute(model);

        // Then mark ABSENT for same lesson -> should update, not add a duplicate
        MarkAttendanceCommand second = new MarkAttendanceCommand(INDEX_FIRST_PERSON, SUBJECT, LESSON_NAME,
                AttendanceStatus.ABSENT);
        second.execute(model);

        Student s = (Student) model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        // verify only one record for that lesson and it has ABSENT
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
    public void execute_veryLargeIndex_throwsCommandException() {
        Index huge = Index.fromOneBased(Integer.MAX_VALUE);
        MarkAttendanceCommand cmd = new MarkAttendanceCommand(huge, SUBJECT, LESSON_NAME,
                AttendanceStatus.PRESENT);

        assertCommandFailure(cmd, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        MarkAttendanceCommand a = new MarkAttendanceCommand(INDEX_FIRST_PERSON, "Math", "L1",
                AttendanceStatus.PRESENT);
        MarkAttendanceCommand b = new MarkAttendanceCommand(INDEX_FIRST_PERSON, "Math", "L1",
                AttendanceStatus.PRESENT);
        MarkAttendanceCommand c = new MarkAttendanceCommand(INDEX_SECOND_PERSON, "Math", "L1",
                AttendanceStatus.PRESENT);
        MarkAttendanceCommand d = new MarkAttendanceCommand(INDEX_FIRST_PERSON, "Physics", "L1",
                AttendanceStatus.PRESENT);
        MarkAttendanceCommand e = new MarkAttendanceCommand(INDEX_FIRST_PERSON, "Math", "L2",
                AttendanceStatus.PRESENT);
        MarkAttendanceCommand f = new MarkAttendanceCommand(INDEX_FIRST_PERSON, "Math", "L1",
                AttendanceStatus.ABSENT);

        assertTrue(a.equals(a));      // same object
        assertTrue(a.equals(b));      // same values
        assertFalse(a.equals(1));     // different type
        assertFalse(a.equals(null));  // null
        assertFalse(a.equals(c));     // different index
        assertFalse(a.equals(d));     // different subject
        assertFalse(a.equals(e));     // different lesson
        assertFalse(a.equals(f));     // different status
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        MarkAttendanceCommand cmd = new MarkAttendanceCommand(targetIndex, SUBJECT, LESSON_NAME,
                AttendanceStatus.PRESENT);
        String expected = MarkAttendanceCommand.class.getCanonicalName()
                + "{targetIndex=" + targetIndex
                + ", subject=" + SUBJECT
                + ", lessonName=" + LESSON_NAME
                + ", status=" + AttendanceStatus.PRESENT + "}";
        assertEquals(expected, cmd.toString());
    }
}
