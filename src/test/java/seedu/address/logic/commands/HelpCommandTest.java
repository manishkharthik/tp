package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.HelpCommand.SHOWING_HELP_MESSAGE;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class HelpCommandTest {

    private final Model model = new ModelManager();
    private final Model expectedModel = new ModelManager();

    @Test
    public void execute_help_success() throws CommandException {
        CommandResult expectedCommandResult = new CommandResult(SHOWING_HELP_MESSAGE, true, false);
        assertEquals(expectedCommandResult, new HelpCommand("").execute(model));
    }

    @Test
    public void equals_sameInstance_returnsTrue() {
        HelpCommand helpCommand1 = new HelpCommand("");
        HelpCommand helpCommand2 = new HelpCommand("");
        assertTrue(helpCommand1.equals(helpCommand2));
    }

    @Test
    public void toString_correctFormat() {
        HelpCommand helpCommand = new HelpCommand("");
        String expected = HelpCommand.class.getCanonicalName();
        assertEquals(expected, helpCommand.toString());
    }
}
