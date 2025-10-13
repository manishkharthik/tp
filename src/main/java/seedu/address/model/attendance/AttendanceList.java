package seedu.address.model.attendance;
import seedu.address.model.person.*;

import java.time.LocalDateTime;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Objects;
import java.util.Map;



/**
 * Represents a list of attendances of attendance records.
 * It is tagged to lesson class.
 * Guarantees: details are present ....
 * 
 * Attendance list provides methods to mark & retrieve attendance and handles bulk operations
 */
public class AttendanceList {

    // Map of student ID to their attendance records 
    private final Map<String, List<AttendanceRecord>> studentAttendance;

    /**
     * Creates a new attendanceList for students of a lesson
     */
    public AttendanceList() {
        this.studentAttendance = new HashMap<>();
    }

    /**
     * Copies over an attendance list that already exists
     */
    public AttendanceList(AttendanceList toBeCopied) {
        requireAllNonNull(toBeCopied);
        studentAttendance = new HashMap<>();
        toBeCopied.studentAttendance.forEach((studentId, records) -> 
                                            studentAttendance.put(studentId, new ArrayList<>(records)));
    }

    /**
     * @param student the student whose attendance to mark
     * @param dateTime the date and time of the attendance
     * @param status the attendance status (Optional)
     */
    public void markAttendance(Student student, LocalDateTime dateTime, AttendanceStatus status) {
        requireAllNonNull(student, dateTime, status);
        // TODO: Finish up logic for mark attendance
        int studentId = student.getStudentId();
        Name studentName = student.getName();
        System.out.println("Marked" + studentName +  "attendance");
    }

    /**
     * @param student the student whose attendance record we get
     * @return list of attendance records for student
     */
    public List<AttendanceRecord> getStudentAttendance(Student student) {
        try {
            System.out.println("Returned student attendance!");
            return new ArrayList<>();
        } catch (Exception e) {
            throw new Error(e);
        }
    }

    public double getAttendanceRate(Student student) {
        List<AttendanceRecord> records = getStudentAttendance(student);
        if (records.isEmpty()) {
            return 0.0;
        }
        // converts into stream and filter and count those that are present
        long presentCount = records.stream()
                .filter(r -> r.getStatus() == AttendanceStatus.PRESENT)
                .count();
        return (double) presentCount / records.size();
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two attendance list.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
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
        return "AttendanceList: " + studentAttendance.size() + "students";
    }

}
