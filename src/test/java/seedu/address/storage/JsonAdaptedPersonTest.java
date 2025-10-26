package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.storage.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.student.Student;

/**
 * Unit tests for {@link JsonAdaptedPerson}.
 * <p>
 * Ensures that both {@code Person} and {@code Student} objects are correctly serialized to
 * and deserialized from JSON. This test suite covers all basic field validations and verifies
 * that missing or invalid data raises appropriate exceptions.
 */
public class JsonAdaptedPersonTest {

    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    // Student-related test data
    private static final String VALID_STUDENT_CLASS = "10A";
    private static final List<String> VALID_SUBJECTS = List.of("Math", "Physics");
    private static final String VALID_EMERGENCY_CONTACT = "91234567";
    private static final String VALID_PAYMENT_STATUS = "Paid";
    private static final String VALID_ASSIGNMENT_STATUS = "Completed";

    // New attendance string format: STATUS|LESSON_NAME|SUBJECT
    private static final List<String> VALID_ATTENDANCE_LIST = List.of("PRESENT|L1|Math");

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(BENSON);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                "person",
                INVALID_NAME,
                VALID_PHONE,
                VALID_EMAIL,
                VALID_ADDRESS,
                VALID_TAGS,
                null, null, null, null, null, null, false
        );
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                "person",
                null,
                VALID_PHONE,
                VALID_EMAIL,
                VALID_ADDRESS,
                VALID_TAGS,
                null, null, null, null, null, null, false
        );
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                "person",
                VALID_NAME,
                INVALID_PHONE,
                VALID_EMAIL,
                VALID_ADDRESS,
                VALID_TAGS,
                null, null, null, null, null, null, false
        );
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                "person",
                VALID_NAME,
                null,
                VALID_EMAIL,
                VALID_ADDRESS,
                VALID_TAGS,
                null, null, null, null, null, null, false
        );
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                "person",
                VALID_NAME,
                VALID_PHONE,
                INVALID_EMAIL,
                VALID_ADDRESS,
                VALID_TAGS,
                null, null, null, null, null, null, false
        );
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                "person",
                VALID_NAME,
                VALID_PHONE,
                null,
                VALID_ADDRESS,
                VALID_TAGS,
                null, null, null, null, null, null, false
        );
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                "person",
                VALID_NAME,
                VALID_PHONE,
                VALID_EMAIL,
                INVALID_ADDRESS,
                VALID_TAGS,
                null, null, null, null, null, null, false
        );
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                "person",
                VALID_NAME,
                VALID_PHONE,
                VALID_EMAIL,
                null,
                VALID_TAGS,
                null, null, null, null, null, null,
                false
        );
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));

        JsonAdaptedPerson person = new JsonAdaptedPerson(
                "person",
                VALID_NAME,
                VALID_PHONE,
                VALID_EMAIL,
                VALID_ADDRESS,
                invalidTags,
                null, null, null, null, null, null, false
        );

        assertThrows(IllegalValueException.class, person::toModelType);
    }

    @Test
    public void toModelType_validStudentDetails_returnsStudent() {
        JsonAdaptedPerson student = new JsonAdaptedPerson(
                "student",
                VALID_NAME,
                VALID_PHONE,
                VALID_EMAIL,
                VALID_ADDRESS,
                VALID_TAGS,
                VALID_STUDENT_CLASS,
                VALID_SUBJECTS,
                VALID_EMERGENCY_CONTACT,
                VALID_PAYMENT_STATUS,
                VALID_ASSIGNMENT_STATUS,
                VALID_ATTENDANCE_LIST,
                false
        );
        assertEquals("student", student.getType());
    }

    @Test
    public void constructor_nullType_defaultsToPerson() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                null, VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_TAGS, null, null, null, null, null, null, false
        );
        assertEquals("person", person.getType());
    }

    @Test
    public void constructor_nullIsArchived_defaultsToFalse() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                "person", VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_TAGS, null, null, null, null, null, null, null
        );
        assertFalse(person.isArchived());
    }

    @Test
    public void constructor_isArchivedTrue_returnsTrue() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                "person", VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_TAGS, null, null, null, null, null, null, true
        );
        assertTrue(person.isArchived());
    }

    @Test
    public void constructor_fromPerson_setsCorrectType() {
        JsonAdaptedPerson adaptedPerson = new JsonAdaptedPerson(BENSON);
        assertEquals("person", adaptedPerson.getType());
    }

    @Test
    public void constructor_fromStudent_setsStudentType() {
        Student student = new Student(
                new Name("Alice"),
                List.of("Math", "Science"),
                "3A",
                "91234567",
                "Paid",
                "Completed"
        );
        JsonAdaptedPerson adaptedStudent = new JsonAdaptedPerson(student);
        assertEquals("student", adaptedStudent.getType());
    }

    @Test
    public void constructor_fromStudent_preservesStudentFields() {
        Student student = new Student(
                new Name("Bob"),
                List.of("English", "History"),
                "4B",
                "98765432",
                "Unpaid",
                "Incomplete"
        );
        JsonAdaptedPerson adapted = new JsonAdaptedPerson(student);

        assertEquals("4B", adapted.getStudentClass());
        assertEquals(List.of("English", "History"), adapted.getSubjects());
        assertEquals("98765432", adapted.getEmergencyContact());
        assertEquals("Unpaid", adapted.getPaymentStatus());
        assertEquals("Incomplete", adapted.getAssignmentStatus());
    }

    @Test
    public void toModelType_emptyTags_returnsPersonWithEmptyTagSet() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                "person", VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                new ArrayList<>(), null, null, null, null, null, null, false
        );
        Person modelPerson = person.toModelType();
        assertTrue(modelPerson.getTags().isEmpty());
    }

    @Test
    public void toModelType_nullTags_returnsPersonWithEmptyTagSet() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                "person", VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                null, null, null, null, null, null, null, false
        );
        Person modelPerson = person.toModelType();
        assertTrue(modelPerson.getTags().isEmpty());
    }

    @Test
    public void toModelType_studentWithoutAttendance_success() throws Exception {
        JsonAdaptedPerson student = new JsonAdaptedPerson(
                "student", VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_TAGS, VALID_STUDENT_CLASS, VALID_SUBJECTS, VALID_EMERGENCY_CONTACT,
                VALID_PAYMENT_STATUS, VALID_ASSIGNMENT_STATUS, null, false
        );
        Person modelPerson = student.toModelType();
        assertTrue(modelPerson instanceof Student);
    }

    @Test
    public void toModelType_studentWithEmptyAttendance_success() throws Exception {
        JsonAdaptedPerson student = new JsonAdaptedPerson(
                "student", VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_TAGS, VALID_STUDENT_CLASS, VALID_SUBJECTS, VALID_EMERGENCY_CONTACT,
                VALID_PAYMENT_STATUS, VALID_ASSIGNMENT_STATUS, new ArrayList<>(), false
        );
        Person modelPerson = student.toModelType();
        assertTrue(modelPerson instanceof Student);
    }

    @Test
    public void toModelType_attendanceWithEmptyPart_throwsIllegalValueException() {
        JsonAdaptedPerson student = new JsonAdaptedPerson(
                "student", VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_TAGS, VALID_STUDENT_CLASS, VALID_SUBJECTS, VALID_EMERGENCY_CONTACT,
                VALID_PAYMENT_STATUS, VALID_ASSIGNMENT_STATUS,
                List.of("|Math|PRESENT"), false
        );
        assertThrows(IllegalValueException.class, student::toModelType);
    }

    @Test
    public void toModelType_invalidAttendanceStatus_throwsIllegalValueException() {
        JsonAdaptedPerson student = new JsonAdaptedPerson(
                "student", VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_TAGS, VALID_STUDENT_CLASS, VALID_SUBJECTS, VALID_EMERGENCY_CONTACT,
                VALID_PAYMENT_STATUS, VALID_ASSIGNMENT_STATUS,
                List.of("L1|Math|INVALID_STATUS"), false
        );
        assertThrows(IllegalValueException.class, student::toModelType);
    }

    @Test
    public void toModelType_multipleValidAttendanceRecords_success() throws Exception {
        List<String> attendanceRecords = List.of(
                "L1|Math|PRESENT",
                "L2|Science|ABSENT",
                "L3|English|LATE"
        );
        JsonAdaptedPerson student = new JsonAdaptedPerson(
                "student", VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_TAGS, VALID_STUDENT_CLASS, VALID_SUBJECTS, VALID_EMERGENCY_CONTACT,
                VALID_PAYMENT_STATUS, VALID_ASSIGNMENT_STATUS, attendanceRecords, false
        );
        Person modelPerson = student.toModelType();
        assertTrue(modelPerson instanceof Student);
        Student modelStudent = (Student) modelPerson;
        assertEquals(3, modelStudent.getAttendanceList().getStudentAttendance().size());
    }

    @Test
    public void getters_allFields_returnCorrectValues() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                "student", VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_TAGS, VALID_STUDENT_CLASS, VALID_SUBJECTS, VALID_EMERGENCY_CONTACT,
                VALID_PAYMENT_STATUS, VALID_ASSIGNMENT_STATUS, VALID_ATTENDANCE_LIST, true
        );

        assertEquals("student", person.getType());
        assertEquals(VALID_NAME, person.getName());
        assertEquals(VALID_PHONE, person.getPhone());
        assertEquals(VALID_EMAIL, person.getEmail());
        assertEquals(VALID_ADDRESS, person.getAddress());
        assertEquals(VALID_TAGS, person.getTags());
        assertEquals(VALID_STUDENT_CLASS, person.getStudentClass());
        assertEquals(VALID_SUBJECTS, person.getSubjects());
        assertEquals(VALID_EMERGENCY_CONTACT, person.getEmergencyContact());
        assertEquals(VALID_PAYMENT_STATUS, person.getPaymentStatus());
        assertEquals(VALID_ASSIGNMENT_STATUS, person.getAssignmentStatus());
        assertEquals(VALID_ATTENDANCE_LIST, person.getAttendanceList());
        assertTrue(person.isArchived());
    }

    @Test
    public void constructor_nullSubjects_handlesGracefully() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                "student", VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_TAGS, VALID_STUDENT_CLASS, null, VALID_EMERGENCY_CONTACT,
                VALID_PAYMENT_STATUS, VALID_ASSIGNMENT_STATUS, null, false
        );
        assertEquals(null, person.getSubjects());
    }

    @Test
    public void constructor_nullAttendanceList_handlesGracefully() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                "student", VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_TAGS, VALID_STUDENT_CLASS, VALID_SUBJECTS, VALID_EMERGENCY_CONTACT,
                VALID_PAYMENT_STATUS, VALID_ASSIGNMENT_STATUS, null, false
        );
        assertEquals(null, person.getAttendanceList());
    }

    @Test
    public void toModelType_personWithNoStudentFields_returnsPerson() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                "person", VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_TAGS, null, null, null, null, null, null, false
        );
        Person modelPerson = person.toModelType();
        assertFalse(modelPerson instanceof Student);
    }
}
