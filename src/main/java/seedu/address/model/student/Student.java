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
    private final AttendanceList attendance;
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
     * @param attendance Attendance list of the student.
     * @param paymentStatus Current payment status.
     * @param assignmentStatus Assignment completion status.
     */
    public Student(Name name, List<String> subjects, String studentClass,
                   String emergencyContact, AttendanceList attendance,
                   String paymentStatus, String assignmentStatus) {
        super(name, new Phone("000"), new Email("placeholder@example.com"),
                new Address("N/A"), new HashSet<>());
        this.subjects = new ArrayList<>(subjects);
        this.studentClass = studentClass;
        this.emergencyContact = emergencyContact;
        this.attendance = attendance;
        this.paymentStatus = paymentStatus;
        this.assignmentStatus = assignmentStatus;
    }

    public List<String> getSubjects() {
        return new ArrayList<>(subjects);
    }

    public String getStudentClass() {
        return studentClass;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public AttendanceList getAttendanceStatus() {
        return attendance;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public String getAssignmentStatus() {
        return assignmentStatus;
    }

    public int getStudentId() {
        return id;
    }

    /**
     * Returns true if both student are the same.
     * @param otherStudent
     * @return
     */
    public boolean isSameStudent(Student otherStudent) {
        if (otherStudent == this) {
            return true;
        }

        return otherStudent != null
                && otherStudent.getName().equals(getName())
                && otherStudent.getStudentClass().equals(getStudentClass());
    }

    /**
     * Returns true if both students have the same name.
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
        return getName().equals(otherStudent.getName());
    }

    @Override
    public int hashCode() {
        return getName().hashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", getName())
                .add("subjects", subjects)
                .add("class", studentClass)
                .add("emergencyContact", emergencyContact)
                .add("attendance", attendance)
                .add("paymentStatus", paymentStatus)
                .add("assignmentStatus", assignmentStatus)
                .toString();
    }
}
