package seedu.address.model.lesson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import seedu.address.model.attendance.AttendanceStatus;
import seedu.address.model.lesson.exceptions.DuplicateLessonException;
import seedu.address.model.lesson.exceptions.LessonNotFoundException;

/**
 * Represents a list of lessons for a specific subject.
 * Each LessonList instance is tied to a single subject.
 */
public class LessonList {
    private static final String EMPTY_SUBJECT = "";

    /**
     * Creates an empty LessonList.
     */
    private final List<Lesson> lessons;
    private final String subject;

    /**
     * Creates an empty LessonList.
     */
    public LessonList() {
        this(EMPTY_SUBJECT);
    }

    /**
     * Creates a LessonList associated with a specific subject.
     *
     * @param subject the subject this list is for
     */
    public LessonList(String subject) {
        Objects.requireNonNull(subject);
        this.subject = subject;
        this.lessons = new ArrayList<>();
    }

    /**
     * Returns true if the list contains an equivalent lesson.
     */
    public boolean contains(Lesson lesson) {
        Objects.requireNonNull(lesson);
        return lessons.contains(lesson);
    }

    /**
     * Adds a lesson to the list.
     * The lesson must not already exist in the list.
     *
     * @param lesson the lesson to add
     * @throws DuplicateLessonException if the lesson is a duplicate of an existing lesson
     */
    public void addLesson(Lesson lesson) {
        Objects.requireNonNull(lesson);
        if (contains(lesson)) {
            throw new DuplicateLessonException();
        }
        lessons.add(lesson);
    }

    /**
     * Returns true if any lesson in the list belongs to the given subject.
     */
    public boolean containsSubject(String subject) {
        Objects.requireNonNull(subject);
        String trimmedLower = subject.trim().toLowerCase();
        return lessons.stream().anyMatch(lesson -> lesson.getSubject().trim().toLowerCase().equals(trimmedLower));
    }

    /**
     * Returns the internal list.
     */
    public List<Lesson> getInternalList() {
        return Collections.unmodifiableList(lessons);
    }

    /**
     * Deletes a lesson from the list.
     * The lesson must exist in the list.
     *
     * @param lesson the lesson to remove
     * @throws LessonNotFoundException if the lesson does not exist
     */
    public void deleteLesson(Lesson lesson) {
        Objects.requireNonNull(lesson);
        if (!lessons.remove(lesson)) {
            throw new LessonNotFoundException();
        }
    }

    /**
     * Returns a copy of the list of lessons.
     *
     * @return new list containing all lessons
     */
    public List<Lesson> getLessons() {
        return new ArrayList<>(lessons);
    }

    /**
     * Marks attendance for a given lesson name.
     * Returns a new LessonList with updated attendance.
     */
    public LessonList markAttendance(String lessonName, AttendanceStatus status) {
        Objects.requireNonNull(lessonName);
        Objects.requireNonNull(status);

        LessonList updated = new LessonList(this.subject);
        for (Lesson lesson : lessons) {
            if (lesson.getName().equalsIgnoreCase(lessonName)) {
                updated.addLesson(new Lesson(lesson.getName(), lesson.getSubject(), status));
            } else {
                updated.addLesson(lesson);
            }
        }
        return updated;
    }

    /**
     * Returns a formatted string of all lessons and their attendance.
     */
    public String getFormattedAttendance() {
        if (lessons.isEmpty()) {
            return "No lessons found for subject: " + subject;
        }

        StringBuilder sb = new StringBuilder();
        for (Lesson lesson : lessons) {
            sb.append(lesson.getName())
            .append(" ")
            .append(lesson.getAttendanceStatus().toString())
            .append("\n");
        }
        return sb.toString().trim();
    }

    /**
     * Returns the subject this list represents.
     */
    public String getSubject() {
        return subject;
    }

    @Override
    public String toString() {
        return "Subject: " + subject + "\nLessons: " + lessons.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof LessonList)) {
            return false;
        }
        LessonList otherList = (LessonList) other;
        return subject.equals(otherList.subject)
                && lessons.equals(otherList.lessons);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subject, lessons);
    }
}
