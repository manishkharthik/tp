package seedu.address.model.lesson;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents a Lesson in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Lesson {
    public static final String MESSAGE_CONSTRAINTS = "Lesson name and subject must not be blank.";

    private final String name;
    private final String subject;

    /**
     * Creates a Lesson object.
     *
     * @param name The name of the lesson.
     * @param subject The subject this lesson belongs to.
     */
    public Lesson(String name, String subject) {
        requireAllNonNull(name, subject);
        if (name.trim().isEmpty() || subject.trim().isEmpty()) {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }
        // Check for null and empty values
        assert name != null : "Lesson name cannot be null";
        assert subject != null : "Subject name cannot be null";
        assert !name.trim().isEmpty() : "Lesson name cannot be blank";
        assert !subject.trim().isEmpty() : "Subject name cannot be blank";

        this.name = name.trim();
        this.subject = subject.trim();
    }


    public String getName() {
        assert name != null : "Lesson name is null";
        assert !name.trim().isEmpty() : "Lesson name is empty";
        return name;
    }

    public String getSubject() {
        assert subject != null : "Subject name is null";
        assert !subject.trim().isEmpty() : "Subject name is empty";
        return subject;
    }

    /**
     * Returns true if both lessons have the same identity.
     */
    public boolean isSameLesson(Lesson otherLesson) {
        if (otherLesson == this) {
            return true;
        }

        if (otherLesson == null) {
            return false;
        }

        // Assert that both lessons have valid names before comparison
        assert name != null : "Current lesson's name is null";
        assert subject != null : "Current lesson's subject is null";
        assert otherLesson.name != null : "Other lesson's name is null";
        assert otherLesson.subject != null : "Other lesson's subject is null";

        return otherLesson.getName().equals(getName())
                && otherLesson.getSubject().equals(getSubject());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Lesson)) {
            return false;
        }

        Lesson otherLesson = (Lesson) other;

        // Assert that both lessons have valid data before comparison
        assert name != null : "Current lesson's name is null";
        assert subject != null : "Current lesson's subject is null";
        assert otherLesson.name != null : "Other lesson's name is null";
        assert otherLesson.subject != null : "Other lesson's subject is null";

        return name.equals(otherLesson.name)
                && subject.equals(otherLesson.subject);
    }

    @Override
    public int hashCode() {
        assert name != null : "Name cannot be null for hash calculation";
        assert subject != null : "Subject cannot be null for hash calculation";
        return Objects.hash(name, subject);
    }

    @Override
    public String toString() {
        // Assert all fields are non-null before creating string representation
        assert name != null : "Lesson name is null";
        assert subject != null : "Subject name is null";

        return new ToStringBuilder(this)
                .add("name", name)
                .add("subject", subject)
                .toString();
    }
}
