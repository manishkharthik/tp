package seedu.address.model.attendance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.lesson.Lesson;

public class AttendanceListTest {

    private final AttendanceList list = new AttendanceList();

    // Helpers
    private static final Lesson L1_MATH = new Lesson("L1", "Math");
    private static final Lesson L1_MATH_DUP = new Lesson("L1", "Math"); // same identity as L1_MATH
    private static final Lesson L2_MATH = new Lesson("L2", "Math");
    private static final Lesson QUIZ_SCI = new Lesson("Quiz", "Science");

    // Test 1: Constructor creates an empty attendance list.
    @Test
    void constructor_createsEmptyAttendanceList() {
        AttendanceList list = new AttendanceList();
        assertNotNull(list);
        assertEquals(0, list.getStudentAttendance().size());
    }

    // Test 2: markAttendance adds a new record when none exists for the lesson.
    @Test
    void markAttendance_addsNewRecordWhenNoneExists() {
        list.markAttendance(L1_MATH, AttendanceStatus.PRESENT);
        assertEquals(1, list.getStudentAttendance().size());
        assertEquals(AttendanceStatus.PRESENT, list.getStudentAttendance().get(0).getStatus());
        assertEquals(L1_MATH, list.getStudentAttendance().get(0).getLesson());
    }

    // Test 3: markAttendance updates an existing record when one exists for the same lesson.
    @Test
    void markAttendance_updatesExistingRecordWhenOneExists() {
        list.markAttendance(L1_MATH, AttendanceStatus.PRESENT);
        list.markAttendance(L1_MATH_DUP, AttendanceStatus.ABSENT);
        assertEquals(1, list.getStudentAttendance().size());
        assertEquals(AttendanceStatus.ABSENT, list.getStudentAttendance().get(0).getStatus());
        assertEquals(L1_MATH, list.getStudentAttendance().get(0).getLesson());
    }

    // Test 4: markAttendance adds multiple records correctly for different lessons.
    @Test
    void markAttendance_addsMultipleRecordsCorrectly() {
        list.markAttendance(L1_MATH, AttendanceStatus.PRESENT);
        list.markAttendance(L2_MATH, AttendanceStatus.ABSENT);
        assertEquals(2, list.getStudentAttendance().size());
        assertEquals(AttendanceStatus.PRESENT, list.getStudentAttendance().get(0).getStatus());
        assertEquals(L1_MATH, list.getStudentAttendance().get(0).getLesson());
        assertEquals(AttendanceStatus.ABSENT, list.getStudentAttendance().get(1).getStatus());
        assertEquals(L2_MATH, list.getStudentAttendance().get(1).getLesson());
    }

    // Test 5: getting the attendance list returns the correct records (and is defensive).
    @Test
    void getAttendanceList_returnsCorrectRecords_andIsDefensive() {
        list.markAttendance(L1_MATH, AttendanceStatus.PRESENT);
        list.markAttendance(QUIZ_SCI, AttendanceStatus.LATE);

        List<AttendanceRecord> records = list.getStudentAttendance();
        assertEquals(2, records.size());
        assertEquals(L1_MATH, records.get(0).getLesson());
        assertEquals(AttendanceStatus.PRESENT, records.get(0).getStatus());
        assertEquals(QUIZ_SCI, records.get(1).getLesson());
        assertEquals(AttendanceStatus.LATE, records.get(1).getStatus());

        // defensive copy: returned list should be unmodifiable (if your impl returns unmodifiableList)
        assertThrows(UnsupportedOperationException.class, () ->
                records.add(new AttendanceRecord(new Lesson("X", "Y"), AttendanceStatus.PRESENT)));
    }

    // Test 6: equals method works correctly.
    @Test
    void equals_worksCorrectly1() {
        AttendanceList list1 = new AttendanceList();
        AttendanceList list2 = new AttendanceList();
        assertEquals(list1, list2);
    }

    @Test
    void equals_worksCorrectly2() {
        AttendanceList list1 = new AttendanceList();
        AttendanceList list2 = new AttendanceList();
        list1.markAttendance(L1_MATH, AttendanceStatus.PRESENT);
        assertNotEquals(list1, list2);
    }

    // Test 7: toString method works correctly.
    @Test
    void toString_worksCorrectly() {
        AttendanceList list1 = new AttendanceList();
        list1.markAttendance(L1_MATH, AttendanceStatus.PRESENT);
        String expected = "AttendanceList: 1 entries";
        assertEquals(expected, list1.toString());
    }
}
