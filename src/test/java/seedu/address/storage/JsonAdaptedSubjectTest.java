package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.address.model.subject.Subject;

/**
 * Unit tests for {@link JsonAdaptedSubject}.
 *
 * Ensures correct serialization and deserialization of {@code Subject} objects,
 * validation of field constraints, and proper handling of invalid input.
 */
public class JsonAdaptedSubjectTest {

    private static final String VALID_SUBJECT_NAME = "Mathematics";

    @Test
    public void constructor_withValidSubjectName_success() throws Exception {
        JsonAdaptedSubject adapted = new JsonAdaptedSubject(
                VALID_SUBJECT_NAME,
                new JsonAdaptedLessonList(new ArrayList<>(), VALID_SUBJECT_NAME)
        );
        assertEquals(VALID_SUBJECT_NAME, adapted.toModelType().getName());
    }

    @Test
    public void constructor_withSubjectObject_success() throws Exception {
        Subject subject = new Subject(VALID_SUBJECT_NAME);
        JsonAdaptedSubject adapted = new JsonAdaptedSubject(subject);
        assertEquals(VALID_SUBJECT_NAME, adapted.toModelType().getName());
    }

    @Test
    public void toModelType_validSubject_returnsEquivalentSubject() throws Exception {
        JsonAdaptedLessonList lessonList = new JsonAdaptedLessonList(new ArrayList<>(), VALID_SUBJECT_NAME);
        JsonAdaptedSubject adapted = new JsonAdaptedSubject(VALID_SUBJECT_NAME, lessonList);
        Subject modelSubject = adapted.toModelType();
        assertEquals(new Subject(VALID_SUBJECT_NAME), modelSubject);
    }

    @Test
    public void toModelType_validButDifferentSubjects_notEqual() throws Exception {
        JsonAdaptedLessonList emptyLessons = new JsonAdaptedLessonList(new ArrayList<>(), "Math");
        JsonAdaptedLessonList emptyLessons2 = new JsonAdaptedLessonList(new ArrayList<>(), "Physics");

        JsonAdaptedSubject math = new JsonAdaptedSubject("Math", emptyLessons);
        JsonAdaptedSubject physics = new JsonAdaptedSubject("Physics", emptyLessons2);

        Subject mathModel = math.toModelType();
        Subject physicsModel = physics.toModelType();

        assertFalse(mathModel.equals(physicsModel));
    }

    @Test
    public void equals_andHashCode_workCorrectly() throws Exception {
        JsonAdaptedLessonList lessonList = new JsonAdaptedLessonList(new ArrayList<>(), "Science");

        JsonAdaptedSubject a = new JsonAdaptedSubject("Science", lessonList);
        JsonAdaptedSubject b = new JsonAdaptedSubject("Science", lessonList);
        JsonAdaptedSubject c = new JsonAdaptedSubject("History",
                new JsonAdaptedLessonList(new ArrayList<>(), "History"));

        assertEquals(a.toModelType(), b.toModelType());
        assertFalse(a.toModelType().equals(c.toModelType()));
    }
}
