package seedu.address.model.subject;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.exceptions.LessonNotFoundException;

/**
 * Unit tests for {@link Subject}.
 */
public class SubjectTest {

    private Subject math;
    private Subject science;
    private Lesson lesson1;
    private Lesson lesson2;

    @BeforeEach
    public void setUp() {
        math = new Subject("Math");
        lesson1 = new Lesson("L1", "Math");
        lesson2 = new Lesson("L2", "Math");
    }

    @Test
    public void equals_differentContent_returnsFalse() {
        String invalidSubject = "I am invalid";
        assertFalse(math.equals(invalidSubject));
    }

    @Test
    public void removeLesson_existingLesson_successfullyRemoves() {
        math.addLesson(lesson1);
        assertTrue(math.containsLesson(lesson1));

        math.removeLesson(lesson1);
        assertFalse(math.containsLesson(lesson1));
    }

    @Test
    public void removeLesson_nonExistentLesson_throwsLessonNotFoundException() {
        math.addLesson(lesson1);
        assertThrows(LessonNotFoundException.class, () -> math.removeLesson(lesson2));
    }

    @Test
    public void removeLesson_nullLesson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> math.removeLesson(null));
    }

    @Test
    public void isSameSubject_sameObject_returnsTrue() {
        assertTrue(math.isSameSubject(math));
    }

    @Test
    public void isSameSubject_nullObject_returnsFalse() {
        assertFalse(math.isSameSubject(null));
    }

    @Test
    public void isSameSubject_sameNameDifferentCase_returnsFalse() {
        Subject mathLowercase = new Subject("math");
        assertFalse(math.isSameSubject(mathLowercase));
    }

    @Test
    public void isSameSubject_differentName_returnsFalse() {
        assertFalse(math.isSameSubject(science));
    }

    @Test
    public void isSameSubject_sameName_returnsTrue() {
        Subject copyMath = new Subject("Math");
        assertTrue(math.isSameSubject(copyMath));
    }

    @Test
    public void addThenRemove_multipleLessons_allRemovedSuccessfully() {
        math.addLesson(lesson1);
        math.addLesson(lesson2);
        assertTrue(math.containsLesson(lesson1));
        assertTrue(math.containsLesson(lesson2));

        math.removeLesson(lesson1);
        math.removeLesson(lesson2);
        assertFalse(math.containsLesson(lesson1));
        assertFalse(math.containsLesson(lesson2));
    }
}
