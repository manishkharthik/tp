package seedu.address.model.student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.person.Name;

public class StudentListTest {

    private Student student1;
    private Student student2;
    private StudentList studentList;

    @BeforeEach
    public void setUp() {
        // Optionally, mark attendance for specific dates if needed
        student1 = new Student(
                new Name("Alice"),
                List.of("Math"),
                "10A",
                "98765432",
                "Paid",
                "Completed");

        student2 = new Student(
                new Name("Bob"),
                List.of("Science"),
                "10B",
                "87651234",
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
        String expected = "Subject: Math\n"
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

    @Test
    public void constructor_blankSubject_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new StudentList(" "));
    }

    @Test
    public void addStudent_nullStudent_throwsNullPointerException() {
        StudentList list = new StudentList("Math");
        assertThrows(NullPointerException.class, () -> list.addStudent(null));
    }

    @Test
    public void deleteStudent_nullStudent_throwsNullPointerException() {
        StudentList list = new StudentList("Math");
        assertThrows(NullPointerException.class, () -> list.deleteStudent(null));
    }

    @Test
    public void getSize_reflectsListStateCorrectly() {
        StudentList list = new StudentList("Math");
        assertEquals(0, list.size());
        list.addStudent(student1);
        assertEquals(1, list.size());
        list.deleteStudent(student1);
        assertEquals(0, list.size());
    }

    @Test
    public void isEmpty_reflectsListStateCorrectly() {
        StudentList list = new StudentList("Math");
        assertTrue(list.isEmpty());
        list.addStudent(student1);
        assertFalse(list.isEmpty());
        list.deleteStudent(student1);
        assertTrue(list.isEmpty());
    }

    @Test
    public void equals_nullAndDifferentType_returnsFalse() {
        StudentList list = new StudentList("Math");
        assertNotEquals(list, null);
        assertNotEquals(list, "string");
    }

    @Test
    public void hashCode_consistentAcrossInvocations() {
        StudentList list = new StudentList("Math");
        list.addStudent(student1);
        int firstHash = list.hashCode();
        int secondHash = list.hashCode();
        assertEquals(firstHash, secondHash);
    }

    @Test
    public void deleteStudent_notPresent_noCrash() {
        StudentList list = new StudentList("Math");
        list.addStudent(student1);
        list.deleteStudent(student2);
        assertEquals(1, list.getStudentsList().size());
    }

    @Test
    public void toString_includesSubjectAndStudentNames() {
        StudentList list = new StudentList("Science");
        list.addStudent(student1);
        String output = list.toString();
        assertTrue(output.contains("Subject: Science"));
        assertTrue(output.contains("Alice"));
    }

    @Test
    public void containsSubject_caseInsensitiveMatch() {
        StudentList list = new StudentList("Math");
        assertTrue(list.getSubject().equalsIgnoreCase("math"));
    }
}
