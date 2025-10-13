package seedu.address.model.attendance;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import seedu.address.model.person.Name;
import seedu.address.model.student.Student;



/**
 * Represents a list of attendance records tagged to a single student.
 * Each student has his/her own attendance list.
 * Each attendance list contains all of a student’s attendance records.
 * 
 * AttendanceList provides methods to mark attendance, retrieve attendance,
 * calculate attendance rate for a student
 * and handles bulk operations for students within a lesson.
 */
public class AttendanceList {

    // A list of a student's attendance records
    private final List<AttendanceRecord> studentAttendance;

    /**
     * Creates a new AttendanceList for a student
     */
    public AttendanceList() {
        this.studentAttendance = new ArrayList<>();
    }

    /**
     * Marks attendance for the student at a specific date and time.
     *
     * @param dateTime the date and time of the attendance
     * @param status   the attendance status
     */
    public void markAttendance(LocalDateTime dateTime, AttendanceStatus status) {
        requireAllNonNull(student, dateTime, status);
        // TODO: Complete logic for marking attendance
        int studentId = student.getStudentId();
        Name studentName = student.getName();
        System.out.println("Marked " + studentName + " attendance");
    }

    /**
     * Returns the list of attendance records for a given student.
     *
     * @param student the student whose attendance record is requested
     * @return a list of AttendanceRecord objects
     */
    public List<AttendanceRecord> getStudentAttendance(Student student) {
        try {
            System.out.println("Returned student attendance!");
            return new ArrayList<>();
        } catch (Exception e) {
            throw new Error(e);
        }
    }

    /**
     * Calculates the attendance rate for the student.
     *
     * @param student the student whose attendance rate is calculated
     * @return a double representing the fraction of attended sessions
     */
    public double getAttendanceRate(Student student) {
        List<AttendanceRecord> records = getStudentAttendance(student);
        if (records.isEmpty()) {
            return 0.0;
        }

        long presentCount = records.stream()
                .filter(r -> r.getStatus() == AttendanceStatus.PRESENT)
                .count();

        return (double) presentCount / records.size();
    }

    /**
     * Returns true if both attendance lists have the same data.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AttendanceList)) {
            return false;
        }

        AttendanceList otherAttendanceList = (AttendanceList) other;
        return studentAttendance.equals(otherAttendanceList.studentAttendance);
    }

    /**
     * Return hashcode of the attendance list
     */
    @Override
    public int hashCode() {
        return Objects.hash(studentAttendance);
    }

    /**
     * Return a string representation of the attendance list (number of entries)
     */
    @Override
    public String toString() {
        return "AttendanceList: " + studentAttendance.size() + " entries";
    }
}
