package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.lesson.Lesson;

public class DeleteLessonCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(new AddressBook(), new UserPrefs());
    }

    @Test
    public void execute_existingLesson_success() throws Exception {
        Lesson lesson = new Lesson("Algebra", "Math");
        model.addLesson(lesson);

        DeleteLessonCommand command = new DeleteLessonCommand(lesson);
        CommandResult result = command.execute(model);

        assertEquals(
            String.format(DeleteLessonCommand.MESSAGE_SUCCESS, lesson),
            result.getFeedbackToUser()
        );
        assertFalse(model.hasLesson(lesson)); // lesson should be gone
    }

    @Test
    public void execute_nonExistingLesson_throwsCommandException() {
        Lesson lesson = new Lesson("Nonexistent", "Physics");
        DeleteLessonCommand command = new DeleteLessonCommand(lesson);

        assertThrows(CommandException.class, () -> command.execute(model));
    }

    @Test
    public void equals() {
        Lesson algebra = new Lesson("Algebra", "Math");
        Lesson geometry = new Lesson("Geometry", "Math");
        DeleteLessonCommand deleteAlgebra = new DeleteLessonCommand(algebra);
        DeleteLessonCommand deleteGeometry = new DeleteLessonCommand(geometry);

        assertTrue(deleteAlgebra.equals(deleteAlgebra)); // same object
        assertTrue(deleteAlgebra.equals(new DeleteLessonCommand(algebra))); // same values
        assertFalse(deleteAlgebra.equals(deleteGeometry)); // different lessons
        assertFalse(deleteAlgebra.equals(null));
    }
}

