package seedu.address.model.attendance;

/**
 * Represents the possible attendance statuses for a student.
 */
public enum AttendanceStatus {
    PRESENT("PRESENT"),
    ABSENT("ABSENT"),
    LATE("LATE"),
    EXCUSED("EXCUSED");

    private final String displayValue;

    AttendanceStatus(String displayValue) {
        this.displayValue = displayValue;
    }

    @Override
    public String toString() {
        return displayValue;
    }
}
