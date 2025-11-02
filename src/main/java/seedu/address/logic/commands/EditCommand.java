package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLASS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMERGENCY_CONTACT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAYMENT_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECTS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.attendance.AttendanceList;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.student.Student;
import seedu.address.model.subject.Subject;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing person in the TutorTrack.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the person identified "
            + "by the index number used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_CLASS + "CLASS] "
            + "[" + PREFIX_SUBJECTS + "SUBJECTS] "
            + "[" + PREFIX_EMERGENCY_CONTACT + "EMERGENCY CONTACT] "
            + "[" + PREFIX_PAYMENT_STATUS + "PAYMENT STATUS] "
            + "[" + PREFIX_ASSIGNMENT_STATUS + "ASSIGNMENT STATUS]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_CLASS + "3A "
            + PREFIX_SUBJECTS + "Maths, Science "
            + PREFIX_EMERGENCY_CONTACT + "91221231 "
            + PREFIX_PAYMENT_STATUS + "Paid "
            + PREFIX_ASSIGNMENT_STATUS + "Completed ";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the tutor track.";

    private static final Logger LOGGER = Logger.getLogger(EditCommand.class.getName());
    private final Index index;
    private final EditPersonDescriptor editPersonDescriptor;

    /**
     * @param index of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public EditCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Person> lastShownList = model.isViewingArchived()
            ? model.getFilteredArchivedPersonList()
            : model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            LOGGER.fine(() -> "Invalid index for edit: " + index.getZeroBased()
                    + " (size=" + lastShownList.size() + ")");
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        LOGGER.fine(() -> "Editing person: " + personToEdit);

        Person editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);

        boolean isDuplicate = model.isViewingArchived()
            ? (!personToEdit.isSamePerson(editedPerson) && model.hasArchivedPerson(editedPerson))
            : (!personToEdit.isSamePerson(editedPerson) && model.hasPerson(editedPerson));

        if (isDuplicate) {
            LOGGER.fine("Edit would create a duplicate person; aborting.");
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        if (model.isViewingArchived()) {
            model.setArchivedPerson(personToEdit, editedPerson);
            model.updateFilteredArchivedPersonList(PREDICATE_SHOW_ALL_PERSONS);
        } else {
            model.setPerson(personToEdit, editedPerson);
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        }

        LOGGER.fine(() -> "Edit successful: " + editedPerson);
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson)));
    }

    /** Creates and returns a {@code Person} edited with {@code editPersonDescriptor}. */
    private static Person createEditedPerson(Person personToEdit,
        EditPersonDescriptor editPersonDescriptor) throws CommandException {
        assert personToEdit != null : "personToEdit must not be null";

        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());

        boolean editingStudentFields = editPersonDescriptor.getSubjects().isPresent()
                || editPersonDescriptor.getStudentClass().isPresent()
                || editPersonDescriptor.getEmergencyContact().isPresent()
                || editPersonDescriptor.getPaymentStatus().isPresent()
                || editPersonDescriptor.getAssignmentStatus().isPresent();

        // If the existing record is a Student, or student-only fields are being edited,
        // construct a Student and preserve attendance (EditCommand does not change attendance).
        if (personToEdit instanceof Student || editingStudentFields) {
            List<Subject> updatedSubjects;
            String updatedStudentClass;
            String updatedEmergencyContact;
            String updatedPaymentStatus;
            String updatedAssignmentStatus;

            if (personToEdit instanceof Student) {
                Student s = (Student) personToEdit;
                updatedSubjects = editPersonDescriptor.getSubjects().orElse(s.getSubjects());
                updatedStudentClass = editPersonDescriptor.getStudentClass().orElse(s.getStudentClass());
                updatedEmergencyContact = editPersonDescriptor.getEmergencyContact().orElse(s.getEmergencyContact());
                updatedPaymentStatus = editPersonDescriptor.getPaymentStatus().orElse(s.getPaymentStatus());
                updatedAssignmentStatus = editPersonDescriptor.getAssignmentStatus().orElse(s.getAssignmentStatus());
                if (updatedSubjects.isEmpty()) {
                    throw new CommandException(Subject.MESSAGE_CONSTRAINTS);
                }
                Student edited = new Student(
                        updatedName,
                        updatedSubjects,
                        updatedStudentClass,
                        updatedEmergencyContact,
                        updatedPaymentStatus,
                        updatedAssignmentStatus
                );
                // Preserve existing attendance explicitly
                copyAttendance(s.getAttendanceList(), edited.getAttendanceList());
                assert edited.getAttendanceList() != null : "Student attendance list should be preserved";
                return edited;
            } else {
                // Converting a Person to a Student because student-only fields are provided.
                updatedSubjects = editPersonDescriptor.getSubjects().orElse(List.of());
                updatedStudentClass = editPersonDescriptor.getStudentClass().orElse("");
                updatedEmergencyContact = editPersonDescriptor.getEmergencyContact().orElse("");
                updatedPaymentStatus = editPersonDescriptor.getPaymentStatus().orElse("");
                updatedAssignmentStatus = editPersonDescriptor.getAssignmentStatus().orElse("");
                if (updatedSubjects.isEmpty()) {
                    throw new CommandException(Subject.MESSAGE_CONSTRAINTS);
                }
                return new Student(
                        updatedName,
                        updatedSubjects,
                        updatedStudentClass,
                        updatedEmergencyContact,
                        updatedPaymentStatus,
                        updatedAssignmentStatus
                );
            }
        }

        // Otherwise, keep as Person
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(personToEdit.getPhone());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(personToEdit.getEmail());
        Address updatedAddress = editPersonDescriptor.getAddress().orElse(personToEdit.getAddress());
        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(personToEdit.getTags());

        Person edited = new Person(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedTags);
        assert edited != null : "edited Person must not be null";
        return edited;
    }

    private static void copyAttendance(AttendanceList from, AttendanceList to) {
        if (from == null || to == null) {
            return;
        }
        from.getStudentAttendance().forEach(r -> to.markAttendance(r.getLesson(), r.getStatus()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof EditCommand)) {
            return false;
        }
        EditCommand o = (EditCommand) other;
        return index.equals(o.index) && editPersonDescriptor.equals(o.editPersonDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editPersonDescriptor", editPersonDescriptor)
                .toString();
    }

    /** Stores the details to edit the person with. */
    public static class EditPersonDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;
        private Set<Tag> tags;

        // Student-only (excluding attendance)
        private List<Subject> subjects;
        private String studentClass;
        private String emergencyContact;
        private AttendanceList attendanceList;
        private String paymentStatus;
        private String assignmentStatus;

        public EditPersonDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
            setTags(toCopy.tags);
            setSubjects(toCopy.subjects);
            setStudentClass(toCopy.studentClass);
            setEmergencyContact(toCopy.emergencyContact);
            setAttendanceList(toCopy.attendanceList);
            setPaymentStatus(toCopy.paymentStatus);
            setAssignmentStatus(toCopy.assignmentStatus);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(
                    name, phone, email, address, tags,
                    subjects, studentClass, emergencyContact, paymentStatus, assignmentStatus
            );
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setAddress(Address address) {
            this.address = address;
        }
        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        public void setSubjects(List<Subject> subjects) {
            this.subjects = (subjects == null) ? null : List.copyOf(subjects);
        }

        public Optional<List<Subject>> getSubjects() {
            return (subjects == null) ? Optional.empty() : Optional.of(List.copyOf(subjects));
        }

        public void setStudentClass(String studentClass) {
            this.studentClass = studentClass;
        }

        public Optional<String> getStudentClass() {
            return Optional.ofNullable(studentClass);
        }

        public void setEmergencyContact(String emergencyContact) {
            this.emergencyContact = emergencyContact;
        }

        public Optional<String> getEmergencyContact() {
            return Optional.ofNullable(emergencyContact);
        }

        public void setPaymentStatus(String paymentStatus) {
            this.paymentStatus = paymentStatus;
        }

        public Optional<String> getPaymentStatus() {
            return Optional.ofNullable(paymentStatus);
        }

        public void setAttendanceList(AttendanceList attendanceList) {
            this.attendanceList = attendanceList;
        }

        public Optional<AttendanceList> getAttendanceList() {
            return Optional.ofNullable(attendanceList);
        }

        public void setAssignmentStatus(String assignmentStatus) {
            this.assignmentStatus = assignmentStatus;
        }

        public Optional<String> getAssignmentStatus() {
            return Optional.ofNullable(assignmentStatus);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            if (!(other instanceof EditPersonDescriptor)) {
                return false;
            }
            EditPersonDescriptor o = (EditPersonDescriptor) other;
            return Objects.equals(name, o.name)
                    && Objects.equals(phone, o.phone)
                    && Objects.equals(email, o.email)
                    && Objects.equals(address, o.address)
                    && Objects.equals(tags, o.tags)
                    && Objects.equals(subjects, o.subjects)
                    && Objects.equals(studentClass, o.studentClass)
                    && Objects.equals(emergencyContact, o.emergencyContact)
                    && Objects.equals(paymentStatus, o.paymentStatus)
                    && Objects.equals(assignmentStatus, o.assignmentStatus);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("phone", phone)
                    .add("email", email)
                    .add("address", address)
                    .add("tags", tags)
                    .add("subjects", subjects)
                    .add("studentClass", studentClass)
                    .add("emergencyContact", emergencyContact)
                    .add("attendanceList", attendanceList)
                    .add("paymentStatus", paymentStatus)
                    .add("assignmentStatus", assignmentStatus)
                    .toString();
        }
    }
}
