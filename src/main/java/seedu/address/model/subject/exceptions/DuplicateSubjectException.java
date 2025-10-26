package seedu.address.model.subject.exceptions;

/**
 * Signals that the operation will result in duplicate Subjects (Subjects are considered
 * duplicates if they have the same
 * name).
 */
public class DuplicateSubjectException extends RuntimeException {
    public DuplicateSubjectException() {
        super("Operation would result in duplicate subjects");
    }
}
