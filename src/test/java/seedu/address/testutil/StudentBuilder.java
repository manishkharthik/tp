package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.attendance.AttendanceList;
import seedu.address.model.person.Name;
import seedu.address.model.student.Student;
import seedu.address.model.subject.Subject;

/**
 * A utility class to help with building {@code Student} objects for tests.
 * <p>
 * Provides default values for all student-related fields and supports
 * method chaining for flexible test data creation.
 */
public class StudentBuilder extends PersonBuilder {

    public static final String DEFAULT_NAME = "Ben Tan";
    public static final String DEFAULT_STUDENT_CLASS = "3A";
    public static final String DEFAULT_EMERGENCY_CONTACT = "91234567";
    public static final String DEFAULT_PAYMENT_STATUS = "Pending";
    public static final String DEFAULT_ASSIGNMENT_STATUS = "Not Submitted";
    public static final Subject[] DEFAULT_SUBJECTS = { new Subject("Math"), new Subject("Science") };

    private Name name;
    private List<Subject> subjects;
    private String studentClass;
    private String emergencyContact;
    private AttendanceList attendance;
    private String paymentStatus;
    private String assignmentStatus;

    /**
     * Creates a {@code StudentBuilder} with the default details.
     * <ul>
     *   <li>Name: "Ben Tan"</li>
     *   <li>Class: "3A"</li>
     *   <li>Subjects: "Math", "Science"</li>
     *   <li>Emergency contact: "91234567"</li>
     *   <li>Payment status: "Pending"</li>
     *   <li>Assignment status: "Not Submitted"</li>
     * </ul>
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
     * Initializes the {@code StudentBuilder} with the data of an existing {@code Student}.
     *
     * @param studentToCopy The student whose attributes should be copied.
     */
    public StudentBuilder(Student studentToCopy) {
        name = studentToCopy.getName();
        subjects = studentToCopy.getSubjects();
        studentClass = studentToCopy.getStudentClass();
        emergencyContact = studentToCopy.getEmergencyContact();
        attendance = studentToCopy.getAttendanceList();
        paymentStatus = studentToCopy.getPaymentStatus();
        assignmentStatus = studentToCopy.getAssignmentStatus();
    }

    /**
     * Sets the {@code Name} of the {@code Student} being built.
     *
     * @param name The student's name.
     * @return This builder instance for method chaining.
     */
    @Override
    public StudentBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses a comma-separated list of subjects and sets it for the {@code Student}.
     * <p>
     * Example: {@code withSubjects("Math, Science, English")}
     *
     * @param csvSubjects A comma-separated string of subject names.
     * @return This builder instance for method chaining.
     */
    public StudentBuilder withSubjects(String csvSubjects) {
        String[] parts = csvSubjects.split(",");
        this.subjects = new ArrayList<>();
        for (String s : parts) {
            String trimmed = s.trim();
            if (!trimmed.isEmpty()) {
                subjects.add(new Subject(trimmed));
            }
        }
        return this;
    }

    /**
     * Sets the {@code studentClass} of the {@code Student}.
     *
     * @param studentClass The student's class or group name (e.g., "4B").
     * @return This builder instance for method chaining.
     */
    public StudentBuilder withStudentClass(String studentClass) {
        this.studentClass = studentClass;
        return this;
    }

    /**
     * Sets the {@code emergencyContact} of the {@code Student}.
     *
     * @param emergencyContact The student's emergency contact number.
     * @return This builder instance for method chaining.
     */
    public StudentBuilder withEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
        return this;
    }

    /**
     * Sets the {@code AttendanceList} of the {@code Student}.
     * <p>
     * Can be used to prepopulate attendance records in tests.
     *
     * @param attendance The attendance list to assign.
     * @return This builder instance for method chaining.
     */
    public StudentBuilder withAttendance(AttendanceList attendance) {
        this.attendance = attendance;
        return this;
    }

    /**
     * Sets the {@code paymentStatus} of the {@code Student}.
     *
     * @param paymentStatus The payment status string (e.g., "Paid", "Pending").
     * @return This builder instance for method chaining.
     */
    public StudentBuilder withPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
        return this;
    }

    /**
     * Sets the {@code assignmentStatus} of the {@code Student}.
     *
     * @param assignmentStatus The assignment completion status (e.g., "Submitted", "Incomplete").
     * @return This builder instance for method chaining.
     */
    public StudentBuilder withAssignmentStatus(String assignmentStatus) {
        this.assignmentStatus = assignmentStatus;
        return this;
    }

    /**
     * Builds and returns a {@code Student} instance with the current builder state.
     *
     * @return The constructed {@code Student} object.
     */
    public Student build() {
        return new Student(name, subjects, studentClass, emergencyContact,
        paymentStatus, assignmentStatus);
    }
}
