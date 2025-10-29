package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ListLessonsCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_subjectFound_listsLessons() {
        String subject = "Math";
        model.addLesson(new seedu.address.model.lesson.Lesson("Algebra", "Math"));
        expectedModel.addLesson(new seedu.address.model.lesson.Lesson("Algebra", "Math"));

        ListLessonsCommand command = new ListLessonsCommand(subject);
        String expectedMessage = String.format(
            ListLessonsCommand.MESSAGE_SUCCESS,
            subject,
            "1. Algebra"
        );

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_subjectNotFound_throwsCommandException() {
        String subject = "NonexistentSubject";
        ListLessonsCommand command = new ListLessonsCommand(subject);
        assertCommandFailure(command, model, String.format(ListLessonsCommand.MESSAGE_SUBJECT_NOT_FOUND, subject));
    }

    @Test
    public void equals() {
        ListLessonsCommand listMathCommand = new ListLessonsCommand("Math");
        ListLessonsCommand listScienceCommand = new ListLessonsCommand("Science");

        // same object -> returns true
        assertTrue(listMathCommand.equals(listMathCommand));

        // same values -> returns true
        ListLessonsCommand listMathCommandCopy = new ListLessonsCommand("Math");
        assertTrue(listMathCommand.equals(listMathCommandCopy));

        // different types -> returns false
        assertFalse(listMathCommand.equals(1));

        // null -> returns false
        assertFalse(listMathCommand.equals(null));

        // different subject -> returns false
        assertFalse(listMathCommand.equals(listScienceCommand));
    }
}
