package seedu.address.model.person;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Creates student object.
 */
public class Student extends Person {

    private List<String> subjects;
    private String studentClass;
    private String emergencyContact;
    private String attendance;
    private String paymentStatus;
    private String assignmentStatus;


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
                   String studentClass, String emergencyContact, String attendance,
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
    public String getAttendanceStatus() {
        return attendance;
    }
    public String getPaymentStatus() {
        return paymentStatus;
    }
    public String getAssignmentStatus() {
        return assignmentStatus;
    }
}
