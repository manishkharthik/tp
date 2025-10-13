package seedu.address.model.attendance;

import java.time.LocalDateTime;
import java.util.Objects;


/**
 * Represents the attendance of a student.
 * It is tagged to Student class and attendanceList class.
 * It is made up of a dateTime and a AttendanceStatus.
 *
 * Attendance record provides methods to mark & retrieve attendance and handles bulk operations
 */
public class AttendanceRecord {
    // Private fields
    private final LocalDateTime dateTime;
    private AttendanceStatus status;

    /**
     * Constructor
     */
    public AttendanceRecord(AttendanceStatus status, LocalDateTime dateTime) {
        this.status = status;
        this.dateTime = dateTime;
    }

    /**
     * @return returns current status of this record
     */
    public AttendanceStatus getStatus() {
        return this.status;
    }

    /**
     * Sets the status of this attendance record
     */
    public void setStatus(AttendanceStatus status) {
        this.status = status;
    }

    /**
     * @return returns current date and time of this attendance record
     */
    public LocalDateTime getDateTime() {
        return this.dateTime;
    }

    /**
     * Returns true if attendance record already exists
     * Uniqueness is determined by dateTime only
     * (i.e. same dateTime)
     *
     * @param other the other attendance record to compare with
     * @return true if both attendance records have the same dateTime
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // Instanceof handles nulls
        if (!(other instanceof AttendanceRecord)) {
            return false;
        }

        AttendanceRecord otherAttendanceRecord = (AttendanceRecord) other;
        return dateTime.equals(otherAttendanceRecord.dateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateTime);
    }

    @Override
    public String toString() {
        return "dateTime: " + this.dateTime + "; status: " + this.status;
    }
}
