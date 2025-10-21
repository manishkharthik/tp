package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.attendance.AttendanceList;
import seedu.address.model.person.Name;
import seedu.address.model.student.Student;

/**
 * A utility class to help with building {@code Student} objects for tests.
 */
public class StudentBuilder extends PersonBuilder {

    public static final String DEFAULT_NAME = "Ben Tan";
    public static final String DEFAULT_STUDENT_CLASS = "3A";
    public static final String DEFAULT_EMERGENCY_CONTACT = "91234567";
    public static final String DEFAULT_PAYMENT_STATUS = "Pending";
    public static final String DEFAULT_ASSIGNMENT_STATUS = "Not Submitted";
    public static final String[] DEFAULT_SUBJECTS = { "Math", "Science" };

    private Name name;
    private List<String> subjects;
    private String studentClass;
    private String emergencyContact;
    private AttendanceList attendance;
    private String paymentStatus;
    private String assignmentStatus;

    /**
     * Creates a {@code StudentBuilder} with the default details.
     */
    public StudentBuilder() {
        name = new Name(DEFAULT_NAME);
        subjects = new ArrayList<>(Arrays.asList(DEFAULT_SUBJECTS));
        studentClass = DEFAULT_STUDENT_CLASS;
        emergencyContact = DEFAULT_EMERGENCY_CONTACT;
        attendance = new AttendanceList();
        paymentStatus = DEFAULT_PAYMENT_STATUS;
        assignmentStatus = DEFAULT_ASSIGNMENT_STATUS;
    }

    /**
     * Initializes the StudentBuilder with the data of {@code studentToCopy}.
     */
    public StudentBuilder(Student studentToCopy) {
        name = studentToCopy.getName();
        subjects = new ArrayList<>(studentToCopy.getSubjects());
        studentClass = studentToCopy.getStudentClass();
        emergencyContact = studentToCopy.getEmergencyContact();
        attendance = studentToCopy.getAttendanceStatus();
        paymentStatus = studentToCopy.getPaymentStatus();
        assignmentStatus = studentToCopy.getAssignmentStatus();
    }

    public StudentBuilder withSubjects(String csvSubjects) {
        String[] parts = csvSubjects.split(",");
        this.subjects = new ArrayList<>();
        for (String s : parts) {
            String trimmed = s.trim();
            if (!trimmed.isEmpty()) {
                subjects.add(trimmed);
            }
        }
        return this;
    }

    public StudentBuilder withStudentClass(String studentClass) {
        this.studentClass = studentClass;
        return this;
    }

    public StudentBuilder withEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
        return this;
    }

    public StudentBuilder withAttendance(AttendanceList attendance) {
        this.attendance = attendance;
        return this;
    }

    public StudentBuilder withPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
        return this;
    }

    public StudentBuilder withAssignmentStatus(String assignmentStatus) {
        this.assignmentStatus = assignmentStatus;
        return this;
    }

    public Student build() {
        return new Student(name, subjects, studentClass, emergencyContact,
                attendance, paymentStatus, assignmentStatus);
    }
}