package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;
import seedu.address.model.student.Student;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    private static final String PLACEHOLDER_PHONE = "000";
    private static final String PLACEHOLDER_EMAIL = "placeholder@example.com";
    private static final String PLACEHOLDER_ADDRESS = "N/A";

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;
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
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);

        // Hide placeholder values for phone, email, address
        setLabelOrHide(phone, person.getPhone().value, PLACEHOLDER_PHONE);
        setLabelOrHide(address, person.getAddress().value, PLACEHOLDER_ADDRESS);
        setLabelOrHide(email, person.getEmail().value, PLACEHOLDER_EMAIL);

        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));

        if (person instanceof Student) {
            Student s = (Student) person;
            if (studentClass != null) {
                studentClass.setText("Class: " + s.getStudentClass());
            }
            if (subjects != null) {
                subjects.setText("Subjects: " + String.join(", ", s.getSubjects().stream()
                        .map(subj -> subj.getName())
                        .toList()));
            }
            if (emergencyContact != null) {
                emergencyContact.setText("Emergency: " + s.getEmergencyContact());
            }
            if (paymentStatus != null) {
                paymentStatus.setText("Payment: " + s.getPaymentStatus());
            }
            if (assignmentStatus != null) {
                assignmentStatus.setText("Assignment: " + s.getAssignmentStatus());
            }
        } else {
            hideLabel(studentClass);
            hideLabel(subjects);
            hideLabel(emergencyContact);
            hideLabel(paymentStatus);
            hideLabel(assignmentStatus);
        }
    }

    /**
     * Sets the label text if value is not a placeholder, otherwise hides the label.
     */
    private void setLabelOrHide(Label label, String value, String placeholder) {
        if (label == null) {
            return;
        }

        if (isPlaceholder(value, placeholder)) {
            hideLabel(label);
        } else {
            label.setText(value);
            label.setVisible(true);
            label.setManaged(true);
        }
    }

    /**
     * Hides a label from display.
     */
    private void hideLabel(Label label) {
        if (label != null) {
            label.setVisible(false);
            label.setManaged(false);
        }
    }

    /**
     * Checks if a value is a placeholder.
     */
    private boolean isPlaceholder(String value, String placeholder) {
        return placeholder.equals(value);
    }
}
