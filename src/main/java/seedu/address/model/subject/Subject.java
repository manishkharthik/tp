package seedu.address.model.subject;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.LessonList;

/**
 * Represents a Subject in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */

//@TODO: Wire up between subject and lessonlist functions
public class Subject {
    private final String name;
    private final LessonList lessonList;

    /**
     * Creates a Subject object.
     *
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

    public String getName() {
        assert name != null : "Subject name is null";
        assert !name.trim().isEmpty() : "Subject name is empty";
        return name;
    }

    public List<Lesson> getLessons() {
        assert lessonList != null : "Lesson list is null";
        return lessonList.getLessons();
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
