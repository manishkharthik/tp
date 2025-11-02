package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.student.Student;
import seedu.address.model.subject.Subject;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.StudentBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * EditCommand.
 */
public class EditCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Student testStudent = new StudentBuilder()
                .withName("Ben")
                .withStudentClass("3A")
                .withSubjects("Math,Science")
                .withEmergencyContact("98765432")
                .withPaymentStatus("Paid")
                .withAssignmentStatus("Submitted")
                .build();
        model.addPerson(testStudent);
    }

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Person editedPerson = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(editedPerson).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_convertPersonToStudent_success() throws Exception {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder()
                .withStudentClass("2A")
                .withSubjects("English")
                .withEmergencyContact("88888881")
                .withPaymentStatus("Pending")
                .withAssignmentStatus("Not Submitted")
                .build();

        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, descriptor);
        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS,
                Messages.format(firstPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        editCommand.execute(model);
        Person edited = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        assertTrue(edited instanceof Student);
        Student s = (Student) edited;
        assertEquals("2A", s.getStudentClass());
        assertEquals(List.of(new Subject("English")), s.getSubjects());
        assertEquals("88888881", s.getEmergencyContact());
        assertEquals("Pending", s.getPaymentStatus());
    }

    @Test
    public void execute_editExistingStudent_success() throws Exception {
        // The Student we manually added in setUp is the last one in the list
        Index studentIndex = Index.fromOneBased(model.getFilteredPersonList().size());
        Student existingStudent = (Student) model.getFilteredPersonList().get(studentIndex.getZeroBased());

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder()
                .withStudentClass("3B")
                .withSubjects("Math,Physics")
                .withPaymentStatus("Unpaid")
                .build();

        EditCommand editCommand = new EditCommand(studentIndex, descriptor);
        editCommand.execute(model);

        Student edited = (Student) model.getFilteredPersonList().get(studentIndex.getZeroBased());
        assertEquals("3B", edited.getStudentClass());
        assertEquals(List.of(new Subject("Math"), new Subject("Physics")), edited.getSubjects());
        assertEquals("Unpaid", edited.getPaymentStatus());
        assertEquals(existingStudent.getName(), edited.getName());
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, new EditPersonDescriptor());
        Person editedPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicatePersonUnfilteredList_failure() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(firstPerson).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_PERSON, descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void equals_withStudentFields() {
        EditPersonDescriptor descriptorA = new EditPersonDescriptorBuilder()
                .withName("Alice")
                .withStudentClass("4C")
                .withSubjects("Chemistry,Math")
                .build();

        EditPersonDescriptor descriptorB = new EditPersonDescriptorBuilder()
                .withName("Alice")
                .withStudentClass("4C")
                .withSubjects("Chemistry,Math")
                .build();

        EditPersonDescriptor descriptorDifferent = new EditPersonDescriptorBuilder()
                .withName("Alice")
                .withStudentClass("4D")
                .withSubjects("Biology")
                .build();

        assertTrue(descriptorA.equals(descriptorB));
        assertFalse(descriptorA.equals(descriptorDifferent));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        EditCommand editCommand = new EditCommand(index, editPersonDescriptor);
        String expected = EditCommand.class.getCanonicalName() + "{index=" + index + ", editPersonDescriptor="
                + editPersonDescriptor + "}";
        assertEquals(expected, editCommand.toString());
    }

    @Test
    public void equals_includesAllStudentFields() {
        EditPersonDescriptor descriptorA = new EditPersonDescriptorBuilder()
                .withSubjects("Math,Science")
                .withStudentClass("3A")
                .withEmergencyContact("91234567")
                .withPaymentStatus("Paid")
                .withAssignmentStatus("Submitted")
                .build();

        EditPersonDescriptor descriptorB = new EditPersonDescriptorBuilder()
                .withSubjects("Math,Science")
                .withStudentClass("3A")
                .withEmergencyContact("91234567")
                .withPaymentStatus("Paid")
                .withAssignmentStatus("Submitted")
                .build();

        assertTrue(descriptorA.equals(descriptorB));
    }

    @Test
    public void toString_includesAllStudentFields() {
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder()
                .withSubjects("Math,Science")
                .withStudentClass("3A")
                .withEmergencyContact("98765432")
                .withPaymentStatus("Paid")
                .withAssignmentStatus("Submitted")
                .build();

        String output = descriptor.toString();
        assertTrue(output.contains("Math"));
        assertTrue(output.contains("3A"));
        assertTrue(output.contains("98765432"));
        assertTrue(output.contains("Paid"));
        assertTrue(output.contains("Submitted"));
    }

}
