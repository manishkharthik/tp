package seedu.address.model.lesson;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class LessonListTest {

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
        lessonList.addLesson(new Lesson("Algebra", "Math")); // "Algebra" is the name, "Math" is the subject
        assertTrue(lessonList.containsSubject("Math"));
    }

    @Test
    public void getInternalList_modifyList_throwsUnsupportedOperationException() {
        LessonList lessonList = new LessonList("Math");
        assertThrows(UnsupportedOperationException.class, ()
            -> lessonList.getInternalList().add(new Lesson("Math", "Algebra")));
    }
}
