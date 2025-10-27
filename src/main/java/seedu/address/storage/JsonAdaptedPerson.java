package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.attendance.AttendanceList;
import seedu.address.model.attendance.AttendanceStatus;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.student.Student;
import seedu.address.model.subject.Subject;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Person} and its subclass {@link Student}.
 *
 * <p>This class is converts the data from the model layer to JSON stored on the disk.
 * It supports both {@code Person} and {@code Student} data storage.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private static final Logger LOGGER = Logger.getLogger(JsonAdaptedPerson.class.getName());

    private static final String TYPE_PERSON = "person";
    private static final String TYPE_STUDENT = "student";

    private static final String ATTENDANCE_DELIMITER_REGEX = "\\|";
    private static final int ATTENDANCE_FIELDS = 3;

    private static final String MESSAGE_INVALID_ATTENDANCE = "Invalid attendance record: %s";
    private static final String MESSAGE_INVALID_ATTENDANCE_LESSON = "Invalid attendance record (bad lesson): %s";
    private static final String MESSAGE_INVALID_ATTENDANCE_STATUS = "Invalid attendance record (bad status): %s";

    // Ensures distinction between Students and Persons can be established
    private final String type;

    // Base fields
    private final String name;
    private final String phone;
    private final String email;
    private final String address;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    // Student-only fields
    private final String studentClass;
    private final List<Subject> subjects;
    private final String emergencyContact;
    private final String paymentStatus;
    private final String assignmentStatus;
    private final List<String> attendanceList;
    private final boolean isArchived;

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given JSON properties.
     * Called by Jackson during deserialization.
     */
    @JsonCreator
    public JsonAdaptedPerson(
            @JsonProperty("type") String type,
            @JsonProperty("name") String name,
            @JsonProperty("phone") String phone,
            @JsonProperty("email") String email,
            @JsonProperty("address") String address,
            @JsonProperty("tags") List<JsonAdaptedTag> tags,
            @JsonProperty("class") String studentClass,
            @JsonProperty("subjects") List<String> subjects,
            @JsonProperty("emergencyContact") String emergencyContact,
            @JsonProperty("paymentStatus") String paymentStatus,
            @JsonProperty("assignmentStatus") String assignmentStatus,
            @JsonProperty("attendanceList") List<String> attendanceList,
            @JsonProperty("isArchived") Boolean isArchived) {

        this.type = normalizeType(type);
        this.name = trimOrNull(name);
        this.phone = trimOrNull(phone);
        this.email = trimOrNull(email);
        this.address = trimOrNull(address);

        if (tags != null) {
            this.tagged.addAll(tags);
        }

        this.studentClass = trimOrNull(studentClass);
        this.subjects = (subjects == null) ? null : new ArrayList<>(subjects.stream().map(Subject::new).toList());
        this.emergencyContact = trimOrNull(emergencyContact);
        this.paymentStatus = trimOrNull(paymentStatus);
        this.assignmentStatus = trimOrNull(assignmentStatus);
        this.attendanceList = (attendanceList == null) ? null : new ArrayList<>(attendanceList);

        this.isArchived = (isArchived != null) ? isArchived : false;

        if (type == null || type.isBlank()) {
            LOGGER.fine("Missing 'type' in JSON; defaulting to 'person'.");
        }
        if (this.isArchived) {
            LOGGER.fine("Loaded archived flag = true for JsonAdaptedPerson.");
        }

        assert this.tagged != null : "tagged list must be initialised";
    }

    /**
     * Converts a given {@link Person} (or {@link Student}) into this Jackson-friendly class.
     */
    public JsonAdaptedPerson(Person source) {
        requireNonNull(source, "source person must not be null");

        this.type = (source instanceof Student) ? TYPE_STUDENT : TYPE_PERSON;
        this.name = source.getName().fullName;
        this.phone = source.getPhone().value;
        this.email = source.getEmail().value;
        this.address = source.getAddress().value;

        source.getTags().forEach(tag -> this.tagged.add(new JsonAdaptedTag(tag)));

        if (source instanceof Student) {
            Student s = (Student) source;
            this.studentClass = s.getStudentClass();
            this.subjects = new ArrayList<>(s.getSubjects());
            this.emergencyContact = s.getEmergencyContact();
            this.paymentStatus = s.getPaymentStatus();
            this.assignmentStatus = s.getAssignmentStatus();
            this.attendanceList = new ArrayList<>();
            s.getAttendanceList().getStudentAttendance().forEach(record ->
                this.attendanceList.add(record.getLesson().getName()
                        + "|" + record.getLesson().getSubject()
                        + "|" + record.getStatus().name())
            );
        } else {
            this.studentClass = null;
            this.subjects = null;
            this.emergencyContact = null;
            this.paymentStatus = null;
            this.assignmentStatus = null;
            this.attendanceList = null;
        }

        this.isArchived = false;

        assert this.tagged != null : "tagged list must be initialised";
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("phone")
    public String getPhone() {
        return phone;
    }

    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    @JsonProperty("address")
    public String getAddress() {
        return address;
    }

    @JsonProperty("tags")
    public List<JsonAdaptedTag> getTags() {
        return Collections.unmodifiableList(tagged);
    }

    @JsonProperty("class")
    public String getStudentClass() {
        return studentClass;
    }

    @JsonProperty("subjects")
    public List<Subject> getSubjects() {
        return (subjects == null) ? null : Collections.unmodifiableList(subjects);
    }

    @JsonProperty("emergencyContact")
    public String getEmergencyContact() {
        return emergencyContact;
    }

    @JsonProperty("paymentStatus")
    public String getPaymentStatus() {
        return paymentStatus;
    }

    @JsonProperty("assignmentStatus")
    public String getAssignmentStatus() {
        return assignmentStatus;
    }

    @JsonProperty("attendanceList")
    public List<String> getAttendanceList() {
        return (attendanceList == null) ? null : Collections.unmodifiableList(attendanceList);
    }

    public boolean isArchived() {
        return isArchived;
    }


    /**
     * Converts this JSON Object into a {@link Person} or {@link Student}.
     *
     * @throws IllegalValueException if any required field is missing or invalid.
     */
    public Person toModelType() throws IllegalValueException {
        BaseFields base = parseBaseFields();
        AttendanceList modelAttendance = parseAttendance(attendanceList);

        if (isStudentLike()) {
            return buildStudent(base, modelAttendance);
        }

        return new Person(base.name, base.phone, base.email, base.address, base.tagSet);
    }

    private static String normalizeType(String raw) {
        if (raw == null || raw.isBlank()) {
            return TYPE_PERSON;
        }
        String normalized = raw.trim().toLowerCase();
        if (!TYPE_PERSON.equals(normalized) && !TYPE_STUDENT.equals(normalized)) {
            LOGGER.warning(() -> "Unknown 'type' = " + raw + "; defaulting to 'person'.");
            return TYPE_PERSON;
        }
        return normalized;
    }

    private static String trimOrNull(String s) {
        if (s == null) {
            return null;
        }
        String t = s.trim();
        return t.isEmpty() ? null : t;
    }

    private boolean isStudentLike() {
        return TYPE_STUDENT.equalsIgnoreCase(type)
                || studentClass != null
                || subjects != null
                || emergencyContact != null
                || paymentStatus != null
                || assignmentStatus != null
                || attendanceList != null;
    }

    private BaseFields parseBaseFields() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }

        Name modelName = createName(name);
        Phone modelPhone = createPhone(phone);
        Email modelEmail = createEmail(email);
        Address modelAddress = createAddress(address);

        List<Tag> modelTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            if (tag == null) {
                LOGGER.warning("Encountered null JsonAdaptedTag; skipping.");
                continue;
            }
            modelTags.add(tag.toModelType());
        }
        Set<Tag> tagSet = new HashSet<>(modelTags);

        assert modelName != null && modelPhone != null && modelEmail != null && modelAddress != null
                : "Base model fields must not be null after parsing";

        return new BaseFields(modelName, modelPhone, modelEmail, modelAddress, tagSet);
    }

    private AttendanceList parseAttendance(List<String> encoded) throws IllegalValueException {
        AttendanceList result = new AttendanceList();
        if (encoded == null) {
            return result;
        }

        for (String recordString : encoded) {
            if (recordString == null || recordString.isBlank()) {
                throw new IllegalValueException(String.format(MESSAGE_INVALID_ATTENDANCE, recordString));
            }
            String[] parts = recordString.split(ATTENDANCE_DELIMITER_REGEX, -1);
            if (parts.length != ATTENDANCE_FIELDS
                    || parts[0].isEmpty()
                    || parts[1].isEmpty()
                    || parts[2].isEmpty()) {
                throw new IllegalValueException(String.format(MESSAGE_INVALID_ATTENDANCE, recordString));
            }

            final Lesson lesson;
            try {
                lesson = new Lesson(parts[0], parts[1]);
            } catch (RuntimeException ex) {
                LOGGER.warning(() -> "Bad attendance lesson in '" + recordString + "': " + ex.getMessage());
                throw new IllegalValueException(String.format(MESSAGE_INVALID_ATTENDANCE_LESSON, recordString));
            }

            final AttendanceStatus status;
            try {
                status = AttendanceStatus.valueOf(parts[2]);
            } catch (RuntimeException ex) {
                LOGGER.warning(() -> "Bad attendance status in '" + recordString + "': " + ex.getMessage());
                throw new IllegalValueException(String.format(MESSAGE_INVALID_ATTENDANCE_STATUS, recordString));
            }

            result.markAttendance(lesson, status);
        }
        return result;
    }

    private Person buildStudent(BaseFields base, AttendanceList attendance) {
        Student student = new Student(
                base.name,
                subjects,
                studentClass,
                emergencyContact,
                paymentStatus,
                assignmentStatus);

        attendance.getStudentAttendance()
                .forEach(r -> student.getAttendanceList().markAttendance(r.getLesson(), r.getStatus()));

        assert student.getAttendanceList() != null : "Student attendance list should be initialized";
        return student;
    }

    private Name createName(String raw) throws IllegalValueException {
        try {
            return new Name(raw);
        } catch (RuntimeException ex) {
            LOGGER.fine(() -> "Invalid name: " + ex.getMessage());
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
    }

    private Phone createPhone(String raw) throws IllegalValueException {
        try {
            return new Phone(raw);
        } catch (RuntimeException ex) {
            LOGGER.fine(() -> "Invalid phone: " + ex.getMessage());
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
    }

    private Email createEmail(String raw) throws IllegalValueException {
        try {
            return new Email(raw);
        } catch (RuntimeException ex) {
            LOGGER.fine(() -> "Invalid email: " + ex.getMessage());
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
    }

    private Address createAddress(String raw) throws IllegalValueException {
        try {
            return new Address(raw);
        } catch (RuntimeException ex) {
            LOGGER.fine(() -> "Invalid address: " + ex.getMessage());
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Private internal class to access base fields more cleanly.
     */
    private static final class BaseFields {
        private final Name name;
        private final Phone phone;
        private final Email email;
        private final Address address;
        private final Set<Tag> tagSet;

        private BaseFields(Name name, Phone phone, Email email, Address address, Set<Tag> tagSet) {
            this.name = name;
            this.phone = phone;
            this.email = email;
            this.address = address;
            this.tagSet = tagSet;
        }
    }
}
