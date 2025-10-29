package seedu.address.model.subject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import seedu.address.model.lesson.Lesson;
import seedu.address.model.subject.exceptions.DuplicateSubjectException;
import seedu.address.model.subject.exceptions.SubjectNotFoundException;

/**
 * Represents a list of lessons for a specific subject.
 * Each LessonList instance is tied to a single subject.
 */
public class SubjectList {
    private final List<Subject> subjects;

    /**
     * Creates a SubjectList for the TutorTrack.
     */
    public SubjectList() {
        this.subjects = new ArrayList<>();
    }

    /**
     * Returns true if the list contains an equivalent subject.
     * @param subject is a Subject
     */
    public boolean contains(Subject subject) {
        Objects.requireNonNull(subject);
        return subjects.contains(subject);
    }

    /**
     * Returns true if the list contains an equivalent subject.
     * @param subjectName is a String
     */
    public boolean contains(String subjectName) {
        Objects.requireNonNull(subjectName, "subjectName");
        return subjects.stream()
                .map(Subject::getName) // or s -> s.getName()
                .filter(Objects::nonNull)
                .anyMatch(name -> name.equalsIgnoreCase(subjectName));
    }

    /**
     * Adds a subject to the list.
     * The subject must not already exist in the list.
     *
     * @param subject the subject to add
     * @throws DuplicateSubjectException if the subject is a duplicate of an
     *                                   existing lesson
     */
    public void addSubject(Subject subject) {
        Objects.requireNonNull(subject);
        if (contains(subject)) {
            throw new DuplicateSubjectException(); // Add to exceptions
        }
        subjects.add(subject);
    }

    /**
     * Returns true if any subject in the list belongs to the given subject.
     */
    public boolean containsLesson(Lesson lesson) {
        Objects.requireNonNull(lesson);
        return subjects.stream().anyMatch(subject -> subject.containsLesson(lesson));
    }

    /**
     * Returns the internal list.
     */
    public List<Subject> getInternalList() {
        return Collections.unmodifiableList(subjects);
    }

    /**
     * Deletes a subject from the list.
     * The subject must exist in the list.
     *
     * @param subject the subject to remove
     * @throws SubjectNotFoundException if the subject does not exist
     */
    public void deleteSubject(Subject subject) {
        Objects.requireNonNull(subject);
        if (!subjects.remove(subject)) {
            throw new SubjectNotFoundException(); // Add exception
        }
    }

    /**
     * Returns a copy of the list of subjects.
     *
     * @return new list containing all subjects
     */
    public List<Subject> getSubjects() {
        return new ArrayList<>(subjects);
    }

    @Override
    public String toString() {
        return "Subjects:  " + subjects.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof SubjectList)) {
            return false;
        }
        SubjectList otherList = (SubjectList) other;
        return this.subjects.equals(otherList.subjects);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subjects);
    }
}
