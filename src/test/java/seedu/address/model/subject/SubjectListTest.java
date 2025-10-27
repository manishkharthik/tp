package seedu.address.model.subject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.lesson.Lesson;
import seedu.address.model.subject.exceptions.DuplicateSubjectException;
import seedu.address.model.subject.exceptions.SubjectNotFoundException;

public class SubjectListTest {

    private SubjectList subjectList;
    private Subject math;
    private Subject science;
    private Lesson lesson1;
    private Lesson lesson2;

    @BeforeEach
    public void setUp() {
        subjectList = new SubjectList();
        math = new Subject("Math");
        science = new Subject("Science");
        lesson1 = new Lesson("Math", "Algebra");
        lesson2 = new Lesson("Science", "Physics");
    }

    @Test
    public void addSubject_newSubject_success() throws DuplicateSubjectException {
        subjectList.addSubject(math);
        assertTrue(subjectList.contains(math));
    }

    @Test
    public void addSubject_duplicateSubject_throwsDuplicateSubjectException() throws DuplicateSubjectException {
        subjectList.addSubject(math);
        assertThrows(DuplicateSubjectException.class, () -> subjectList.addSubject(math));
    }

    @Test
    public void addSubject_nullSubject_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> subjectList.addSubject(null));
    }

    @Test
    public void deleteSubject_existingSubject_success() throws DuplicateSubjectException, SubjectNotFoundException {
        subjectList.addSubject(math);
        subjectList.deleteSubject(math);
        assertFalse(subjectList.contains(math));
        assertThrows(SubjectNotFoundException.class, () -> subjectList.deleteSubject(math));
    }

    @Test
    public void deleteSubject_notFound_throwsSubjectNotFoundException() {
        assertThrows(SubjectNotFoundException.class, () -> subjectList.deleteSubject(math));
    }

    @Test
    public void containsLesson_lessonInSubject_returnsTrue() throws DuplicateSubjectException {
        math.addLesson(lesson1);
        subjectList.addSubject(math);
        assertTrue(subjectList.containsLesson(lesson1));
    }

    @Test
    public void containsLesson_lessonNotInAnySubject_returnsFalse() throws DuplicateSubjectException {
        subjectList.addSubject(math);
        assertFalse(subjectList.containsLesson(lesson2));
    }

    @Test
    public void getSubjects_returnsDefensiveCopy() throws DuplicateSubjectException {
        subjectList.addSubject(math);
        List<Subject> subjects = subjectList.getSubjects();
        subjects.clear();
        assertEquals(1, subjectList.getSubjects().size());
    }

    @Test
    public void equals_sameContent_returnsTrue() throws DuplicateSubjectException {
        subjectList.addSubject(math);
        SubjectList copy = new SubjectList();
        copy.addSubject(math);
        assertEquals(subjectList, copy);
        assertEquals(subjectList.hashCode(), copy.hashCode());
    }

    @Test
    public void equals_differentContent_returnsFalse() throws DuplicateSubjectException {
        subjectList.addSubject(math);
        SubjectList diff = new SubjectList();
        diff.addSubject(science);
        assertNotEquals(subjectList, diff);
    }

    @Test
    public void equals_nullOrDifferentType_returnsFalse() {
        assertNotEquals(subjectList, null);
        assertNotEquals(subjectList, "some string");
    }

    @Test
    public void toString_containsSubjectNames() throws DuplicateSubjectException {
        subjectList.addSubject(math);
        String output = subjectList.toString();
        assertTrue(output.contains("Math"));
    }
}
