package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListArchiveCommand.
 */
public class ListArchiveCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsArchivedList() {
        Person personToArchive = model.getFilteredPersonList().get(0);
        model.archivePerson(personToArchive);
        expectedModel.archivePerson(personToArchive);

        expectedModel.updateFilteredArchivedPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);

        // Create the FULL CommandResult with showArchived=true
        CommandResult expectedCommandResult = new CommandResult(
                ListArchiveCommand.MESSAGE_SUCCESS, false, false, true);

        assertCommandSuccess(new ListArchiveCommand(), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_emptyArchivedList_showsEmptyList() {
        expectedModel.updateFilteredArchivedPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);

        CommandResult expectedCommandResult = new CommandResult(
                ListArchiveCommand.MESSAGE_SUCCESS, false, false, true);

        assertCommandSuccess(new ListArchiveCommand(), model, expectedCommandResult, expectedModel);

        assertEquals(0, model.getFilteredArchivedPersonList().size());
    }

    @Test
    public void execute_multipleArchivedPersons_showsAllArchivedPersons() {
        Person firstPerson = model.getFilteredPersonList().get(0);
        Person secondPerson = model.getFilteredPersonList().get(1);

        model.archivePerson(firstPerson);
        model.archivePerson(secondPerson);

        expectedModel.archivePerson(firstPerson);
        expectedModel.archivePerson(secondPerson);
        expectedModel.updateFilteredArchivedPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);

        CommandResult expectedCommandResult = new CommandResult(
                ListArchiveCommand.MESSAGE_SUCCESS, false, false, true);

        assertCommandSuccess(new ListArchiveCommand(), model, expectedCommandResult, expectedModel);

        assertEquals(2, model.getFilteredArchivedPersonList().size());
    }

    @Test
    public void execute_commandResultShowsArchived_success() {
        CommandResult result = new ListArchiveCommand().execute(model);

        assertTrue(result.isShowArchived());
        assertFalse(result.isShowHelp());
        assertFalse(result.isExit());
    }

    @Test
    public void equals() {
        ListArchiveCommand listArchiveCommand1 = new ListArchiveCommand();
        ListArchiveCommand listArchiveCommand2 = new ListArchiveCommand();

        // same object -> returns true
        assertTrue(listArchiveCommand1.equals(listArchiveCommand1));

        // same type -> returns true
        assertTrue(listArchiveCommand1.equals(listArchiveCommand2));

        // different types -> returns false
        assertFalse(listArchiveCommand1.equals(1));

        // null -> returns false
        assertFalse(listArchiveCommand1.equals(null));

        // different command type -> returns false
        assertFalse(listArchiveCommand1.equals(new ListCommand()));
    }

    @Test
    public void execute_validModel_assertionsPass() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        // This will execute line 19 (assert model != null)
        CommandResult result = new ListArchiveCommand().execute(model);

        // This covers lines 23-25 (assert result checks)
        assertNotNull(result);
        assertTrue(result.isShowArchived());
        assertEquals(ListArchiveCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());
    }

    @Test
    public void hashCode_validCommand_returnsHashCode() {
        ListArchiveCommand command = new ListArchiveCommand();

        // This covers line 46 (assert COMMAND_WORD != null in hashCode)
        int hashCode = command.hashCode();

        assertEquals(ListArchiveCommand.COMMAND_WORD.hashCode(), hashCode);
    }

    @Test
    public void execute_afterArchiving_showsArchivedPersons() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        // Archive some persons
        Person person1 = model.getFilteredPersonList().get(0);
        Person person2 = model.getFilteredPersonList().get(1);
        model.archivePerson(person1);
        model.archivePerson(person2);

        // Execute command - this tests all assertion lines
        CommandResult result = new ListArchiveCommand().execute(model);

        // Verify the assertions held true
        assertNotNull(result);
        assertTrue(result.isShowArchived());
        assertEquals(2, model.getFilteredArchivedPersonList().size());
    }

    @Test
    public void execute_twice() {
        Model m = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        // archive one person
        Person p = m.getFilteredPersonList().get(0);
        m.archivePerson(p);

        CommandResult r1 = new ListArchiveCommand().execute(m);
        CommandResult r2 = new ListArchiveCommand().execute(m);

        assertTrue(r1.isShowArchived());
        assertTrue(r2.isShowArchived());
        assertEquals(1, m.getFilteredArchivedPersonList().size());
    }
}
