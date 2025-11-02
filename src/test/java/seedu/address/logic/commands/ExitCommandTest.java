package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class ExitCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_exit_success() {
        CommandResult expectedCommandResult = new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true);
        assertCommandSuccess(new ExitCommand(), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void equals() {
        ExitCommand command1 = new ExitCommand();
        ExitCommand command2 = new ExitCommand();

        // same object → true
        assertTrue(command1.equals(command1));

        // different instances → false (default behavior)
        assertFalse(command1.equals(command2));

        // null → false
        assertFalse(command1.equals(null));

        // different type → false
        assertFalse(command1.equals(1));
    }


    @Test
    public void toStringMethod() {
        ExitCommand exitCommand = new ExitCommand();
        String expected = ExitCommand.class.getCanonicalName();
        assertEquals(expected, exitCommand.toString());
    }

}
