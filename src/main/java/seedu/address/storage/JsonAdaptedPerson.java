package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Student;
import seedu.address.model.tag.Tag;

@JsonInclude(JsonInclude.Include.NON_NULL) // omit nulls for backward compat
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
    private final String attendance;
    private final String paymentStatus;
    private final String assignmentStatus;

    @JsonCreator
    public JsonAdaptedPerson(
            @JsonProperty("type") String type,
            @JsonProperty("name") String name,
            @JsonProperty("phone") String phone,
            @JsonProperty("email") String email,
            @JsonProperty("address") String address,
            @JsonProperty("tagged") List<JsonAdaptedTag> tagged,
            @JsonProperty("class") String studentClass,
            @JsonProperty("subjects") List<String> subjects,
            @JsonProperty("emergencyContact") String emergencyContact,
            @JsonProperty("attendance") String attendance,
            @JsonProperty("paymentStatus") String paymentStatus,
            @JsonProperty("assignmentStatus") String assignmentStatus
    ) {
        this.type = (type == null) ? "person" : type;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
        this.studentClass = studentClass;
        this.subjects = (subjects == null) ? null : new ArrayList<>(subjects);
        this.emergencyContact = emergencyContact;
        this.attendance = attendance;
        this.paymentStatus = paymentStatus;
        this.assignmentStatus = assignmentStatus;
    }

    public JsonAdaptedPerson(Person source) {
        // base
        this.type = (source instanceof Student) ? "student" : "person";
        this.name = source.getName().fullName;
        this.phone = source.getPhone().value;
        this.email = source.getEmail().value;
        this.address = source.getAddress().value;
        source.getTags().forEach(tag -> this.tagged.add(new JsonAdaptedTag(tag)));

        if (source instanceof Student) {
            Student s = (Student) source;
            this.studentClass = s.getStudentClass();          // String
            this.subjects = new ArrayList<>(s.getSubjects()); // List<String>
            this.emergencyContact = s.getEmergencyContact();  // String
            this.attendance = s.getAttendanceStatus();        // String
            this.paymentStatus = s.getPaymentStatus();        // String
            this.assignmentStatus = s.getAssignmentStatus();  // String
        } else {
            this.studentClass = null;
            this.subjects = null;
            this.emergencyContact = null;
            this.attendance = null;
            this.paymentStatus = null;
            this.assignmentStatus = null;
        }
    }

    // Jackson getters
    @JsonProperty("type") public String getType() { return type; }
    @JsonProperty("name") public String getName() { return name; }
    @JsonProperty("phone") public String getPhone() { return phone; }
    @JsonProperty("email") public String getEmail() { return email; }
    @JsonProperty("address") public String getAddress() { return address; }
    @JsonProperty("tagged") public List<JsonAdaptedTag> getTagged() { return tagged; }

    @JsonProperty("class") public String getStudentClass() { return studentClass; }
    @JsonProperty("subjects") public List<String> getSubjects() { return subjects; }
    @JsonProperty("emergencyContact") public String getEmergencyContact() { return emergencyContact; }
    @JsonProperty("attendance") public String getAttendance() { return attendance; }
    @JsonProperty("paymentStatus") public String getPaymentStatus() { return paymentStatus; }
    @JsonProperty("assignmentStatus") public String getAssignmentStatus() { return assignmentStatus; }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     */
    public Person toModelType() throws IllegalValueException {
        // === Validation and construction for base fields ===
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        final Email modelEmail = new Email(email);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        final Address modelAddress = new Address(address);

        final List<Tag> modelTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            modelTags.add(tag.toModelType());
        }
        final Set<Tag> modelTagSet = new HashSet<>(modelTags);

        // === Decide which model to build ===
        boolean hasStudentBits =
                "student".equalsIgnoreCase(type)
                        || studentClass != null
                        || subjects != null
                        || emergencyContact != null
                        || attendance != null
                        || paymentStatus != null
                        || assignmentStatus != null;

        if (hasStudentBits) {
            // NOTE: Adjust this constructor call to your actual Student class signature
            Student student = new Student(
                    modelName,
                    modelPhone,
                    modelEmail,
                    modelAddress,
                    modelTagSet,
                    subjects,
                    studentClass,
                    emergencyContact,
                    attendance,
                    paymentStatus,
                    assignmentStatus
            );
            return student;
        }

        return new Person(modelName, modelPhone, modelEmail, modelAddress, modelTagSet);
    }
}