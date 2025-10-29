package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code ArchiveCommand}.
 */
public class ArchiveCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToArchive = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        ArchiveCommand archiveCommand = new ArchiveCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(ArchiveCommand.MESSAGE_ARCHIVE_PERSON_SUCCESS,
                Messages.format(personToArchive));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.archivePerson(personToArchive);

        assertCommandSuccess(archiveCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        ArchiveCommand archiveCommand = new ArchiveCommand(outOfBoundIndex);

        assertCommandFailure(archiveCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToArchive = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        ArchiveCommand archiveCommand = new ArchiveCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(ArchiveCommand.MESSAGE_ARCHIVE_PERSON_SUCCESS,
                Messages.format(personToArchive));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.archivePerson(personToArchive);
        showNoPerson(expectedModel);

        assertCommandSuccess(archiveCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of TutorTrack list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        ArchiveCommand archiveCommand = new ArchiveCommand(outOfBoundIndex);

        assertCommandFailure(archiveCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_archiveLastPerson_success() {
        int lastIndex = model.getFilteredPersonList().size() - 1;
        Person personToArchive = model.getFilteredPersonList().get(lastIndex);
        ArchiveCommand archiveCommand = new ArchiveCommand(Index.fromZeroBased(lastIndex));

        String expectedMessage = String.format(ArchiveCommand.MESSAGE_ARCHIVE_PERSON_SUCCESS,
                Messages.format(personToArchive));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.archivePerson(personToArchive);

        assertCommandSuccess(archiveCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_archiveFromEmptyList_throwsCommandException() {
        Model emptyModel = new ModelManager();
        ArchiveCommand archiveCommand = new ArchiveCommand(INDEX_FIRST_PERSON);

        assertCommandFailure(archiveCommand, emptyModel, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_veryLargeIndex_throwsCommandException() {
        Index largeIndex = Index.fromOneBased(Integer.MAX_VALUE);
        ArchiveCommand archiveCommand = new ArchiveCommand(largeIndex);

        assertCommandFailure(archiveCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_archiveDoesNotAffectOtherPersons_success() {
        int initialSize = model.getFilteredPersonList().size();
        Person personToArchive = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person otherPerson = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());

        ArchiveCommand archiveCommand = new ArchiveCommand(INDEX_FIRST_PERSON);
        try {
            archiveCommand.execute(model);
        } catch (Exception e) {
            // Should not happen
        }

        assertEquals(initialSize - 1, model.getFilteredPersonList().size());
        assertTrue(model.getFilteredPersonList().contains(otherPerson));
        assertFalse(model.getFilteredPersonList().contains(personToArchive));
    }

    @Test
    public void execute_indexExactlyAtBoundary_throwsCommandException() {
        int size = model.getFilteredPersonList().size();
        Index boundaryIndex = Index.fromOneBased(size + 1);
        ArchiveCommand archiveCommand = new ArchiveCommand(boundaryIndex);

        assertCommandFailure(archiveCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        ArchiveCommand archiveFirstCommand = new ArchiveCommand(INDEX_FIRST_PERSON);
        ArchiveCommand archiveSecondCommand = new ArchiveCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(archiveFirstCommand.equals(archiveFirstCommand));

        // same values -> returns true
        ArchiveCommand archiveFirstCommandCopy = new ArchiveCommand(INDEX_FIRST_PERSON);
        assertTrue(archiveFirstCommand.equals(archiveFirstCommandCopy));

        // different types -> returns false
        assertFalse(archiveFirstCommand.equals(1));

        // null -> returns false
        assertFalse(archiveFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(archiveFirstCommand.equals(archiveSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        ArchiveCommand archiveCommand = new ArchiveCommand(targetIndex);
        String expected = ArchiveCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, archiveCommand.toString());
    }

    @Test
    public void execute_validIndex_assertionsPass() throws CommandException {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Person personToArchive = model.getFilteredPersonList().get(0);

        ArchiveCommand command = new ArchiveCommand(INDEX_FIRST_PERSON);

        // This executes all the assertion lines in execute()
        CommandResult result = command.execute(model);

        assertNotNull(result);
        assertNotNull(result.getFeedbackToUser());
        assertTrue(result.getFeedbackToUser().contains(personToArchive.getName().toString()));
    }

    @Test
    public void equals_sameCommand_assertionsPass() {
        ArchiveCommand command1 = new ArchiveCommand(INDEX_FIRST_PERSON);
        ArchiveCommand command2 = new ArchiveCommand(INDEX_FIRST_PERSON);

        // This executes the assertion in equals()
        assertTrue(command1.equals(command2));
    }

    @Test
    public void toString_validCommand_assertionsPass() {
        ArchiveCommand command = new ArchiveCommand(INDEX_FIRST_PERSON);

        // This executes the assertion in toString()
        String result = command.toString();

        assertNotNull(result);
        assertTrue(result.contains("targetIndex"));
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        ArchiveCommand cmd = new ArchiveCommand(INDEX_FIRST_PERSON);
        org.junit.jupiter.api.Assertions.assertThrows(NullPointerException.class, () -> cmd.execute(null));
    }

    @Test
    public void execute_filteredListEmpty_throwsCommandException() {
        // Ensure TutorTrack has data
        Model nonEmpty = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        // Hide everyone in the filtered view
        nonEmpty.updateFilteredPersonList(p -> false);
        assertTrue(nonEmpty.getFilteredPersonList().isEmpty());

        ArchiveCommand cmd = new ArchiveCommand(Index.fromOneBased(1));
        assertCommandFailure(cmd, nonEmpty, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assertTrue(model.getFilteredPersonList().isEmpty());
    }
}
