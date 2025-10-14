package seedu.address.storage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.attendance.AttendanceList;
import seedu.address.model.attendance.AttendanceStatus;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.student.Student;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Person} and its subclass {@link Student}.
 * <p>
 * This class serves as an intermediate representation between the JSON data stored on disk
 * and the model layer. It supports both {@code Person} and {@code Student} objects through
 * the use of a discriminator field ("type") and conditional inclusion of student-specific attributes.
 * <p>
 * Fields annotated with {@link JsonProperty} map directly to JSON keys when serializing and deserializing.
 * Any null values are omitted for backward compatibility with earlier AddressBook versions.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    // Discriminator to preserve type information
    private final String type;

    // Base fields
    private final String name;
    private final String phone;
    private final String email;
    private final String address;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    // Student-only fields
    // "class" is a reserved word in Java; map JSON name "class" to Java field studentClass
    private final String studentClass;
    private final List<String> subjects;
    private final String emergencyContact;
    private final String paymentStatus;
    private final String assignmentStatus;
    private final List<String> attendanceList;

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given JSON properties.
     * <p>
     * This constructor is called by Jackson during deserialization.
     * The "type" field determines whether to later reconstruct a {@link Person} or {@link Student} model object.
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
            @JsonProperty("attendanceList") List<String> attendanceList) {
        this.type = (type == null) ? "person" : type;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        if (tags != null) {
            this.tagged.addAll(tags);
        }
        this.studentClass = studentClass;
        this.subjects = (subjects == null) ? null : new ArrayList<>(subjects);
        this.emergencyContact = emergencyContact;
        this.paymentStatus = paymentStatus;
        this.assignmentStatus = assignmentStatus;
        this.attendanceList = (attendanceList == null) ? null : new ArrayList<>(attendanceList);
    }

    /**
     * Converts a given {@link Person} (or {@link Student}) model object into this Jackson-friendly class.
     */
    public JsonAdaptedPerson(Person source) {
        this.type = (source instanceof Student) ? "student" : "person";
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
            this.attendanceList = null; // Or extract from Student if needed
        } else {
            this.studentClass = null;
            this.subjects = null;
            this.emergencyContact = null;
            this.paymentStatus = null;
            this.assignmentStatus = null;
            this.attendanceList = null;
        }
    }

    // Jackson getters
    @JsonProperty("type") public String getType() {
        return type;
    }
    @JsonProperty("name") public String getName() {
        return name;
    }
    @JsonProperty("phone") public String getPhone() {
        return phone;
    }
    @JsonProperty("email") public String getEmail() {
        return email;
    }
    @JsonProperty("address") public String getAddress() {
        return address;
    }
    @JsonProperty("tags")
    public List<JsonAdaptedTag> getTags() {
        return tagged;
    }
    @JsonProperty("class") public String getStudentClass() {
        return studentClass;
    }
    @JsonProperty("subjects") public List<String> getSubjects() {
        return subjects;
    }
    @JsonProperty("emergencyContact") public String getEmergencyContact() {
        return emergencyContact;
    }
    @JsonProperty("paymentStatus") public String getPaymentStatus() {
        return paymentStatus;
    }
    @JsonProperty("assignmentStatus") public String getAssignmentStatus() {
        return assignmentStatus;
    }
    @JsonProperty("attendanceList") public List<String> getAttendanceList() {
        return attendanceList;
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@link Person} or {@link Student} object.
     * <p>
     * Validation checks are performed to ensure all required base fields are present.
     * If any student-related fields or a "student" type discriminator are detected,
     * a {@code Student} instance is constructed instead of a {@code Person}.
     *
     * @return a fully constructed {@code Person} or {@code Student} instance.
     * @throws IllegalValueException if any required field is missing or invalid.
     */
    public Person toModelType() throws IllegalValueException {

        // === Validation and construction for base fields ===
        if (name == null) {
            throw new IllegalValueException(String.format(
                    MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        final Name modelName;
        try {
            modelName = new Name(name);
        } catch (IllegalArgumentException e) {
            throw new IllegalValueException(e.getMessage());
        }

        if (phone == null) {
            throw new IllegalValueException(String.format(
                    MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        final Phone modelPhone;
        try {
            modelPhone = new Phone(phone);
        } catch (IllegalArgumentException e) {
            throw new IllegalValueException(e.getMessage());
        }

        if (email == null) {
            throw new IllegalValueException(String.format(
                    MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        final Email modelEmail;
        try {
            modelEmail = new Email(email);
        } catch (IllegalArgumentException e) {
            throw new IllegalValueException(e.getMessage());
        }

        if (address == null) {
            throw new IllegalValueException(String.format(
                    MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        final Address modelAddress;
        try {
            modelAddress = new Address(address);
        } catch (IllegalArgumentException e) {
            throw new IllegalValueException(e.getMessage());
        }

        final List<Tag> modelTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            modelTags.add(tag.toModelType());
        }
        final Set<Tag> modelTagSet = new HashSet<>(modelTags);

        AttendanceList modelAttendanceList = new AttendanceList();
        if (attendanceList != null) {
            for (String recordString : attendanceList) {
                try {
                    // Assume format is "STATUS,DATE_TIME" e.g. "PRESENT,2025-10-14T10:00"
                    String[] parts = recordString.split(",", 2);
                    AttendanceStatus status = AttendanceStatus.valueOf(parts[0]);
                    LocalDateTime dateTime = LocalDateTime.parse(parts[1]);
                    modelAttendanceList.markAttendance(dateTime, status);
                } catch (Exception e) {
                    throw new IllegalValueException("Invalid attendance record: " + recordString);
                }
            }
        }

        // === Determine whether to construct a Student ===
        boolean hasStudentBits =
                "student".equalsIgnoreCase(type)
                        || studentClass != null
                        || subjects != null
                        || emergencyContact != null
                        || paymentStatus != null
                        || assignmentStatus != null
                        || attendanceList != null;
        if (hasStudentBits) {
            return new Student(
                    modelName,
                    modelPhone,
                    modelEmail,
                    modelAddress,
                    modelTagSet,
                    subjects,
                    studentClass,
                    emergencyContact,
                    modelAttendanceList,
                    paymentStatus,
                    assignmentStatus
            );
        }
        return new Person(modelName, modelPhone, modelEmail, modelAddress, modelTagSet);
    }
}
