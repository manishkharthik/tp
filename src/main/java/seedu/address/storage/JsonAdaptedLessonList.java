package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.model.lesson.LessonList;

/**
 * Jackson-friendly version of {@link LessonList}.
 */
public class JsonAdaptedLessonList {

    private final List<JsonAdaptedLesson> lessons = new ArrayList<>();
    private final String subject;

    /**
     * Constructs a {@code JsonAdaptedLessonList} with the given details.
     */
    @JsonCreator
    public JsonAdaptedLessonList(@JsonProperty("lessons") List<JsonAdaptedLesson> lessons,
                                 @JsonProperty("subject") String subject) {
        if (lessons != null) {
            this.lessons.addAll(lessons);
        }
        this.subject = subject;
    }

    /**
     * Converts a given {@code LessonList} into this class for Jackson use.
     */
    public JsonAdaptedLessonList(LessonList source) {
        this.subject = source.getSubject();
        this.lessons.addAll(source.getLessons().stream()
                .map(JsonAdaptedLesson::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted lesson list object into the model's {@code LessonList}.
     */
    public LessonList toModelType() {
        LessonList lessonList = new LessonList(subject);
        for (JsonAdaptedLesson jsonLesson : lessons) {
            lessonList.addLesson(jsonLesson.toModelType());
        }
        return lessonList;
    }
}
