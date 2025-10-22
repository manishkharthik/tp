package seedu.address.model.attendance;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import seedu.address.model.lesson.Lesson;

/**
 * Represents a list of attendance records tagged to a single student.
 * Each student has their own AttendanceList containing all attendance records for lessons.
 *
 * AttendanceList provides methods to mark attendance, retrieve records,
 * calculate attendance rate, and handle updates for lessons.
 */
public class AttendanceList {

    // Each student’s attendance records
    private final List<AttendanceRecord> studentAttendance;

    /**
     * Creates a new AttendanceList for a student.
     */
    public AttendanceList() {
        this.studentAttendance = new ArrayList<>();
    }

    /**
     * Marks attendance for the given lesson with the provided status.
     * If a record for the same lesson already exists, updates it.
     * Otherwise, adds a new record.
     *
     * @param lesson the lesson for which attendance is marked
     * @param status the attendance status
     */
    public void markAttendance(Lesson lesson, AttendanceStatus status) {
        requireAllNonNull(lesson, status);

        AttendanceRecord newRecord = new AttendanceRecord(lesson, status);

        // Check if an existing record for the same lesson exists
        for (AttendanceRecord record : studentAttendance) {
            if (record.equals(newRecord)) {
                record.setStatus(status);
                System.out.println("Updated existing attendance record for lesson: " + lesson);
                return;
            }
        }

        // Otherwise, add a new attendance record
        studentAttendance.add(newRecord);
        System.out.println("Added new attendance record for lesson: " + lesson);
    }

    /**
     * Returns the list of attendance records for this student.
     */
    public List<AttendanceRecord> getStudentAttendance() {
        System.out.println("Returned student attendance!");
        return List.copyOf(studentAttendance);
    }

    /**
     * Calculates the attendance rate for the student.
     *
     * @return the fraction of lessons attended (PRESENT / total)
     */
    public double getAttendanceRate() {
        if (studentAttendance.isEmpty()) {
            return 0.0;
        }

        long presentCount = studentAttendance.stream()
                .filter(r -> r.getStatus() == AttendanceStatus.PRESENT)
                .count();

        return (double) presentCount / studentAttendance.size();
    }

    /**
     * Returns true if both attendance lists contain the same data.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AttendanceList)) {
            return false;
        }

        AttendanceList otherList = (AttendanceList) other;
        return studentAttendance.equals(otherList.studentAttendance);
    }

    /**
     * Return hashcode of the attendance list
     */
    @Override
    public int hashCode() {
        return Objects.hash(studentAttendance);
    }

    /**
     * Returns a string representation of the attendance list.
     */
    @Override
    public String toString() {
        return "AttendanceList: " + studentAttendance.size() + " entries";
    }
}
