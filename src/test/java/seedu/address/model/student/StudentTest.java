package seedu.address.model.student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.person.Name;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.subject.Subject;

/**
 * Unit tests for the {@link Student} class.
 * Ensures that constructor validation works as expected.
 */
public class StudentTest {

    private final Name validName = new Name("John Doe");
    private final List<Subject> validSubjects = List.of(new Subject("Math"), new Subject("Science"));
    private final String validClass = "10A";
    private final String validEmergencyContact = "98765432";
    private final String validPaymentStatus = "Paid";
    private final String validAssignmentStatus = "Completed";
    private Student baseStudent;

    @BeforeEach
    public void setUp() {
        baseStudent = createValidStudent();
    }

    private Student createValidStudent() {
        return new Student(
                validName,
                validSubjects,
                validClass,
                validEmergencyContact,
                validPaymentStatus,
                validAssignmentStatus);
    }

    @Test
    public void constructor_validInputs_createsStudent() {
        Student student = createValidStudent();
        assertNotNull(student);
        assertEquals(validName, student.getName());
        assertEquals(validSubjects, student.getSubjects());
        assertEquals(validClass, student.getStudentClass());
        assertEquals(validEmergencyContact, student.getEmergencyContact());
        assertEquals(validPaymentStatus, student.getPaymentStatus());
        assertEquals(validAssignmentStatus, student.getAssignmentStatus());
    }

    @Test
    public void constructor_nullName_throwsAssertionError() {
        assertThrows(AssertionError.class, () -> new Student(
                null,
                validSubjects,
                validClass,
                validEmergencyContact,
                validPaymentStatus,
                validAssignmentStatus));
    }

    @Test
    public void constructor_nullSubjects_throwsAssertionError() {
        assertThrows(AssertionError.class, () -> new Student(
                validName,
                null,
                validClass,
                validEmergencyContact,
                validPaymentStatus,
                validAssignmentStatus));
    }

    @Test
    public void constructor_subjectsContainingNull_throwsAssertionError() {
        List<Subject> subjectsWithNull = new ArrayList<>(validSubjects);
        subjectsWithNull.add(null);
        assertThrows(AssertionError.class, () -> new Student(
                validName,
                subjectsWithNull,
                validClass,
                validEmergencyContact,
                validPaymentStatus,
                validAssignmentStatus));
    }

    @Test
    public void constructor_emptySubjectsList_throwsAssertionError() {
        assertThrows(AssertionError.class, () -> new Student(
                validName,
                new ArrayList<>(),
                validClass,
                validEmergencyContact,
                validPaymentStatus,
                validAssignmentStatus));
    }

    @Test
    public void constructor_subjectsContainingBlankString_throwsAssertionError() {
        List<Subject> subjectsWithBlank = new ArrayList<>(validSubjects);
        assertThrows(AssertionError.class, () -> {
            subjectsWithBlank.add(new Subject("   ")); // move this inside!
            new Student(
                    validName,
                    subjectsWithBlank,
                    validClass,
                    validEmergencyContact,
                    validPaymentStatus,
                    validAssignmentStatus);
        });
    }

    @Test
    public void constructor_emergencyContactNonDigits_throwsAssertionError() {
        assertThrows(AssertionError.class, () -> new Student(
                validName,
                validSubjects,
                validClass,
                "1234567a",
                validPaymentStatus,
                validAssignmentStatus));
    }

    @Test
    public void constructor_nullClass_throwsAssertionError() {
        assertThrows(AssertionError.class, () -> new Student(
                validName,
                validSubjects,
                null,
                validEmergencyContact,
                validPaymentStatus,
                validAssignmentStatus));
    }

    @Test
    public void constructor_blankClass_throwsAssertionError() {
        assertThrows(AssertionError.class, () -> new Student(
                validName,
                validSubjects,
                "   ",
                validEmergencyContact,
                validPaymentStatus,
                validAssignmentStatus));
    }

    @Test
    public void constructor_nullEmergencyContact_throwsAssertionError() {
        assertThrows(AssertionError.class, () -> new Student(
                validName,
                validSubjects,
                validClass,
                null,
                validPaymentStatus,
                validAssignmentStatus));
    }

    @Test
    public void constructor_invalidEmergencyContact_throwsAssertionError() {
        assertThrows(AssertionError.class, () -> new Student(
                validName,
                validSubjects,
                validClass,
                "123", // Less than 8 digits
                validPaymentStatus,
                validAssignmentStatus));
    }

    @Test
    public void constructor_blankPaymentStatus_defaultsToUnpaid() {
        Student student = new Student(
                validName,
                validSubjects,
                validClass,
                validEmergencyContact,
                "   ",
                validAssignmentStatus);
        assertEquals("Unpaid", student.getPaymentStatus());
    }

