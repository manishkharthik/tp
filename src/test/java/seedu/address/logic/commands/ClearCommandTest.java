package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration and unit tests for {@code ClearCommand}.
 */
public class ClearCommandTest {

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(""), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.setAddressBook(new AddressBook());

        assertCommandSuccess(new ClearCommand(""), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        ClearCommand command = new ClearCommand("");
        org.junit.jupiter.api.Assertions.assertThrows(NullPointerException.class, () -> command.execute(null));
    }

    @Test
    public void execute_nonEmptyAddressBook_personListBecomesEmpty() throws CommandException {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        assertFalse(model.getFilteredPersonList().isEmpty(), "Person list should start non-empty");

        new ClearCommand("").execute(model);

        assertTrue(model.getFilteredPersonList().isEmpty(), "Person list should be empty after clear");
    }

    @Test
    public void execute_clearTwice_success() throws CommandException {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        ClearCommand command = new ClearCommand("");

        command.execute(model);
        command.execute(model);

        assertTrue(model.getFilteredPersonList().isEmpty(), "Clearing twice should not fail");
    }

    @Test
    public void equals_sameInstance_returnsTrue() {
        ClearCommand clearCommand = new ClearCommand("");
        assertTrue(clearCommand.equals(clearCommand));
    }

    @Test
    public void equals_differentInstances_returnsTrue() {
        assertTrue(new ClearCommand("").equals(new ClearCommand("")));
    }

    @Test
    public void equals_differentObject_returnsFalse() {
        assertFalse(new ClearCommand("").equals(1));
        assertFalse(new ClearCommand("").equals(null));
    }

    @Test
    public void execute_returnsExpectedMessage() throws CommandException {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        CommandResult result = new ClearCommand("").execute(model);

        assertNotNull(result);
        assertEquals(ClearCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());
    }
}
