package test.java.seedu.address.model.attendance;

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

    @BeforeEach
    void setUp() {
        list = new AttendanceList();
    }

    // Test 1: Constructor creates an empty attendance list.

    // Test 2: markAttendance adds a new record when none exists for the dateTime.

    // Test 3: markAttendance updates an existing record when one exists for the dateTime.

    // Test 4: markAttendance adds multiple records correctly.

    // Test 5: getting the attendance list returns the correct records.

    // Test 6: getting attendance rate calculates correctly.

    // Test 7: equals method works correctly.

    // Test 8: tostring method works correctly.
    
}
