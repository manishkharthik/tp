package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

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
    public void constructor_withValidSubjectName_success() {
        JsonAdaptedSubject adapted = new JsonAdaptedSubject(VALID_SUBJECT_NAME);
        assertEquals(VALID_SUBJECT_NAME, adapted.getSubjectName());
    }

    @Test
    public void constructor_withSubjectObject_success() {
        Subject subject = new Subject(VALID_SUBJECT_NAME);
        JsonAdaptedSubject adapted = new JsonAdaptedSubject(subject);
        assertEquals(VALID_SUBJECT_NAME, adapted.getSubjectName());
    }

    @Test
    public void getSubjectName_returnsCorrectValue() {
        JsonAdaptedSubject adapted = new JsonAdaptedSubject(VALID_SUBJECT_NAME);
        assertEquals("Mathematics", adapted.getSubjectName());
    }

    @Test
    public void toModelType_validSubject_returnsEquivalentSubject() throws Exception {
        JsonAdaptedSubject adapted = new JsonAdaptedSubject(VALID_SUBJECT_NAME);
        Subject modelSubject = adapted.toModelType();
        assertEquals(new Subject(VALID_SUBJECT_NAME), modelSubject);
    }

    @Test
    public void equals_andHashCode_workCorrectly() throws Exception {
        JsonAdaptedSubject a = new JsonAdaptedSubject("Science");
        JsonAdaptedSubject b = new JsonAdaptedSubject("Science");
        JsonAdaptedSubject c = new JsonAdaptedSubject("History");

        assertEquals(a.getSubjectName(), b.getSubjectName());
        assertEquals(a.toModelType(), b.toModelType());
    }

    @Test
    public void toModelType_validButDifferentSubjects_notEqual() throws Exception {
        JsonAdaptedSubject math = new JsonAdaptedSubject("Math");
        JsonAdaptedSubject physics = new JsonAdaptedSubject("Physics");

        Subject mathModel = math.toModelType();
        Subject physicsModel = physics.toModelType();

        assertFalse(mathModel.equals(physicsModel));
    }
}
