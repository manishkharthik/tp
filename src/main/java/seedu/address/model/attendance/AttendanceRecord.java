package seedu.address.model.attendance;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.util.Objects;

import seedu.address.model.lesson.Lesson;


/**
 * Represents the attendance of a student.
 * It is tagged to Student class and attendanceList class.
 * It is made up of a dateTime and a AttendanceStatus.
 *
 * Attendance record provides methods to mark & retrieve attendance and handles bulk operations
 */
public class AttendanceRecord {
    // Private fields
    private final Lesson lesson;
    private AttendanceStatus status;

    /**
     * Constructor
     */
    public AttendanceRecord(Lesson lesson, AttendanceStatus status) {
        requireNonNull(lesson);
        requireNonNull(status);
        this.lesson = lesson;
        this.status = status;
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
    public Lesson getLesson() {
        return lesson;
    }

    /**
     * Returns true if attendance record already exists
     * Uniqueness is determined by lesson only
     * (i.e. same lesson)
     *
     * @param other the other attendance record to compare with
     * @return true if both attendance records have the same lesson
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) return true;
        if (!(other instanceof AttendanceRecord)) return false;
        AttendanceRecord o = (AttendanceRecord) other;
        return lesson.equals(o.lesson);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lesson, status);
    }

    @Override
    public String toString() {
        return "dateTime: " + this.dateTime + "; status: " + this.status;
    }
}
