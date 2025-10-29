package seedu.address.model.subject;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.LessonList;
import seedu.address.model.lesson.exceptions.DuplicateLessonException;
import seedu.address.model.lesson.exceptions.LessonNotFoundException;

/**
 * Represents a Subject in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */

//@TODO: Wire up between subject and lessonlist functions
public class Subject {

    public static final String MESSAGE_CONSTRAINTS = "Subject names should not be empty";
    public static final String VALIDATION_REGEX = ".+";

    private final String name;
    private final LessonList lessonList;

    /**
     * Creates a Subject object.
     * Initializes an empty LessonList for it.
     * @param name The name of the subject.
     */
    public Subject(String name) {
        requireNonNull(name);

        // Check for null and empty values
        assert name != null : "Subject name cannot be null";
        assert !name.trim().isEmpty() : "Subject name cannot be blank";

        this.name = name.trim();
        this.lessonList = new LessonList(name.trim());
    }

    /**
     * Creates a Subject with both a name and an existing LessonList.
     * @param name The name of the subject.
     * @param lessonList The lesson list associated with the subject.
     */
    public Subject(String name, LessonList lessonList) {
        requireNonNull(name);
        requireNonNull(lessonList);

        assert name != null : "Subject name cannot be null";
        assert !name.trim().isEmpty() : "Subject name cannot be blank";

        this.name = name;
        this.lessonList = lessonList;
    }

    public String getName() {
        assert name != null : "Subject name is null";
        assert !name.trim().isEmpty() : "Subject name is empty";
        return name;
    }

    public LessonList getLessons() {
        assert lessonList != null : "Lesson list is null";
        return lessonList;
    }

    /**
     * Adds a lesson to this subject.
     * The lesson must not already exist in the subject.
     *
     * @param lesson The lesson to add
     * @throws DuplicateLessonException if an equivalent lesson already exists
     */
    public void addLesson(Lesson lesson) {
        requireNonNull(lesson);
        lessonList.addLesson(lesson);
    }

    /**
     * Removes a lesson from this subject.
     * The lesson must exist in the subject.
     *
     * @param lesson The lesson to remove
     * @throws LessonNotFoundException if the lesson does not exist
     */
    public void removeLesson(Lesson lesson) {
        requireNonNull(lesson);
        lessonList.deleteLesson(lesson);
    }

    /**
     * Returns true if the subject contains the given lesson.
     */
    public boolean containsLesson(Lesson lesson) {
        requireNonNull(lesson);
        return lessonList.contains(lesson);
    }

    /**
     * Returns true if both subjects have the same name.
     */
    public boolean isSameSubject(Subject otherSubject) {
        if (otherSubject == this) {
            return true;
        }

        if (otherSubject == null) {
            return false;
        }

        // Assert that both subjects have valid names before comparison
        assert name != null : "Current subject's name is null";
        assert otherSubject.name != null : "Other subject's name is null";

        return otherSubject.getName().equals(getName());
    }

    /**
     * Returns true if the subject name equals the given string, ignoring case.
     */
    public boolean equalsIgnoreCase(String otherName) {
        if (otherName == null) {
            return false;
        }

        // Assert that both names are valid before comparison
        assert name != null : "Current subject's name is null";
        assert otherName != null : "Other subject's name is null";

        return name.equalsIgnoreCase(otherName);
    }

    /**
     * Returns true if a given string is a valid subject name.
     */
    public static boolean isValidSubjectName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Subject)) {
            return false;
        }

        Subject otherSubject = (Subject) other;

        // Assert that both subjects have valid names before comparison
        assert name != null : "Current subject's name is null";
        assert otherSubject.name != null : "Other subject's name is null";

        return name.equals(otherSubject.name)
                && lessonList.equals(otherSubject.lessonList);
    }

    @Override
    public int hashCode() {
        assert name != null : "Name cannot be null for hash calculation";
        return Objects.hash(name, lessonList);
    }

    @Override
    public String toString() {
        // Assert all fields are non-null before creating string representation
        assert name != null : "Subject name is null";
        assert lessonList != null : "Lesson list is null";

        return new ToStringBuilder(this)
                .add("name", name)
                .add("lessons", lessonList)
                .toString();
    }
}
