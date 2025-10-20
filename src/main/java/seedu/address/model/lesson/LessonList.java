package seedu.address.model.lesson;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a list of lessons for a specific subject.
 * Each LessonList instance is tied to a single subject.
 */
public class LessonList {
    private final List<Lesson> lessons;
    private final String subject;

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

    //@TODO: Update both lesson list and lesson class so that they have their supported functions
    /**
     * Adds a lesson to the list if not already present.
     *
     * @param lesson the lesson to add
     */
    public void addLesson(Lesson lesson) {
        Objects.requireNonNull(lesson);
        if (!lessons.contains(lesson)) {
            lessons.add(lesson);
        }
    }

    /**
     * Deletes a lesson from the list if present.
     *
     * @param lesson the lesson to remove
     */
    public void deleteLesson(Lesson lesson) {
        Objects.requireNonNull(lesson);
        lessons.remove(lesson);
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