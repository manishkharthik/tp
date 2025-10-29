package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.model.lesson.Lesson;

/**
 * Jackson-friendly version of {@link Lesson}.
 */
public class JsonAdaptedLesson {

    private final String name;
    private final String subject;

    /**
     * Constructs a {@code JsonAdaptedLesson} with the given details.
     */
    @JsonCreator
    public JsonAdaptedLesson(@JsonProperty("name") String name,
                             @JsonProperty("subject") String subject) {
        this.name = name;
        this.subject = subject;
    }

    /**
     * Converts a given {@code Lesson} into this class for Jackson use.
     */
    public JsonAdaptedLesson(Lesson source) {
        this.name = source.getName();
        this.subject = source.getSubject();
    }

    /**
     * Converts this Jackson-friendly adapted lesson object into the model's {@code Lesson}.
     */
    public Lesson toModelType() {
        return new Lesson(name, subject);
    }
}
