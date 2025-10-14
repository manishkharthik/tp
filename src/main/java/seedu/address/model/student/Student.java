package seedu.address.model.student;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.attendance.AttendanceList;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Creates student object.
 */
public class Student extends Person {

    private List<String> subjects;
    private String studentClass;
    private String emergencyContact;
    private AttendanceList attendance;
    private String paymentStatus;
    private String assignmentStatus;
    private final int id = 1;

    /**
     * Every field must be present and not null.
     *
     * @param name Name of the student
     * @param phone Phone number of the student
     * @param email Email of the student
     * @param address Address of the student
     * @param tags Tags associated with the student
     */
    public Student(Name name, Phone phone, Email email, Address address, Set<Tag> tags, List<String> subjects,
                   String studentClass, String emergencyContact, AttendanceList attendance,
                   String paymentStatus, String assignmentStatus) {
        super(name, phone, email, address, tags);
        this.subjects = subjects;
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

    // TODO:
    public int getStudentId() {
        return this.id;
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof Person) && !(other instanceof Student)) {
            return false;
        }
        Student otherPerson = (Student) other;
        return super.equals(otherPerson) && subjects.equals(otherPerson.subjects)
                && studentClass.equals(otherPerson.studentClass)
                && emergencyContact.equals(otherPerson.emergencyContact)
                && attendance.equals(otherPerson.attendance)
                && paymentStatus.equals(otherPerson.paymentStatus)
                && assignmentStatus.equals(otherPerson.assignmentStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                super.hashCode(),
                subjects,
                studentClass,
                emergencyContact,
                attendance,
                paymentStatus,
                assignmentStatus
        );
    }

    @Override
    public String toString() {
        return super.toString() + " "
                + new ToStringBuilder(this)
                .add("subjects", subjects)
                .add("studentClass", studentClass)
                .add("emergencyContact", emergencyContact)
                .add("attendance", attendance)
                .add("paymentStatus", paymentStatus)
                .add("assignmentStatus", assignmentStatus)
                .toString();
    }

}
