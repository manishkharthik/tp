package seedu.address.model.student;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.attendance.AttendanceList;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;

/**
 * Represents a student in the address book.
 */
public class Student extends Person {

    private final List<String> subjects;
    private final String studentClass;
    private final String emergencyContact;
    private final AttendanceList attendanceList;
    private final String paymentStatus;
    private final String assignmentStatus;
    private final int id = 1;

    /**
     * Creates a Student object.
     *
     * @param name Name of the student.
     * @param subjects Subjects taken by the student.
     * @param studentClass The class or group of the student.
     * @param emergencyContact Emergency contact number.
     * @param paymentStatus Current payment status.
     * @param assignmentStatus Assignment completion status.
     */
    public Student(Name name, List<String> subjects, String studentClass,
                   String emergencyContact, String paymentStatus, String assignmentStatus) {
        super(name != null ? name : new Name("INVALID"),
                new Phone("000"), new Email("placeholder@example.com"),
                new Address("N/A"), new HashSet<>());

        assert name != null : "Name cannot be null";
        assert subjects != null : "Subjects list cannot be null";
        assert studentClass != null : "Student class cannot be null";
        assert emergencyContact != null : "Emergency contact cannot be null";
        assert paymentStatus != null : "Payment status cannot be null";
        assert assignmentStatus != null : "Assignment status cannot be null";

        String trimmedStudentClass = studentClass.trim();
        String trimmedEmergencyContact = emergencyContact.trim();
        String trimmedPaymentStatus = paymentStatus.trim();
        String trimmedAssignmentStatus = assignmentStatus.trim();

        assert !trimmedStudentClass.isEmpty() : "Student class cannot be blank";
        assert !trimmedEmergencyContact.isEmpty() : "Emergency contact cannot be blank";
        assert !trimmedPaymentStatus.isEmpty() : "Payment status cannot be blank";
        assert !trimmedAssignmentStatus.isEmpty() : "Assignment status cannot be blank";

        assert !subjects.isEmpty() : "Student must have at least one subject";
        assert subjects.stream().allMatch(subject -> subject != null && !subject.trim().isEmpty())
                : "Subject entries cannot be null or blank";

        assert trimmedEmergencyContact.matches("\\d{8}") : "Emergency contact must be 8 digits";

        this.subjects = new ArrayList<>(subjects);
        this.studentClass = trimmedStudentClass;
        this.emergencyContact = trimmedEmergencyContact;
        this.attendanceList = new AttendanceList();
        this.paymentStatus = trimmedPaymentStatus;
        this.assignmentStatus = trimmedAssignmentStatus;
    }

    public List<String> getSubjects() {
        assert subjects != null : "Subjects list is null";
        assert !subjects.isEmpty() : "Subjects list is empty";
        assert subjects.stream().allMatch(subject -> subject != null && !subject.trim().isEmpty())
            : "Subject entries cannot be null or blank";
        return new ArrayList<>(subjects);
    }

    public String getStudentClass() {
        assert studentClass != null : "Student class is null";
        assert !studentClass.trim().isEmpty() : "Student class is empty";
        return studentClass;
    }

    public String getEmergencyContact() {
        assert emergencyContact != null : "Emergency contact is null";
        assert emergencyContact.matches("\\d{8}") : "Emergency contact format invalid";
        return emergencyContact;
    }

    public AttendanceList getAttendanceList() {
        assert attendanceList != null : "Attendance list is null";
        return attendanceList;
    }

    public String getPaymentStatus() {
        assert paymentStatus != null : "Payment status is null";
        assert !paymentStatus.trim().isEmpty() : "Payment status is empty";
        return paymentStatus;
    }

    public String getAssignmentStatus() {
        assert assignmentStatus != null : "Assignment status is null";
        assert !assignmentStatus.trim().isEmpty() : "Assignment status is empty";
        return assignmentStatus;
    }

    public int getStudentId() {
        assert id > 0 : "Student ID must be positive";
        return id;
    }

    /**
     * Returns true if both student are the same.
     * This is used for identifying if two student entries refer to the same student.
     * @param otherStudent The student to compare with
     * @return true if both students have the same name and class
     */
    public boolean isSameStudent(Student otherStudent) {
        if (otherStudent == this) {
            return true;
        }

        if (otherStudent == null) {
            return false;
        }

        // Assert that both students have valid name and class before comparison
        assert getName() != null : "Current student's name is null";
        assert getStudentClass() != null : "Current student's class is null";
        assert otherStudent.getName() != null : "Other student's name is null";
        assert otherStudent.getStudentClass() != null : "Other student's class is null";

        return otherStudent.getName().equals(getName())
                && otherStudent.getStudentClass().equals(getStudentClass());
    }

    /**
     * Returns true if both students have the same name.
     * This is a looser form of equality that only checks the name.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Student)) {
            return false;
        }
        Student otherStudent = (Student) other;

        // Assert that both students have valid names before comparison
        assert getName() != null : "Current student's name is null";
        assert otherStudent.getName() != null : "Other student's name is null";

        return getName().equals(otherStudent.getName());
    }

    @Override
    public int hashCode() {
        assert getName() != null : "Name cannot be null for hash calculation";
        return getName().hashCode();
    }

    @Override
    public String toString() {
        // Assert all fields are non-null before creating string representation
        assert getName() != null : "Name is null";
        assert subjects != null : "Subjects list is null";
        assert studentClass != null : "Student class is null";
        assert emergencyContact != null : "Emergency contact is null";
        assert attendanceList != null : "Attendance list is null";
        assert paymentStatus != null : "Payment status is null";
        assert assignmentStatus != null : "Assignment status is null";

        return new ToStringBuilder(this)
                .add("name", getName())
                .add("subjects", subjects)
                .add("class", studentClass)
                .add("emergencyContact", emergencyContact)
                .add("attendance", attendanceList)
                .add("paymentStatus", paymentStatus)
                .add("assignmentStatus", assignmentStatus)
                .toString();
    }
}
