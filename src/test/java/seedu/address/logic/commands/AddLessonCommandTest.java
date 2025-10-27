package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.lesson.Lesson;

public class AddLessonCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void constructor_nullLesson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddLessonCommand(null));
    }

    @Test
    public void execute_validLesson_success() {
        Lesson lesson = new Lesson("Math", "Algebra");
        AddLessonCommand addLessonCommand = new AddLessonCommand(lesson);

        String expectedMessage = String.format(AddLessonCommand.MESSAGE_SUCCESS, lesson);
        expectedModel.addLesson(lesson);

        assertCommandSuccess(addLessonCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateLesson_throwsCommandException() {
        Lesson lesson = new Lesson("Math", "Algebra");
        model.addLesson(lesson);
        AddLessonCommand addLessonCommand = new AddLessonCommand(lesson);

        assertCommandFailure(addLessonCommand, model, AddLessonCommand.MESSAGE_DUPLICATE_LESSON);
    }

    @Test
    public void execute_emptyAddressBook_success() {
        model = new ModelManager(); // start with no data
        expectedModel = new ModelManager();
        Lesson lesson = new Lesson("Science", "Physics");

        AddLessonCommand command = new AddLessonCommand(lesson);
        expectedModel.addLesson(lesson);

        String expectedMessage = String.format(AddLessonCommand.MESSAGE_SUCCESS, lesson);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }


    @Test
    public void equals() {
        Lesson algebra = new Lesson("Math", "Algebra");
        Lesson calculus = new Lesson("Math", "Calculus");
        AddLessonCommand addAlgebraCommand = new AddLessonCommand(algebra);
        AddLessonCommand addCalculusCommand = new AddLessonCommand(calculus);

        // same object -> returns true
        assertTrue(addAlgebraCommand.equals(addAlgebraCommand));

        // same values -> returns true
        AddLessonCommand addAlgebraCommandCopy = new AddLessonCommand(algebra);
        assertTrue(addAlgebraCommand.equals(addAlgebraCommandCopy));

        // different types -> returns false
        assertFalse(addAlgebraCommand.equals(1));

        // null -> returns false
        assertFalse(addAlgebraCommand.equals(null));

        // different lesson -> returns false
        assertFalse(addAlgebraCommand.equals(addCalculusCommand));
    }
}
