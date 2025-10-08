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
import seedu.address.model.person.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_STUDENT_SUCCESS,
                Messages.format(personToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_STUDENT_SUCCESS,
                Messages.format(personToDelete));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);
        showNoPerson(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST_PERSON);
        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST_PERSON);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        DeleteCommand deleteCommand = new DeleteCommand(targetIndex);
        String expected = DeleteCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, deleteCommand.toString());
    }

    /**
     * Tests deleting the last person in the list
     */
    @Test
    public void execute_deleteLastPersonInList_success() {
        int lastIndex = model.getFilteredPersonList().size() - 1;
        Person personToDelete = model.getFilteredPersonList().get(lastIndex);
        DeleteCommand deleteCommand = new DeleteCommand(Index.fromZeroBased(lastIndex));

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_STUDENT_SUCCESS,
                Messages.format(personToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Tests deleting when there's only one person in the list
     */
    @Test
    public void execute_deleteOnlyPerson_success() {
        Model singlePersonModel = new ModelManager();
        Person onlyPerson = model.getFilteredPersonList().get(0);
        singlePersonModel.addPerson(onlyPerson);

        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_STUDENT_SUCCESS,
                Messages.format(onlyPerson));

        Model expectedModel = new ModelManager();

        assertCommandSuccess(deleteCommand, singlePersonModel, expectedMessage, expectedModel);
        assertTrue(singlePersonModel.getFilteredPersonList().isEmpty());
    }

    /**
     * Tests deleting from an empty list
     */
    @Test
    public void execute_deleteFromEmptyList_throwsCommandException() {
        Model emptyModel = new ModelManager();
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);

        assertCommandFailure(deleteCommand, emptyModel, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Tests that deleting doesn't affect other persons
     */
    @Test
    public void execute_deleteDoesNotAffectOtherPersons_success() {
        int initialSize = model.getFilteredPersonList().size();
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person otherPerson = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());

        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);
        try {
            deleteCommand.execute(model);
        } catch (CommandException e) {
            // Should not happen
        }

        assertEquals(initialSize - 1, model.getFilteredPersonList().size());
        assertTrue(model.getFilteredPersonList().contains(otherPerson));
        assertFalse(model.getFilteredPersonList().contains(personToDelete));
    }

    /**
     * Tests deleting with very large index
     */
    @Test
    public void execute_veryLargeIndex_throwsCommandException() {
        Index largeIndex = Index.fromOneBased(Integer.MAX_VALUE);
        DeleteCommand deleteCommand = new DeleteCommand(largeIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Tests consecutive deletions
     */
    @Test
    public void execute_consecutiveDeletions_success() throws CommandException {
        int initialSize = model.getFilteredPersonList().size();

        DeleteCommand deleteFirst = new DeleteCommand(INDEX_FIRST_PERSON);
        deleteFirst.execute(model);

        assertEquals(initialSize - 1, model.getFilteredPersonList().size());

        DeleteCommand deleteSecond = new DeleteCommand(INDEX_FIRST_PERSON);
        deleteSecond.execute(model);

        assertEquals(initialSize - 2, model.getFilteredPersonList().size());
    }

    /**
     * Tests that model is not modified when deletion fails
     */
    @Test
    public void execute_invalidIndexDoesNotModifyModel_failure() {
        int initialSize = model.getFilteredPersonList().size();
        Index outOfBoundIndex = Index.fromOneBased(initialSize + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        try {
            deleteCommand.execute(model);
        } catch (CommandException e) {
            // Expected exception
        }

        assertEquals(initialSize, model.getFilteredPersonList().size());
    }

    /**
     * Tests deleting middle person in list
     */
    @Test
    public void execute_deleteMiddlePerson_success() {
        int middleIndex = model.getFilteredPersonList().size() / 2;
        Person personToDelete = model.getFilteredPersonList().get(middleIndex);
        DeleteCommand deleteCommand = new DeleteCommand(Index.fromZeroBased(middleIndex));

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_STUDENT_SUCCESS,
                Messages.format(personToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Tests deleting all persons one by one
     */
    @Test
    public void execute_deleteAllPersonsSequentially_success() throws CommandException {
        while (!model.getFilteredPersonList().isEmpty()) {
            DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);
            deleteCommand.execute(model);
        }

        assertTrue(model.getFilteredPersonList().isEmpty());
    }

    /**
     * Tests that index bounds are checked correctly
     */
    @Test
    public void execute_indexExactlyAtBoundary_throwsCommandException() {
        int size = model.getFilteredPersonList().size();
        Index boundaryIndex = Index.fromOneBased(size + 1);
        DeleteCommand deleteCommand = new DeleteCommand(boundaryIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Tests deletion maintains list order
     */
    @Test
    public void execute_deletionMaintainsListOrder_success() throws CommandException {
        List<Person> originalList = new ArrayList<>(model.getFilteredPersonList());
        Person secondPerson = originalList.get(1);
        Person thirdPerson = originalList.get(2);

        // Delete first person
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);
        deleteCommand.execute(model);

        // Verify remaining persons are in same relative order
        assertEquals(secondPerson, model.getFilteredPersonList().get(0));
        assertEquals(thirdPerson, model.getFilteredPersonList().get(1));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assertTrue(model.getFilteredPersonList().isEmpty());
    }
}
