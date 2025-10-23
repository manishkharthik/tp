package seedu.address.model.lesson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LessonListTest {

    private LessonList mathLessonList;
    private LessonList scienceLessonList;
    private Lesson lesson1;
    private Lesson lesson2;

    @BeforeEach
    public void setUp() {
        lesson1 = new Lesson("Lesson 1", "Math");
        lesson2 = new Lesson("Lesson 2", "Math");

        mathLessonList = new LessonList("Math");
        scienceLessonList = new LessonList("Science");

        mathLessonList.addLesson(lesson1);
        mathLessonList.addLesson(lesson2);
    }

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new LessonList(null));
    }

    @Test
    public void constructor_emptyString_createsEmptyList() {
        LessonList lessonList = new LessonList("");
        assertTrue(lessonList.getInternalList().isEmpty());
    }

    @Test
    public void addLesson_nullLesson_throwsNullPointerException() {
        LessonList lessonList = new LessonList("Math");
        assertThrows(NullPointerException.class, () -> lessonList.addLesson(null));
    }

    @Test
    public void addLesson_validLesson_success() {
        LessonList lessonList = new LessonList("Math");
        Lesson lesson = new Lesson("Math", "Algebra");
        lessonList.addLesson(lesson);
        assertTrue(lessonList.contains(lesson));
    }

    @Test
    public void contains_nullLesson_throwsNullPointerException() {
        LessonList lessonList = new LessonList("Math");
        assertThrows(NullPointerException.class, () -> lessonList.contains(null));
    }

    @Test
    public void contains_lessonNotInList_returnsFalse() {
        LessonList lessonList = new LessonList("Math");
        Lesson lesson = new Lesson("Math", "Algebra");
        assertFalse(lessonList.contains(lesson));
    }

    @Test
    public void contains_lessonInList_returnsTrue() {
        LessonList lessonList = new LessonList("Math");
        Lesson lesson = new Lesson("Math", "Algebra");
        lessonList.addLesson(lesson);
        assertTrue(lessonList.contains(lesson));
    }

    @Test
    public void containsSubject_nullString_throwsNullPointerException() {
        LessonList lessonList = new LessonList("Math");
        assertThrows(NullPointerException.class, () -> lessonList.containsSubject(null));
    }

    @Test
    public void containsSubject_subjectNotInList_returnsFalse() {
        LessonList lessonList = new LessonList("Math");
        assertFalse(lessonList.containsSubject("Science"));
    }

    @Test
    public void containsSubject_subjectInList_returnsTrue() {
        LessonList lessonList = new LessonList("Math");
        lessonList.addLesson(new Lesson("Algebra", "Math"));
        assertTrue(lessonList.containsSubject("Math"));
    }

    @Test
    public void getInternalList_modifyList_throwsUnsupportedOperationException() {
        LessonList lessonList = new LessonList("Math");
        assertThrows(UnsupportedOperationException.class, ()
            -> lessonList.getInternalList().add(new Lesson("Math", "Algebra")));
    }

    @Test
    public void getLessons_returnsNewList() {
        List<Lesson> lessons1 = mathLessonList.getLessons();
        List<Lesson> lessons2 = mathLessonList.getLessons();

        assertNotSame(lessons1, lessons2);
        assertEquals(lessons1, lessons2);
    }

    @Test
    public void getLessons_modifyReturnedList_doesNotAffectOriginal() {
        List<Lesson> lessons = mathLessonList.getLessons();
        lessons.clear();

        // Original should still have 2 lessons
        assertEquals(2, mathLessonList.getLessons().size());
    }

    @Test
    public void getLessons_returnsCorrectLessons() {
        List<Lesson> lessons = mathLessonList.getLessons();

        assertEquals(2, lessons.size());
        assertTrue(lessons.contains(lesson1));
        assertTrue(lessons.contains(lesson2));
    }

    @Test
    public void getSubject_returnsCorrectSubject() {
        assertEquals("Math", mathLessonList.getSubject());
        assertEquals("Science", scienceLessonList.getSubject());
    }

    @Test
    public void toString_withLessons_returnsCorrectFormat() {
        String result = mathLessonList.toString();

        assertTrue(result.contains("Subject: Math"));
        assertTrue(result.contains("Lessons:"));
        assertTrue(result.contains("Lesson 1"));
        assertTrue(result.contains("Lesson 2"));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        assertTrue(mathLessonList.equals(mathLessonList));
    }

    @Test
    public void equals_null_returnsFalse() {
        assertFalse(mathLessonList.equals(null));
    }

    @Test
    public void equals_differentType_returnsFalse() {
        assertFalse(mathLessonList.equals("Math"));
    }

    @Test
    public void equals_sameSubjectAndLessons_returnsTrue() {
        LessonList anotherMathList = new LessonList("Math");
        anotherMathList.addLesson(lesson1);
        anotherMathList.addLesson(lesson2);

        assertTrue(mathLessonList.equals(anotherMathList));
    }

    @Test
    public void equals_differentSubject_returnsFalse() {
        assertFalse(mathLessonList.equals(scienceLessonList));
    }

    @Test
    public void equals_sameSubjectDifferentLessons_returnsFalse() {
        LessonList anotherMathList = new LessonList("Math");
        anotherMathList.addLesson(lesson1);

        assertFalse(mathLessonList.equals(anotherMathList));
    }

    @Test
    public void equals_differentSubjectSameLessons_returnsFalse() {
        LessonList anotherList = new LessonList("Science");
        anotherList.addLesson(lesson1);
        anotherList.addLesson(lesson2);

        assertFalse(mathLessonList.equals(anotherList));
    }

    @Test
    public void hashCode_consistentAcrossMultipleCalls() {
        int hash1 = mathLessonList.hashCode();
        int hash2 = mathLessonList.hashCode();

        assertEquals(hash1, hash2);
    }

    @Test
    public void hashCode_sameSubjectAndLessons_sameHashCode() {
        LessonList anotherMathList = new LessonList("Math");
        anotherMathList.addLesson(lesson1);
        anotherMathList.addLesson(lesson2);

        assertEquals(mathLessonList.hashCode(), anotherMathList.hashCode());
    }
}