    @Test
    public void constructor_nullAssignmentStatus_throwsAssertionError() {
        Student student = new Student(
                validName,
                validSubjects,
                validClass,
                validEmergencyContact,
                validPaymentStatus,
                null);
        assertEquals("Incomplete", student.getAssignmentStatus());
    }

    @Test
    public void constructor_blankAssignmentStatus_throwsAssertionError() {
        Student student = new Student(
                validName,
                validSubjects,
                validClass,
                validEmergencyContact,
                validPaymentStatus,
                "   ");
        assertEquals("Incomplete", student.getAssignmentStatus());
    }

    @Test
    public void getStudentId_valid_returnsConstantId() {
        assertEquals(1, baseStudent.getStudentId());
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        Student copy = new Student(
                validName,
                validSubjects,
                validClass,
                validEmergencyContact,
                validPaymentStatus,
                validAssignmentStatus);
        assertTrue(baseStudent.equals(copy));
    }

    @Test
    public void equals_differentValues_returnsFalse() {
        Student different = new Student(
                new Name("Jane Doe"),
                validSubjects,
                validClass,
                validEmergencyContact,
                validPaymentStatus,
                validAssignmentStatus);
        assertFalse(baseStudent.equals(different));
    }

    @Test
    public void equals_differentTypes_returnsFalse() {
        UniquePersonList list = new UniquePersonList();
        assertFalse(baseStudent.equals(list));
    }

    @Test
    public void isSameStudent_sameInstance_returnsTrue() {
        assertTrue(baseStudent.isSameStudent(baseStudent));
    }

    @Test
    public void isSameStudent_null_returnsFalse() {
        assertFalse(baseStudent.isSameStudent(null));
    }

    @Test
    public void isSameStudent_sameNameDifferentClass_returnsFalse() {
        Student sameNameDifferentClass = new Student(
                validName,
                validSubjects,
                "10B",
                validEmergencyContact,
                validPaymentStatus,
                validAssignmentStatus);
        assertFalse(baseStudent.isSameStudent(sameNameDifferentClass));
    }

    @Test
    public void isSameStudent_sameNameSameClass_returnsTrue() {
        Student duplicate = new Student(
                validName,
                validSubjects,
                validClass,
                validEmergencyContact,
                validPaymentStatus,
                validAssignmentStatus);
        assertTrue(baseStudent.isSameStudent(duplicate));
    }

    @Test
    public void toString_containsAllFields() {
        String result = baseStudent.toString();
        assertTrue(result.contains(validName.toString()));
        assertTrue(result.contains(validClass));
        assertTrue(result.contains("Math"));
        assertTrue(result.contains("Science"));
        assertTrue(result.contains(validEmergencyContact));
        assertTrue(result.contains(validPaymentStatus));
        assertTrue(result.contains(validAssignmentStatus));
    }

    @Test
    public void hashCode_validStudent_computesHashCode() {
        int expected = validName.hashCode();
        assertEquals(expected, baseStudent.hashCode());
    }

    @Test
    public void equals_invalidType_returnsFalse() {
        Object invalidType = "Not a student";
        assertFalse(baseStudent.equals(invalidType));
    }

    @Test
    public void isSameStudent_otherHasDifferentName_returnsFalse() {
        Student otherStudent = new Student(
                new Name("Different Name"),
                validSubjects,
                validClass,
                validEmergencyContact,
                validPaymentStatus,
                validAssignmentStatus);
        assertFalse(baseStudent.isSameStudent(otherStudent));
    }

    @Test
    public void isSameStudent_otherHasDifferentClass_returnsFalse() {
        Student otherStudent = new Student(
                validName,
                validSubjects,
                "Different Class",
                validEmergencyContact,
                validPaymentStatus,
                validAssignmentStatus);
        assertFalse(baseStudent.isSameStudent(otherStudent));
    }

    // Test cases for getter method assertions
    @Test
    public void getSubjects_validState_returnsSubjects() {
        List<Subject> subjects = baseStudent.getSubjects();
        assertEquals(validSubjects, subjects);
    }

    @Test
    public void getSubjectNames_validSubjects_returnsListOfNames() {
        List<String> subjectNames = baseStudent.getSubjectNames();
        assertEquals(List.of("Math", "Science"), subjectNames);
    }

    @Test
    public void getStudentClass_validState_returnsClass() {
        String studentClass = baseStudent.getStudentClass();
        assertEquals(validClass, studentClass);
    }

    @Test
    public void getEmergencyContact_validState_returnsContact() {
        String contact = baseStudent.getEmergencyContact();
        assertEquals(validEmergencyContact, contact);
    }

    @Test
    public void getPaymentStatus_validState_returnsStatus() {
        String status = baseStudent.getPaymentStatus();
        assertEquals(validPaymentStatus, status);
    }

    @Test
    public void getAssignmentStatus_validState_returnsStatus() {
        String status = baseStudent.getAssignmentStatus();
        assertEquals(validAssignmentStatus, status);
    }
}
