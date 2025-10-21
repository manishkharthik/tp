package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) and unit tests for UnarchiveCommand.
 */
public class UnarchiveCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_validIndexArchivedList_success() {
        // Archive a person first
        Person personToArchive = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        model.archivePerson(personToArchive);
        expectedModel.archivePerson(personToArchive);

        // Now unarchive
        UnarchiveCommand unarchiveCommand = new UnarchiveCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(UnarchiveCommand.MESSAGE_UNARCHIVE_PERSON_SUCCESS,
                Messages.format(personToArchive));

        expectedModel.unarchivePerson(personToArchive);

        assertCommandSuccess(unarchiveCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexArchivedList_throwsCommandException() {
        // Archive a person
        Person personToArchive = model.getFilteredPersonList().get(0);
        model.archivePerson(personToArchive);

        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredArchivedPersonList().size() + 1);
        UnarchiveCommand unarchiveCommand = new UnarchiveCommand(outOfBoundIndex);

        assertCommandFailure(unarchiveCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_emptyArchivedList_throwsCommandException() {
        // No archived persons
        UnarchiveCommand unarchiveCommand = new UnarchiveCommand(INDEX_FIRST_PERSON);

        assertCommandFailure(unarchiveCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_multipleArchivedPersons_success() {
        // Archive multiple persons
        Person firstPerson = model.getFilteredPersonList().get(0);
        Person secondPerson = model.getFilteredPersonList().get(1);

        model.archivePerson(firstPerson);
        model.archivePerson(secondPerson);

        expectedModel.archivePerson(firstPerson);
        expectedModel.archivePerson(secondPerson);

        // Unarchive the second person
        UnarchiveCommand unarchiveCommand = new UnarchiveCommand(INDEX_SECOND_PERSON);

        String expectedMessage = String.format(UnarchiveCommand.MESSAGE_UNARCHIVE_PERSON_SUCCESS,
                Messages.format(secondPerson));

        expectedModel.unarchivePerson(secondPerson);

        assertCommandSuccess(unarchiveCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        UnarchiveCommand unarchiveFirstCommand = new UnarchiveCommand(INDEX_FIRST_PERSON);
        UnarchiveCommand unarchiveSecondCommand = new UnarchiveCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(unarchiveFirstCommand.equals(unarchiveFirstCommand));

        // same values -> returns true
        UnarchiveCommand unarchiveFirstCommandCopy = new UnarchiveCommand(INDEX_FIRST_PERSON);
        assertTrue(unarchiveFirstCommand.equals(unarchiveFirstCommandCopy));

        // different types -> returns false
        assertFalse(unarchiveFirstCommand.equals(1));

        // null -> returns false
        assertFalse(unarchiveFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(unarchiveFirstCommand.equals(unarchiveSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        UnarchiveCommand unarchiveCommand = new UnarchiveCommand(targetIndex);
        String expected = UnarchiveCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertTrue(unarchiveCommand.toString().contains("targetIndex"));
    }

    @Test
    public void execute_validModel_assertionsPass() throws CommandException {
        Person personToArchive = model.getFilteredPersonList().get(0);
        model.archivePerson(personToArchive);

        UnarchiveCommand command = new UnarchiveCommand(INDEX_FIRST_PERSON);

        // This executes all assertion lines in execute()
        CommandResult result = command.execute(model);

        assertNotNull(result);
        assertNotNull(result.getFeedbackToUser());
        assertTrue(result.getFeedbackToUser().contains(personToArchive.getName().toString()));
    }

    @Test
    public void hashCode_validCommand_assertionsPass() {
        UnarchiveCommand command = new UnarchiveCommand(INDEX_FIRST_PERSON);

        // This executes assertions in hashCode()
        int hash = command.hashCode();

        assertEquals(INDEX_FIRST_PERSON.hashCode(), hash);
    }

    private void assertNotNull(Object obj) {
        assertTrue(obj != null);
    }
}
