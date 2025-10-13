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

public class AttendanceRecordTest {

    // Sample attendance records for testing.
    private static final LocalDateTime DT1 = LocalDateTime.of(2025, 10, 13, 10, 0);
    private static final LocalDateTime DT2 = LocalDateTime.of(2025, 10, 14, 10, 0);
    private static final LocalDateTime DT3 = LocalDateTime.of(2025, 10, 15, 10, 0);
    private static final LocalDateTime DT4 = LocalDateTime.of(2025, 10, 16, 10, 0);
    private AttendanceRecord record1 = new AttendanceRecord(AttendanceStatus.PRESENT, DT1);
    private AttendanceRecord record2 = new AttendanceRecord(AttendanceStatus.ABSENT, DT2);
    private AttendanceRecord record3 = new AttendanceRecord(AttendanceStatus.EXCUSED, DT3);
    private AttendanceRecord record4 = new AttendanceRecord(AttendanceStatus.LATE, DT4);

    // Test 1: Constructor sets fields correctly.
    @Test
    void constructor_setsFieldsCorrectly() {
        AttendanceRecord test1 = new AttendanceRecord(AttendanceStatus.PRESENT, DT1);
        assertEquals(AttendanceStatus.PRESENT, test1.getStatus());
        assertEquals(DT1, test1.getDateTime());
    }

    // Test 2: getStatus returns correct status.
    @Test
    void getStatus_returnsCorrectStatus() {
        assertEquals(AttendanceStatus.PRESENT, record1.getStatus());
        assertEquals(AttendanceStatus.ABSENT, record2.getStatus());
        assertEquals(AttendanceStatus.EXCUSED, record3.getStatus());
        assertEquals(AttendanceStatus.LATE, record4.getStatus());
    }

    // Test 3: getDateTime returns correct dateTime.
    @Test
    void getDateTime_returnsCorrectDateTime() {
        assertEquals(DT1, record1.getDateTime());
        assertEquals(DT2, record2.getDateTime());
    }

    // Test 4: setStatus updates status correctly.
    @Test
    void setStatus_updatesStatusCorrectly1() {
        AttendanceRecord testRecord = new AttendanceRecord(AttendanceStatus.PRESENT, DT1);
        testRecord.setStatus(AttendanceStatus.ABSENT);
        assertEquals(AttendanceStatus.ABSENT, testRecord.getStatus());
        assertEquals(DT1, testRecord.getDateTime());
    }

    @Test
    void setStatus_updatesStatusCorrectly2() {
        AttendanceRecord testRecord = new AttendanceRecord(AttendanceStatus.ABSENT, DT1);
        testRecord.setStatus(AttendanceStatus.PRESENT);
        assertEquals(AttendanceStatus.PRESENT, testRecord.getStatus());
    }

    // Test 5: equals method works correctly (different dateTime).
    @Test
    void equals_differentDateTime() {
        assertNotEquals(record1, record2);
        assertNotEquals(record1, record3);
        assertNotEquals(record2, record3);
    }

    // Test 6: equals method returns true for different status but same dateTime.
    @Test
    void equals_sameDateTimeDifferentStatus() {
        AttendanceRecord testRecord1 = new AttendanceRecord(AttendanceStatus.ABSENT, DT1);
        AttendanceRecord testRecord2 = new AttendanceRecord(AttendanceStatus.PRESENT, DT2);
        assertTrue(record1.equals(testRecord1));
        assertTrue(record2.equals(testRecord2));
    }

    // Test 8: equals method returns true for same object reference.
    @Test
    void equals_sameObjectReference() {
        assertTrue(record1.equals(record1));
        assertTrue(record2.equals(record2));
        assertTrue(record3.equals(record3));
        assertTrue(record4.equals(record4));
    }

    // Test 9: toString method returns the expected string representation.
    @Test
    void toString_returnsExpectedString() {
        String expected1 = record1.toString();
        String expected2 = record2.toString();
        String expected3 = record3.toString();
        String expected4 = record4.toString();
        assertEquals("dateTime: " + DT1 + "; status: PRESENT", expected1);
        assertEquals("dateTime: " + DT2 + "; status: ABSENT", expected2);
        assertEquals("dateTime: " + DT3 + "; status: EXCUSED", expected3);
        assertEquals("dateTime: " + DT4 + "; status: LATE", expected4);
    }
}
