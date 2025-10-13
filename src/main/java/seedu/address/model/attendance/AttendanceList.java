package seedu.address.model.attendance;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import seedu.address.model.person.Name;
import seedu.address.model.student.Student;



/**
 * Represents a list of attendance records tagged to a single student.
 * Each student has his/her own attendance list.
 * Each attendance list is a student’s personal list of all attendance records.
 * 
 * AttendanceList provides methods to mark attendance, retrieve attendance,
 * calculate attendance rate for a student
 * and handles bulk operations for students within a lesson.
 */
public class AttendanceList {

    // Map of student ID to their attendance records
    private final Map<String, List<AttendanceRecord>> studentAttendance;

    /**
     * Creates a new AttendanceList for students of a lesson.
     */
    public AttendanceList() {
        this.studentAttendance = new HashMap<>();
    }

    /**
     * Copies over an existing AttendanceList.
     */
    public AttendanceList(AttendanceList toBeCopied) {
        requireAllNonNull(toBeCopied);
        studentAttendance = new HashMap<>();
        toBeCopied.studentAttendance.forEach((studentId, records) ->
                studentAttendance.put(studentId, new ArrayList<>(records)));
    }

    /**
     * Marks attendance for a student at a specific date and time.
     *
     * @param student  the student whose attendance to mark
     * @param dateTime the date and time of the attendance
     * @param status   the attendance status
     */
    public void markAttendance(Student student, LocalDateTime dateTime, AttendanceStatus status) {
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
     * Calculates the attendance rate for a student.
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

    @Override
    public int hashCode() {
        return Objects.hash(studentAttendance);
    }

    @Override
    public String toString() {
        return "AttendanceList: " + studentAttendance.size() + " students";
    }
}
