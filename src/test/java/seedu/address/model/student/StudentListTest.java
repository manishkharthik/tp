package seedu.address.model.student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.attendance.AttendanceList;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

public class StudentListTest {

    private Student student1;
    private Student student2;
    private StudentList studentList;

    @BeforeEach
    public void setUp() {
        AttendanceList attendanceList1 = new AttendanceList();
        AttendanceList attendanceList2 = new AttendanceList();
        // Optionally, mark attendance for specific dates if needed
        student1 = new Student(
                new Name("Alice"),
                new Phone("12345678"),
                new Email("alice@example.com"),
                new Address("123 Main St"),
                Set.of(new Tag("CS2103")),
                List.of("Math"),
                "10A",
                "98765432",
                attendanceList1,
                "Paid",
                "Completed");

        student2 = new Student(
                new Name("Bob"),
                new Phone("87654321"),
                new Email("bob@example.com"),
                new Address("456 Side St"),
                Set.of(new Tag("CS2103")),
                List.of("Science"),
                "10B",
                "87651234",
                attendanceList2,
                "Unpaid",
                "Incomplete");

        studentList = new StudentList("Math");
    }

    @Test
    public void constructor_validSubject_success() {
        assertNotNull(studentList);
        assertEquals("Math", studentList.getSubject());
        assertTrue(studentList.getStudentsList().isEmpty());
    }

    @Test
    public void addStudent_successfullyAdds() {
        studentList.addStudent(student1);
        assertEquals(1, studentList.getStudentsList().size());
        assertTrue(studentList.getStudentsList().contains(student1));
    }

    @Test
    public void addStudent_duplicateIgnored() {
        studentList.addStudent(student1);
        studentList.addStudent(student1); // duplicate
        assertEquals(1, studentList.getStudentsList().size());
    }

    @Test
    public void deleteStudent_existingStudent_removed() {
        studentList.addStudent(student1);
        studentList.deleteStudent(student1);
        assertFalse(studentList.getStudentsList().contains(student1));
        assertTrue(studentList.getStudentsList().isEmpty());
    }

    @Test
    public void deleteStudent_nonExistent_noChange() {
        studentList.addStudent(student1);
        studentList.deleteStudent(student2);
        assertEquals(1, studentList.getStudentsList().size());
    }

    @Test
    public void getStudentsList_returnsCopy() {
        studentList.addStudent(student1);
        List<Student> retrieved = studentList.getStudentsList();
        retrieved.clear();
        // internal list should not be affected
        assertEquals(1, studentList.getStudentsList().size());
    }

    @Test
    public void toString_correctFormatting_returnsExpectedString() {
        studentList.addStudent(student1);
        studentList.addStudent(student2);
        String expected =
                "Subject: Math\n"
                        + "1. Alice\n"
                        + "2. Bob\n";
        assertEquals(expected, studentList.toString());
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        StudentList copy = new StudentList("Math");
        copy.addStudent(student1);
        studentList.addStudent(student1);
        assertEquals(studentList, copy);
    }

    @Test
    public void equals_differentSubject_returnsFalse() {
        StudentList different = new StudentList("Science");
        different.addStudent(student1);
        studentList.addStudent(student1);
        assertNotEquals(studentList, different);
    }

    @Test
    public void equals_differentStudents_returnsFalse() {
        StudentList different = new StudentList("Math");
        different.addStudent(student2);
        studentList.addStudent(student1);
        assertNotEquals(studentList, different);
    }

    @Test
    public void hashCode_consistentWithEquals() {
        StudentList copy = new StudentList("Math");
        copy.addStudent(student1);
        studentList.addStudent(student1);
        assertEquals(studentList.hashCode(), copy.hashCode());
    }

    @Test
    public void constructor_nullSubject_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new StudentList(null));
    }
}
