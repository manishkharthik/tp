package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.subject.Subject;

/**
 * Jackson-friendly version of {@link Subject}.
 */
public class JsonAdaptedSubject {

    private final String name;

    /**
     * Constructs a {@code JsonAdaptedSubject} with the given subject name.
     */
    @JsonCreator
    public JsonAdaptedSubject(@JsonProperty("name") String name) {
        this.name = name;
    }

    /**
     * Converts a given {@code Subject} into this class for Jackson use.
     */
    public JsonAdaptedSubject(Subject source) {
        name = source.getName();
    }

    @JsonValue
    public String getSubjectName() {
        return this.name;
    }

    /**
     * Converts this Jackson-friendly adapted subject object into the model's {@code Subject} object.
     */
    public Subject toModelType() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException("Subject name is missing!");
        }
        return new Subject(name);
    }
}
