package seedu.address.model.attendance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.attendance.AttendanceList;

public class AttendanceListTest {

    // Sample attendance records for testing
    private static final LocalDateTime DT1 = LocalDateTime.of(2025, 10, 13, 10, 0);
    private static final LocalDateTime DT2 = LocalDateTime.of(2025, 10, 14, 10, 0);
    private static final LocalDateTime DT3 = LocalDateTime.of(2025, 10, 15, 10, 0);
    private static final LocalDateTime DT4 = LocalDateTime.of(2025, 10, 16, 10, 0);
    private AttendanceList list = new AttendanceList();

    // Test 1: Constructor creates an empty attendance list.
    @Test
    void constructor_createsEmptyAttendanceList() {
        AttendanceList list = new AttendanceList();
        assertNotNull(list);
        assertEquals(0, list.getStudentAttendance().size());
    }

    // Test 2: markAttendance adds a new record when none exists for the dateTime.
    @Test
    void markAttendance_addsNewRecordWhenNoneExists() {
        list.markAttendance(DT1, AttendanceStatus.PRESENT);
        assertEquals(1, list.getStudentAttendance().size());
        assertEquals(AttendanceStatus.PRESENT, list.getStudentAttendance().get(0).getStatus());
        assertEquals(DT1, list.getStudentAttendance().get(0).getDateTime());
    }

    // Test 3: markAttendance updates an existing record when one exists for the dateTime.
    @Test
    void markAttendance_updatesExistingRecordWhenOneExists() {
        list.markAttendance(DT1, AttendanceStatus.PRESENT);
        list.markAttendance(DT1, AttendanceStatus.ABSENT); // Update status
        assertEquals(1, list.getStudentAttendance().size());
        assertEquals(AttendanceStatus.ABSENT, list.getStudentAttendance().get(0).getStatus());
        assertEquals(DT1, list.getStudentAttendance().get(0).getDateTime());
    }

    // Test 4: markAttendance adds multiple records correctly.
    @Test
    void markAttendance_addsMultipleRecordsCorrectly() {
        list.markAttendance(DT1, AttendanceStatus.PRESENT);
        list.markAttendance(DT2, AttendanceStatus.ABSENT);
        assertEquals(2, list.getStudentAttendance().size());
        assertEquals(AttendanceStatus.PRESENT, list.getStudentAttendance().get(0).getStatus());
        assertEquals(DT1, list.getStudentAttendance().get(0).getDateTime());
        assertEquals(AttendanceStatus.ABSENT, list.getStudentAttendance().get(1).getStatus());
        assertEquals(DT2, list.getStudentAttendance().get(1).getDateTime());
    }

    // Test 5: getting the attendance list returns the correct records.
    @Test
    void getStudentAttendance_returnsCorrectRecords() {
        list.markAttendance(DT1, AttendanceStatus.PRESENT);
        list.markAttendance(DT2, AttendanceStatus.ABSENT);
        List<AttendanceRecord> records = list.getStudentAttendance();
        assertEquals(2, records.size());
        assertEquals(AttendanceStatus.PRESENT, records.get(0).getStatus());
        assertEquals(DT1, records.get(0).getDateTime());
        assertEquals(AttendanceStatus.ABSENT, records.get(1).getStatus());
        assertEquals(DT2, records.get(1).getDateTime());
    }

    // Test 6: getting attendance rate calculates correctly.
    @Test
    void getAttendanceRate_calculatesCorrectly() {
        list.markAttendance(DT1, AttendanceStatus.PRESENT);
        list.markAttendance(DT2, AttendanceStatus.ABSENT);
        list.markAttendance(DT3, AttendanceStatus.ABSENT);
        list.markAttendance(DT4, AttendanceStatus.ABSENT);
        double rate = list.getAttendanceRate();
        assertEquals(0.25, rate);
    }

    // Test 7: equals method works correctly.
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
        list1.markAttendance(DT1, AttendanceStatus.PRESENT);
        assertNotEquals(list1, list2); 
    }

    // Test 8: tostring method works correctly.
    @Test
    void toString_worksCorrectly() {
        AttendanceList list1 = new AttendanceList();
        list1.markAttendance(DT1, AttendanceStatus.PRESENT);
        String expected = "AttendanceList: 1 entries";
        assertEquals(expected, list1.toString());
    }
}
