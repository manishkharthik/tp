package seedu.address.model.attendance;

import java.time.LocalDateTime;
import java.util.Objects;


/**
 * Represents the attendance of a student.
 * It is tagged to student class.
 * Guarantees: details are present ....
 * 
 * Attendance record provides methods to mark & retrieve attendance and handles bulk operations
 */
public class AttendanceRecord {
    //private fields
    private final LocalDateTime dateTime;
    private final AttendanceStatus status;

    /**
     * Constructor
     */
    public AttendanceRecord(AttendanceStatus status, LocalDateTime dateTime) {
        this.status = status;
        this.dateTime = dateTime;
    }

    /**
     * 
     * @return returns current status of this record
     */
    public AttendanceStatus getStatus() {
        return this.status;
    }

    /**
     * 
     * @return returns current date and time of this attendance record
     */
    public LocalDateTime getDateTime() {
        return this.dateTime;
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two attendance records.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AttendanceRecord)) {
            return false;
        }

        AttendanceRecord otherAttendanceRecord = (AttendanceRecord) other;
        return dateTime.equals(otherAttendanceRecord.dateTime) 
            && status.equals(otherAttendanceRecord.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateTime, status);
    }

    @Override
    public String toString() {
        return "dateTime: " + this.dateTime + "; status: " + this.status;
    }
}