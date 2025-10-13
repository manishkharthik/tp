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

public class attendanceRecordTest {

    // Sample attendance records for testing
    private static final LocalDateTime DT1 = LocalDateTime.of(2025, 10, 13, 10, 0);
    private static final LocalDateTime DT2 = LocalDateTime.of(2025, 10, 14, 10, 0);
    private AttendanceRecord record1 = new AttendanceRecord(AttendanceStatus.PRESENT, DT1);
    private AttendanceRecord record2 = new AttendanceRecord(AttendanceStatus.ABSENT, DT2);
    private AttendanceRecord record3 = new AttendanceRecord(AttendanceStatus.EXCUSED, DT1);
    private AttendanceRecord record4 = new AttendanceRecord(AttendanceStatus.LATE, DT1);

    // Test 1: Constructor sets fields correctly
    @Test
    void constructor_setsFieldsCorrectly() {
        AttendanceRecord test1 = new AttendanceRecord(AttendanceStatus.PRESENT, DT1);
        assertEquals(AttendanceStatus.PRESENT, test1.getStatus());
        assertEquals(DT1, test1.getDateTime());
    }
    
    // Test 2: getStatus returns correct status


    // Test 3: getDateTime returns correct dateTime


    // Test 4: setStatus updates status correctly


    // Test 5: equals method works correctly (different dateTime)


    // Test 6: equals method returns true for different status but same dateTime
    

    // Test 7: toString method returns expected string representation


}
