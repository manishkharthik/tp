package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.time.LocalDateTime;
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
import seedu.address.model.attendance.AttendanceList;
import seedu.address.model.attendance.AttendanceStatus;
import seedu.address.model.person.Person;
import seedu.address.model.student.Student;
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
    public void execute_convertPersonToStudent_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder()
                .withStudentClass("2A")
                .withSubjects("English")
                .withEmergencyContact("888")
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
        assertEquals(List.of("English"), s.getSubjects());
        assertEquals("888", s.getEmergencyContact());
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
        assertEquals(List.of("Math", "Physics"), edited.getSubjects());
        assertEquals("Unpaid", edited.getPaymentStatus());
        assertEquals(existingStudent.getName(), edited.getName());
    }

    @Test
    public void execute_editStudentAttendance_success() throws Exception {
        Index studentIndex = Index.fromOneBased(model.getFilteredPersonList().size());
        AttendanceList newAttendance = new AttendanceList();
        newAttendance.markAttendance(LocalDateTime.now(), AttendanceStatus.PRESENT);

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder()
                .withAttendance(newAttendance)
                .build();

        EditCommand editCommand = new EditCommand(studentIndex, descriptor);
        editCommand.execute(model);

        Student edited = (Student) model.getFilteredPersonList().get(studentIndex.getZeroBased());
        assertEquals(newAttendance, edited.getAttendanceStatus());
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
        AttendanceList attendanceList = new AttendanceList();
        attendanceList.markAttendance(LocalDateTime.now(), AttendanceStatus.PRESENT);

        EditPersonDescriptor descriptorA = new EditPersonDescriptorBuilder()
                .withSubjects("Math,Science")
                .withStudentClass("3A")
                .withEmergencyContact("91234567")
                .withAttendance(attendanceList)
                .withPaymentStatus("Paid")
                .withAssignmentStatus("Submitted")
                .build();

        EditPersonDescriptor descriptorB = new EditPersonDescriptorBuilder()
                .withSubjects("Math,Science")
                .withStudentClass("3A")
                .withEmergencyContact("91234567")
                .withAttendance(attendanceList)
                .withPaymentStatus("Paid")
                .withAssignmentStatus("Submitted")
                .build();

        assertTrue(descriptorA.equals(descriptorB));
        assertEquals(descriptorA.hashCode(), descriptorB.hashCode());
    }

    @Test
    public void equals_detectsDifferencesInStudentFields() {
        AttendanceList attendanceList1 = new AttendanceList();
        attendanceList1.markAttendance(LocalDateTime.now(), AttendanceStatus.PRESENT);

        AttendanceList attendanceList2 = new AttendanceList();
        attendanceList2.markAttendance(LocalDateTime.now(), AttendanceStatus.ABSENT);

        EditPersonDescriptor base = new EditPersonDescriptorBuilder();
        base.setSubjects(List.of("Math", "Science"));
        base.setStudentClass("3A");
        base.setEmergencyContact("98765");
        base.setAttendance(attendanceList1);
        base.setPaymentStatus("Paid");
        base.setAssignmentStatus("Submitted");

        // different subjects
        EditPersonDescriptor diffSubjects = new EditPersonDescriptorBuilder(base);
        diffSubjects.setSubjects(List.of("Math", "Science", "English"));
        assertFalse(base.equals(diffSubjects));

        // different class
        EditPersonDescriptor diffClass = new EditPersonDescriptorBuilder(base);
        diffClass.setStudentClass("4B");
        assertFalse(base.equals(diffClass));

        // different contact
        EditPersonDescriptor diffContact = new EditPersonDescriptorBuilder(base);
        diffContact.setEmergencyContact("00000");
        assertFalse(base.equals(diffContact));

        // different attendance
        EditPersonDescriptor diffAttendance = new EditPersonDescriptorBuilder(base);
        diffAttendance.setAttendance(attendanceList2);
        assertFalse(base.equals(diffAttendance));

        // different payment
        EditPersonDescriptor diffPayment = new EditPersonDescriptorBuilder(base);
        diffPayment.setPaymentStatus("Unpaid");
        assertFalse(base.equals(diffPayment));

        // different assignment
        EditPersonDescriptor diffAssignment = new EditPersonDescriptorBuilder(base);
        diffAssignment.setAssignmentStatus("Missing");
        assertFalse(base.equals(diffAssignment));
    }

    @Test
    public void toString_includesAllStudentFields() {
        AttendanceList attendanceList = new AttendanceList();
        attendanceList.markAttendance(LocalDateTime.now(), AttendanceStatus.PRESENT);

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder()
                .withSubjects("Math,Science")
                .withStudentClass("3A")
                .withEmergencyContact("98765432")
                .withAttendance(attendanceList)
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