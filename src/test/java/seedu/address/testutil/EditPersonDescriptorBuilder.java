package seedu.address.testutil;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.attendance.AttendanceList;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building {@code EditPersonDescriptor} objects for tests.
 * <p>
 * Provides a fluent API for setting up various fields (including student-related ones)
 * to simulate edit operations in {@code EditCommandTest}.
 */
public class EditPersonDescriptorBuilder {

    private EditPersonDescriptor descriptor;

    /**
     * Creates a new empty {@code EditPersonDescriptorBuilder}.
     */
    public EditPersonDescriptorBuilder() {
        descriptor = new EditPersonDescriptor();
    }

    /**
     * Creates an {@code EditPersonDescriptorBuilder} initialized with a copy of the given descriptor.
     *
     * @param descriptor The {@code EditPersonDescriptor} to copy.
     */
    public EditPersonDescriptorBuilder(EditPersonDescriptor descriptor) {
        this.descriptor = new EditPersonDescriptor(descriptor);
    }

    /**
     * Creates an {@code EditPersonDescriptorBuilder} initialized with the data from the given {@code Person}.
     *
     * @param person The {@code Person} whose data should be used to populate the descriptor.
     */
    public EditPersonDescriptorBuilder(Person person) {
        descriptor = new EditPersonDescriptor();
        descriptor.setName(person.getName());
        descriptor.setPhone(person.getPhone());
        descriptor.setEmail(person.getEmail());
        descriptor.setAddress(person.getAddress());
        descriptor.setTags(person.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditPersonDescriptor} that we are building.
     *
     * @param name A {@code String} representing the person's name.
     * @return This builder, for method chaining.
     */
    public EditPersonDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditPersonDescriptor} that we are building.
     *
     * @param phone A {@code String} representing the person's phone number.
     * @return This builder, for method chaining.
     */
    public EditPersonDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditPersonDescriptor} that we are building.
     *
     * @param email A {@code String} representing the person's email address.
     * @return This builder, for method chaining.
     */
    public EditPersonDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditPersonDescriptor} that we are building.
     *
     * @param address A {@code String} representing the person's residential address.
     * @return This builder, for method chaining.
     */
    public EditPersonDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Parses and sets {@code tags} for the {@code EditPersonDescriptor} that we are building.
     *
     * @param tags One or more {@code String} tag names to be converted into a {@code Set<Tag>}.
     * @return This builder, for method chaining.
     */
    public EditPersonDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    /**
     * Parses a comma-separated list of subjects and sets it for the {@code EditPersonDescriptor}.
     *
     * @param csvSubjects A comma-separated string of subjects (e.g. "Math, Science").
     * @return This builder, for method chaining.
     */
    public EditPersonDescriptorBuilder withSubjects(String csvSubjects) {
        String[] parts = csvSubjects.split(",");
        List<String> subjects = new ArrayList<>();
        for (String s : parts) {
            String trimmed = s.trim();
            if (!trimmed.isEmpty()) {
                subjects.add(trimmed);
            }
        }
        descriptor.setSubjects(subjects);
        return this;
    }

    /**
     * Sets the {@code studentClass} of the {@code EditPersonDescriptor}.
     *
     * @param studentClass The class name or code to set (e.g. "3A").
     * @return This builder, for method chaining.
     */
    public EditPersonDescriptorBuilder withStudentClass(String studentClass) {
        descriptor.setStudentClass(studentClass);
        return this;
    }

    /**
     * Sets the {@code emergencyContact} of the {@code EditPersonDescriptor}.
     *
     * @param emergencyContact The emergency contact number to set.
     * @return This builder, for method chaining.
     */
    public EditPersonDescriptorBuilder withEmergencyContact(String emergencyContact) {
        descriptor.setEmergencyContact(emergencyContact);
        return this;
    }

    /**
     * Sets the {@code attendance} record of the {@code EditPersonDescriptor}.
     *
     * @param attendance The {@code AttendanceList} object containing attendance data.
     * @return This builder, for method chaining.
     */
    public EditPersonDescriptorBuilder withAttendance(AttendanceList attendance) {
        descriptor.setAttendance(attendance);
        return this;
    }

    /**
     * Sets the {@code paymentStatus} of the {@code EditPersonDescriptor}.
     *
     * @param paymentStatus The payment status string to set (e.g. "Paid", "Pending").
     * @return This builder, for method chaining.
     */
    public EditPersonDescriptorBuilder withPaymentStatus(String paymentStatus) {
        descriptor.setPaymentStatus(paymentStatus);
        return this;
    }

    /**
     * Sets the {@code assignmentStatus} of the {@code EditPersonDescriptor}.
     *
     * @param assignmentStatus The assignment submission status (e.g. "Submitted", "Not Submitted").
     * @return This builder, for method chaining.
     */
    public EditPersonDescriptorBuilder withAssignmentStatus(String assignmentStatus) {
        descriptor.setAssignmentStatus(assignmentStatus);
        return this;
    }

    /**
     * Builds and returns the {@code EditPersonDescriptor}.
     *
     * @return The fully constructed {@code EditPersonDescriptor}.
     */
    public EditPersonDescriptor build() {
        return descriptor;
    }
}
