package seedu.address.model.attendance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.lesson.Lesson;

public class AttendanceRecordTest {

    // Lessons for identity checks
    private static final Lesson L1_MATH = new Lesson("L1", "Math");
    private static final Lesson L1_MATH_DUP = new Lesson("L1", "Math"); // same identity as L1_MATH
    private static final Lesson L2_MATH = new Lesson("L2", "Math");
    private static final Lesson QUIZ_SCI = new Lesson("Quiz", "Science");

    // Sample records
    private final AttendanceRecord r1 = new AttendanceRecord(L1_MATH, AttendanceStatus.PRESENT);
    private final AttendanceRecord r2 = new AttendanceRecord(L2_MATH, AttendanceStatus.ABSENT);
    private final AttendanceRecord r3 = new AttendanceRecord(QUIZ_SCI, AttendanceStatus.EXCUSED);
    private final AttendanceRecord r4 = new AttendanceRecord(L1_MATH, AttendanceStatus.LATE); // same lesson as r1

    // Test 1: constructor sets fields correctly
    @Test
    void constructor_setsFieldsCorrectly() {
        AttendanceRecord rec = new AttendanceRecord(L1_MATH, AttendanceStatus.PRESENT);
        assertEquals(L1_MATH, rec.getLesson());
        assertEquals(AttendanceStatus.PRESENT, rec.getStatus());
    }

    // Test 2: getters return correct values
    @Test
    void getters_returnCorrectValues() {
        assertEquals(L1_MATH, r1.getLesson());
        assertEquals(AttendanceStatus.PRESENT, r1.getStatus());

        assertEquals(L2_MATH, r2.getLesson());
        assertEquals(AttendanceStatus.ABSENT, r2.getStatus());
    }

    // Test 3: setStatus updates status but not lesson
    @Test
    void setStatus_updatesOnlyStatus() {
        AttendanceRecord rec = new AttendanceRecord(QUIZ_SCI, AttendanceStatus.ABSENT);
        rec.setStatus(AttendanceStatus.PRESENT);
        assertEquals(QUIZ_SCI, rec.getLesson());
        assertEquals(AttendanceStatus.PRESENT, rec.getStatus());
    }

    // Test 4: equals — different lessons => not equal
    @Test
    void equals_differentLessons_notEqual() {
        assertNotEquals(r1, r2);
        assertNotEquals(r1, r3);
        assertNotEquals(r2, r3);
    }

    // Test 5: equals — same lesson, different status => equal
    @Test
    void equals_sameLessonDifferentStatus_equal() {
        // r1 (L1_MATH, PRESENT) vs r4 (L1_MATH, LATE)
        assertTrue(r1.equals(r4));

        // also via a new instance using a duplicate Lesson object with same identity
        AttendanceRecord rec = new AttendanceRecord(L1_MATH_DUP, AttendanceStatus.EXCUSED);
        assertTrue(r1.equals(rec));
    }

    // Test 6: equals — same object reference
    @Test
    void equals_sameReference_true() {
        assertTrue(r1.equals(r1));
        assertTrue(r2.equals(r2));
        assertTrue(r3.equals(r3));
    }

    // Test 7: toString contains lesson info and status (don’t overfit exact formatting)
    @Test
    void toString_containsLessonAndStatus() {
        String s1 = r1.toString();
        // Should at least include lesson name/subject and status
        assertTrue(s1.contains("L1"));
        assertTrue(s1.contains("Math"));
        assertTrue(s1.contains("PRESENT"));
    }
}
