package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.lesson.LessonList;
import seedu.address.model.subject.Subject;

/**
 * Jackson-friendly version of {@link Subject}.
 */
public class JsonAdaptedSubject {

    private final String name;
    private final JsonAdaptedLessonList lessonList;

    /**
     * Constructs a {@code JsonAdaptedSubject} with the given details.
     * Supports both object and string-based subject JSON formats.
     */
    @JsonCreator
    public JsonAdaptedSubject(
            @JsonProperty("name") String name,
            @JsonProperty("lessonList") JsonAdaptedLessonList lessonList) {

        // If Jackson reads a plain string "Math" instead of an object,
        // name will be null but lessonList will also be null.
        // So we treat it as a fallback to the older format.
        if (name == null && lessonList == null) {
            // Legacy case — the raw string "Math"
            this.name = null;
            this.lessonList = null;
        } else {
            this.name = name;
            this.lessonList = lessonList;
        }
    }

    /**
     * Creates an adapter from the model's {@code Subject}.
     */
    public JsonAdaptedSubject(Subject source) {
        this.name = source.getName();
        this.lessonList = new JsonAdaptedLessonList(source.getLessons());
    }

    /**
     * Returns this subject as a plain string when serialized
     * (for backward compatibility with older JSON files).
     */
    @JsonValue
    public Object getJsonValue() {
        if (lessonList == null) {
            // Old format: just return the name string
            return this.name;
        }

        // New structured format
        return new Object() {
            public final String name = JsonAdaptedSubject.this.name;
            public final JsonAdaptedLessonList lessonList = JsonAdaptedSubject.this.lessonList;
        };
    }

    /**
     * Converts this Jackson-friendly adapted subject object into the model's {@code Subject}.
     */
    public Subject toModelType() throws IllegalValueException {
        if (name == null && lessonList == null) {
            throw new IllegalValueException("Subject name is missing!");
        }

        String subjectName = this.name != null ? this.name : "Unnamed Subject";

        LessonList modelLessonList;
        if (this.lessonList != null) {
            modelLessonList = this.lessonList.toModelType();
        } else {
            modelLessonList = new LessonList(subjectName);
        }

        return new Subject(subjectName, modelLessonList);
    }
}
