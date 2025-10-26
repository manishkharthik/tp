package seedu.address.model.student;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * Represents a list of students enrolled in a specific subject.
 * Each StudentList instance is tied to a single subject.
 *
 * <p>Design notes:
 * - Subject is validated (non-null, non-blank).
 * - Collection is encapsulated; callers get an unmodifiable view.
 * - Methods return booleans that "sound like booleans" (e.g., add/remove/contains).
 * - Logging records no-op duplicate additions and missing removes.
 * - Assertions guard internal invariants in development.
 */
public final class StudentList {

    public static final String MESSAGE_SUBJECT_CONSTRAINTS = "Subject must be non-empty.";
    private static final Logger LOGGER = Logger.getLogger(StudentList.class.getName());

    private final List<Student> students;
    private final String subject;

    /**
     * Creates a StudentList associated with a specific subject.
     *
     * @param subject the subject this list is for (non-null, non-blank)
     * @throws NullPointerException if subject is null
     * @throws IllegalArgumentException if subject is blank
     */
    public StudentList(String subject) {
        this.subject = validateSubject(subject);
        this.students = new ArrayList<>();
        assert !this.subject.isBlank() : "subject must be non-blank";
    }

    /**
     * Adds a student to the list if not already present.
     *
     * @param student the student to add (non-null)
     * @return true if the student was added; false if it was already present
     * @throws NullPointerException if student is null
     */
    public boolean addStudent(Student student) {
        requireNonNull(student, "student must not be null");
        if (students.contains(student)) {
            LOGGER.fine(() -> "Duplicate student ignored for subject '" + subject + "': " + student);
            return false;
        }
        boolean added = students.add(student);
        assert added && students.contains(student) : "student should be present after add";
        LOGGER.fine(() -> "Added student to subject '" + subject + "': " + student);
        return true;
    }

    /**
     * Deletes a student from the list if present.
     *
     * @param student the student to delete (non-null)
     * @return true if deleted; false if not found
     * @throws NullPointerException if student is null
     */
    public boolean deleteStudent(Student student) {
        requireNonNull(student, "student must not be null");
        boolean removed = students.remove(student);
        if (removed) {
            LOGGER.fine(() -> "Removed student from subject '" + subject + "': " + student);
        } else {
            LOGGER.fine(() -> "No-op remove; student not found for subject '" + subject + "': " + student);
        }
        return removed;
    }

    /**
     * Returns true if the list already contains the student.
     *
     * @param student the student to check (non-null)
     * @return true if present; false otherwise
     * @throws NullPointerException if student is null
     */
    public boolean containsStudent(Student student) {
        requireNonNull(student, "student must not be null");
        return students.contains(student);
    }

    /**
     * Returns an copy of the list of the students, leaving the original list unmodified
     */
    public List<Student> getStudentsList() {
        return new ArrayList<>(students);
    }

    /** Returns the number of students. */
    public int size() {
        return students.size();
    }

    /** Returns true if there are no students. */
    public boolean isEmpty() {
        return students.isEmpty();
    }

    /** Returns the subject this list represents. */
    public String getSubject() {
        return subject;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Subject: ").append(subject).append(System.lineSeparator());
        for (int i = 0; i < students.size(); i++) {
            sb.append(i + 1).append(". ").append(students.get(i).getName()).append(System.lineSeparator());
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof StudentList)) {
            return false;
        }
        StudentList otherList = (StudentList) other;
        return subject.equals(otherList.subject) && students.equals(otherList.students);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subject, students);
    }

    private static String validateSubject(String raw) {
        if (raw == null) {
            throw new NullPointerException("subject must not be null");
        }
        String trimmed = raw.trim();
        if (trimmed.isEmpty()) {
            throw new IllegalArgumentException(MESSAGE_SUBJECT_CONSTRAINTS);
        }
        return trimmed;
    }
}
