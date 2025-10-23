package seedu.address.model.lesson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link Lesson}.
 */
public class LessonTest {

    // ==================== Constructor Tests ====================

    @Test
    public void constructor_validNameAndSubject_success() {
        Lesson lesson = new Lesson("Algebra", "Math");
        assertEquals("Algebra", lesson.getName());
        assertEquals("Math", lesson.getSubject());
    }

    @Test
    public void constructor_nullName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Lesson(null, "Math"));
    }

    @Test
    public void constructor_nullSubject_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Lesson("Algebra", null));
    }

    @Test
    public void constructor_bothNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Lesson(null, null));
    }

    @Test
    public void constructor_blankName_throwsAssertionError() {
        assertThrows(AssertionError.class, () -> new Lesson("   ", "Math"));
    }

    @Test
    public void constructor_blankSubject_throwsAssertionError() {
        assertThrows(AssertionError.class, () -> new Lesson("Algebra", "   "));
    }

    @Test
    public void constructor_emptyName_throwsAssertionError() {
        assertThrows(AssertionError.class, () -> new Lesson("", "Math"));
    }

    @Test
    public void constructor_emptySubject_throwsAssertionError() {
        assertThrows(AssertionError.class, () -> new Lesson("Algebra", ""));
    }

    @Test
    public void constructor_nameWithWhitespace_trimsCorrectly() {
        Lesson lesson = new Lesson("  Algebra  ", "Math");
        assertEquals("Algebra", lesson.getName());
    }

    @Test
    public void constructor_subjectWithWhitespace_trimsCorrectly() {
        Lesson lesson = new Lesson("Algebra", "  Math  ");
        assertEquals("Math", lesson.getSubject());
    }

    @Test
    public void constructor_bothWithWhitespace_trimsBoth() {
        Lesson lesson = new Lesson("  Algebra  ", "  Math  ");
        assertEquals("Algebra", lesson.getName());
        assertEquals("Math", lesson.getSubject());
    }

    // ==================== getName() Tests ====================

    @Test
    public void getName_validLesson_returnsCorrectName() {
        Lesson lesson = new Lesson("Calculus", "Math");
        assertEquals("Calculus", lesson.getName());
    }

    @Test
    public void getName_calledMultipleTimes_returnsConsistentValue() {
        Lesson lesson = new Lesson("Physics", "Science");
        assertEquals(lesson.getName(), lesson.getName());
    }

    // ==================== getSubject() Tests ====================

    @Test
    public void getSubject_validLesson_returnsCorrectSubject() {
        Lesson lesson = new Lesson("Algebra", "Mathematics");
        assertEquals("Mathematics", lesson.getSubject());
    }

    @Test
    public void getSubject_calledMultipleTimes_returnsConsistentValue() {
        Lesson lesson = new Lesson("Biology", "Science");
        assertEquals(lesson.getSubject(), lesson.getSubject());
    }

    // ==================== isSameLesson() Tests ====================

    @Test
    public void isSameLesson_sameObject_returnsTrue() {
        Lesson lesson = new Lesson("Algebra", "Math");
        assertTrue(lesson.isSameLesson(lesson));
    }

    @Test
    public void isSameLesson_null_returnsFalse() {
        Lesson lesson = new Lesson("Algebra", "Math");
        assertFalse(lesson.isSameLesson(null));
    }

    @Test
    public void isSameLesson_sameNameAndSubject_returnsTrue() {
        Lesson lesson1 = new Lesson("Algebra", "Math");
        Lesson lesson2 = new Lesson("Algebra", "Math");
        assertTrue(lesson1.isSameLesson(lesson2));
    }

    @Test
    public void isSameLesson_differentName_returnsFalse() {
        Lesson lesson1 = new Lesson("Algebra", "Math");
        Lesson lesson2 = new Lesson("Calculus", "Math");
        assertFalse(lesson1.isSameLesson(lesson2));
    }

    @Test
    public void isSameLesson_differentSubject_returnsFalse() {
        Lesson lesson1 = new Lesson("Algebra", "Math");
        Lesson lesson2 = new Lesson("Algebra", "Science");
        assertFalse(lesson1.isSameLesson(lesson2));
    }

    @Test
    public void isSameLesson_differentNameAndSubject_returnsFalse() {
        Lesson lesson1 = new Lesson("Algebra", "Math");
        Lesson lesson2 = new Lesson("Biology", "Science");
        assertFalse(lesson1.isSameLesson(lesson2));
    }

    @Test
    public void isSameLesson_caseInsensitive_returnsFalse() {
        // Assuming case-sensitive comparison
        Lesson lesson1 = new Lesson("Algebra", "Math");
        Lesson lesson2 = new Lesson("algebra", "math");
        assertFalse(lesson1.isSameLesson(lesson2));
    }

    // ==================== equals() Tests ====================

    @Test
    public void equals_sameObject_returnsTrue() {
        Lesson lesson = new Lesson("Algebra", "Math");
        assertTrue(lesson.equals(lesson));
    }

    @Test
    public void equals_null_returnsFalse() {
        Lesson lesson = new Lesson("Algebra", "Math");
        assertFalse(lesson.equals(null));
    }

    @Test
    public void equals_differentType_returnsFalse() {
        Lesson lesson = new Lesson("Algebra", "Math");
        assertFalse(lesson.equals("Algebra"));
    }

    @Test
    public void equals_sameNameAndSubject_returnsTrue() {
        Lesson lesson1 = new Lesson("Algebra", "Math");
        Lesson lesson2 = new Lesson("Algebra", "Math");
        assertTrue(lesson1.equals(lesson2));
    }

    @Test
    public void equals_differentName_returnsFalse() {
        Lesson lesson1 = new Lesson("Algebra", "Math");
        Lesson lesson2 = new Lesson("Calculus", "Math");
        assertFalse(lesson1.equals(lesson2));
    }

    @Test
    public void equals_differentSubject_returnsFalse() {
        Lesson lesson1 = new Lesson("Algebra", "Math");
        Lesson lesson2 = new Lesson("Algebra", "Science");
        assertFalse(lesson1.equals(lesson2));
    }

    @Test
    public void equals_differentNameAndSubject_returnsFalse() {
        Lesson lesson1 = new Lesson("Algebra", "Math");
        Lesson lesson2 = new Lesson("Biology", "Science");
        assertFalse(lesson1.equals(lesson2));
    }

    // ==================== hashCode() Tests ====================

    @Test
    public void hashCode_sameNameAndSubject_sameHashCode() {
        Lesson lesson1 = new Lesson("Algebra", "Math");
        Lesson lesson2 = new Lesson("Algebra", "Math");
        assertEquals(lesson1.hashCode(), lesson2.hashCode());
    }

    @Test
    public void hashCode_differentLesson_differentHashCode() {
        Lesson lesson1 = new Lesson("Algebra", "Math");
        Lesson lesson2 = new Lesson("Biology", "Science");
        assertNotEquals(lesson1.hashCode(), lesson2.hashCode());
    }

    @Test
    public void hashCode_consistentAcrossMultipleCalls() {
        Lesson lesson = new Lesson("Algebra", "Math");
        int hash1 = lesson.hashCode();
        int hash2 = lesson.hashCode();
        assertEquals(hash1, hash2);
    }

    @Test
    public void hashCode_equalsContract_maintainsContract() {
        // If two objects are equal, they must have the same hash code
        Lesson lesson1 = new Lesson("Algebra", "Math");
        Lesson lesson2 = new Lesson("Algebra", "Math");

        if (lesson1.equals(lesson2)) {
            assertEquals(lesson1.hashCode(), lesson2.hashCode());
        }
    }

    // ==================== toString() Tests ====================

    @Test
    public void toString_validLesson_containsNameAndSubject() {
        Lesson lesson = new Lesson("Algebra", "Math");
        String result = lesson.toString();

        assertTrue(result.contains("Algebra"));
        assertTrue(result.contains("Math"));
    }

    @Test
    public void toString_validLesson_containsFieldLabels() {
        Lesson lesson = new Lesson("Algebra", "Math");
        String result = lesson.toString();

        assertTrue(result.contains("name"));
        assertTrue(result.contains("subject"));
    }

    @Test
    public void toString_consistentAcrossMultipleCalls() {
        Lesson lesson = new Lesson("Algebra", "Math");
        String result1 = lesson.toString();
        String result2 = lesson.toString();

        assertEquals(result1, result2);
    }

    @Test
    public void toString_differentLessons_differentStrings() {
        Lesson lesson1 = new Lesson("Algebra", "Math");
        Lesson lesson2 = new Lesson("Biology", "Science");

        assertNotEquals(lesson1.toString(), lesson2.toString());
    }

    // ==================== Immutability Tests ====================

    @Test
    public void lesson_isImmutable() {
        Lesson lesson = new Lesson("Algebra", "Math");
        String originalName = lesson.getName();
        String originalSubject = lesson.getSubject();

        // Call getters multiple times
        lesson.getName();
        lesson.getSubject();

        // Values should remain unchanged
        assertEquals(originalName, lesson.getName());
        assertEquals(originalSubject, lesson.getSubject());
    }

    // ==================== Edge Case Tests ====================

    @Test
    public void constructor_specialCharactersInName_success() {
        Lesson lesson = new Lesson("Algebra-101", "Math");
        assertEquals("Algebra-101", lesson.getName());
    }

    @Test
    public void constructor_numbersInName_success() {
        Lesson lesson = new Lesson("Math 101", "Mathematics");
        assertEquals("Math 101", lesson.getName());
    }

    @Test
    public void constructor_longName_success() {
        String longName = "Introduction to Advanced Calculus and Differential Equations";
        Lesson lesson = new Lesson(longName, "Math");
        assertEquals(longName, lesson.getName());
    }

    @Test
    public void constructor_singleCharacterName_success() {
        Lesson lesson = new Lesson("A", "Math");
        assertEquals("A", lesson.getName());
    }

    @Test
    public void constructor_singleCharacterSubject_success() {
        Lesson lesson = new Lesson("Algebra", "M");
        assertEquals("M", lesson.getSubject());
    }
}
