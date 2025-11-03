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

    /**
     * Gets an existing subject by name, or creates and adds it if it doesn't exist.
     */
    public Subject getOrCreateSubject(String subjectName) {
        Objects.requireNonNull(subjectName, "subjectName");
        String trimmedName = subjectName.trim();
        for (Subject subject : subjects) {
            if (subject.getName().equalsIgnoreCase(trimmedName)) {
                return subject;
            }
        }
        Subject newSubject = new Subject(trimmedName);
        subjects.add(newSubject);
        return newSubject;
    }

    /**
     * Gets an existing subject by name.
     */
    public java.util.Optional<Subject> getSubject(String subjectName) {
        Objects.requireNonNull(subjectName, "subjectName");
        String trimmedName = subjectName.trim();
        return subjects.stream()
                .filter(subject -> subject.getName().equalsIgnoreCase(trimmedName)).findFirst();
    }
    /**
     * Returns the {@code Subject} in this list whose name matches the given {@code name},
     * ignoring case. Returns {@code null} if no such subject exists.
     */
    public Subject findByName(String name) {
        for (Subject s : subjects) {
            if (s.getName().equalsIgnoreCase(name)) {
                return s;
            }
        }
        return null;
    }

    /**
     * Retrieves an existing {@code Subject} with the given {@code name} if it exists,
     * or creates and adds a new one if it does not.
     * This ensures that all subjects with the same name share a single instance within the list.
     */
    public Subject getOrAdd(String name) {
        Objects.requireNonNull(name);
        Subject existing = findByName(name);
        if (existing != null) {
            return existing;
        }
        Subject created = new Subject(name);
        subjects.add(created);
        return created;
    }

    public void clear() {
        subjects.clear();
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
