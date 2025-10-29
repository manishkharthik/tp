package seedu.address.ui;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.student.Student;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class StudentCard extends UiPart<Region> {

    private static final String FXML = "StudentListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved
     * keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The
     *      issue on AddressBook level 4</a>
     */

    public final Student student;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label studentClass;
    @FXML
    private Label subjects;
    @FXML
    private Label emergencyContact;
    @FXML
    private Label paymentStatus;
    @FXML
    private Label assignmentStatus;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to
     * display.
     */
    public StudentCard(Student student, int displayedIndex) {
        super(FXML);
        this.student = student;
        id.setText(displayedIndex + ". ");
        name.setText(student.getName().fullName);
        if (studentClass != null) {
            studentClass.setText("Class: " + student.getStudentClass());
        }
        if (subjects != null) {
            subjects.setText("Subjects: " + String.join(", ",
                student.getSubjects().stream().map(subject -> subject.getName()).toList()));
        }
        if (emergencyContact != null) {
            emergencyContact.setText("Emergency: " + student.getEmergencyContact());
        }
        if (paymentStatus != null) {
            paymentStatus.setText("Payment: " + student.getPaymentStatus());
        }
        if (assignmentStatus != null) {
            assignmentStatus.setText("Assignment: " + student.getAssignmentStatus());
        }
    }
}
