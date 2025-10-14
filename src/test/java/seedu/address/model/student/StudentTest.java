package seedu.address.model.student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.attendance.AttendanceList;
import seedu.address.model.attendance.AttendanceStatus;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Unit tests for the {@link seedu.address.model.student.Student} class.
 * <p>
 * Ensures that all fields, methods, and inherited behaviors are
 * functioning as intended.
 */
public class StudentTest {

    private final Name name = new Name("John Doe");
    private final List<String> subjects = List.of("Math", "Science");
    private final String studentClass = "10A";
    private final String emergencyContact = "98765432";
    private final AttendanceList attendance = new AttendanceList();
    private final String paymentStatus = "Paid";
    private final String assignmentStatus = "Completed";
    private final LocalDateTime time = LocalDateTime.of(2025, 10, 13, 10, 0);
    {
        attendance.markAttendance(time, AttendanceStatus.PRESENT);
    }

    private final Student baseStudent = new Student(
        name, subjects, studentClass, emergencyContact,
        attendance, paymentStatus, assignmentStatus
        );

    @Test
    public void constructor_allFieldsPresent_success() {
        assertNotNull(baseStudent);
        assertEquals(name, baseStudent.getName());
        assertEquals(new Phone("000"), baseStudent.getPhone());
        assertEquals(new Email("placeholder@example.com"), baseStudent.getEmail());
        assertEquals(new Address("N/A"), baseStudent.getAddress());
        assertEquals(0, baseStudent.getTags().size()); // tags are empty
        assertEquals(subjects, baseStudent.getSubjects());
        assertEquals(studentClass, baseStudent.getStudentClass());
        assertEquals(emergencyContact, baseStudent.getEmergencyContact());
        assertEquals(attendance, baseStudent.getAttendanceStatus());
        assertEquals(paymentStatus, baseStudent.getPaymentStatus());
        assertEquals(assignmentStatus, baseStudent.getAssignmentStatus());
    }

    @Test
    public void constructor_nullName_throwsNullPointerException() {
        List<String> subjects = List.of("Math");
        Set<Tag> tags = Set.of(new Tag("CS2103"));

        assertThrows(NullPointerException.class, () ->
                new Student(
                        null,
                        subjects,
                        "10A",
                        "98765432",
                        attendance,
                        "Paid",
                        "Completed"
                ));
    }

    @Test
    public void getStudentId_valid_returnsConstantId() {
        assertEquals(1, baseStudent.getStudentId());
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        Student copy = new Student(
                name, subjects, studentClass, emergencyContact,
                attendance, paymentStatus, assignmentStatus);
        assertTrue(baseStudent.equals(copy));
    }

    @Test
    public void equals_differentValues_returnsFalse() {
        Student different = new Student(
                new Name("Jane Doe"),
                subjects, studentClass, emergencyContact,
                attendance, paymentStatus, assignmentStatus);
        assertFalse(baseStudent.equals(different));
    }

    @Test
    public void hashCode_consistentWithEquals() {
        Student copy = new Student(
                name,
                subjects, studentClass, emergencyContact,
                attendance, paymentStatus, assignmentStatus);
        assertEquals(baseStudent.hashCode(), copy.hashCode());

        Student different = new Student(
                new Name("Different"),
                subjects, studentClass, emergencyContact,
                attendance, paymentStatus, assignmentStatus);
        assertNotEquals(baseStudent.hashCode(), different.hashCode());
    }

    @Test
    public void toString_containsAllKeyFields() {
        String result = baseStudent.toString();
        assertTrue(result.contains("John Doe"));
        assertTrue(result.contains("10A"));
        assertTrue(result.contains("98765432"));
        assertTrue(result.contains("Math"));
        assertTrue(result.contains("Paid"));
        assertTrue(result.contains("Completed"));
    }
}
