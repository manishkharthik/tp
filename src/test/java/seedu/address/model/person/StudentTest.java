package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.tag.Tag;

/**
 * Unit tests for the {@link Student} class.
 * <p>
 * Verifies that all fields, including extended student-specific attributes,
 * are properly stored and accessible through their getters.
 */
public class StudentTest {

    @Test
    public void constructor_allFieldsPresent_success() {
        Name name = new Name("John Doe");
        Phone phone = new Phone("12345678");
        Email email = new Email("john@example.com");
        Address address = new Address("123 Main St");
        Set<Tag> tags = Set.of(new Tag("CS2103"));
        List<String> subjects = List.of("Math", "Science");
        String studentClass = "10A";
        String emergencyContact = "98765432";
        String attendance = "Present";
        String paymentStatus = "Paid";
        String assignmentStatus = "Completed";

        Student student = new Student(name, phone, email, address, tags,
                subjects, studentClass, emergencyContact, attendance, paymentStatus, assignmentStatus);

        assertNotNull(student);
        assertEquals(name, student.getName());
        assertEquals(phone, student.getPhone());
        assertEquals(email, student.getEmail());
        assertEquals(address, student.getAddress());
        assertEquals(tags, student.getTags());
        assertEquals(subjects, student.getSubjects());
        assertEquals(studentClass, student.getStudentClass());
        assertEquals(emergencyContact, student.getEmergencyContact());
        assertEquals(attendance, student.getAttendanceStatus());
        assertEquals(paymentStatus, student.getPaymentStatus());
        assertEquals(assignmentStatus, student.getAssignmentStatus());
    }

    @Test
    public void constructor_nullName_throwsNullPointerException() {
        List<String> subjects = List.of("Math");
        Set<Tag> tags = Set.of(new Tag("CS2103"));

        assertThrows(NullPointerException.class, () ->
                new Student(null,
                        new Phone("12345678"),
                        new Email("john@example.com"),
                        new Address("123 Main St"),
                        tags,
                        subjects,
                        "10A",
                        "98765432",
                        "Present",
                        "Paid",
                        "Completed"));
    }
}
