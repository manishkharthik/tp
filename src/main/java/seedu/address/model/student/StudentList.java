package seedu.address.model.student;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a list of students enrolled in a specific subject.
 * Each StudentList instance is tied to a single subject.
 */
public class StudentList {

    private final List<Student> students;
    private final String subject;

    /**
     * Creates a StudentList associated with a specific subject.
     *
     * @param subject the subject this list is for
     */
    public StudentList(String subject) {
        Objects.requireNonNull(subject);
        this.subject = subject;
        this.students = new ArrayList<>();
    }

    /**
     * Adds a student to the list if not already present.
     *
     * @param student the student to add
     */
    public void addStudent(Student student) {
        Objects.requireNonNull(student);
        if (!students.contains(student)) {
            students.add(student);
        }
    }

    /**
     * Deletes a student from the list if present.
     *
     * @param student the student to remove
     */
    public void deleteStudent(Student student) {
        Objects.requireNonNull(student);
        students.remove(student);
    }

    /**
     * Returns a copy of the list of students.
     *
     * @return new list containing all students
     */
    public List<Student> getStudentsList() {
        return new ArrayList<>(students);
    }

    /**
     * Returns the subject this list represents.
     */
    public String getSubject() {
        return subject;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("Subject: " + subject + "\n");
        for (int i = 0; i < students.size(); i++) {
            s.append(i + 1)
                    .append(". ")
                    .append(students.get(i).getName())
                    .append("\n");
        }
        return s.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof StudentList)) {
            return false;
        }
        StudentList otherList = (StudentList) other;
        return subject.equals(otherList.subject)
                && students.equals(otherList.students);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subject, students);
    }
}
