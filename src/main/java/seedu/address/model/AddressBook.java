package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.LessonList;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.student.Student;
import seedu.address.model.subject.Subject;
import seedu.address.model.subject.SubjectList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;
    private final UniquePersonList archivedPersons;
    private final LessonList lessonList;
    private final SubjectList subjectList;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
        archivedPersons = new UniquePersonList();
        lessonList = new LessonList();
        subjectList = new SubjectList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setPersons(persons);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
        setArchivedPersons(newData.getArchivedPersonList());
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addPerson(Person p) {
        persons.add(p);
        if (p instanceof Student) {
            Student s = (Student) p;
            List<Subject> subjects = s.getSubjects();
            for (int i = 0; i < subjects.size(); i++) {
                if (!subjectList.contains(subjects.get(i))) {
                    subjectList.addSubject(new Subject(subjects.get(i).getName()));
                }
            }
        }
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);

        persons.setPerson(target, editedPerson);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Person key) {
        persons.remove(key);
    }

    /**
     * Archives {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void archivePerson(Person key) {
        persons.remove(key);
        archivedPersons.add(key);
    }

    /**
     * Sets list of archived persons to param
     * @param archivedPersons list of archived persons
     */
    public void setArchivedPersons(List<Person> archivedPersons) {
        this.archivedPersons.setPersons(archivedPersons);
    }

    /**
     * Unarchives {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the archived list.
     */
    public void unarchivePerson(Person key) {
        requireNonNull(key);
        assert key != null : "Person to unarchive should not be null";
        assert archivedPersons.contains(key) : "Person must be in archived list";

        archivedPersons.remove(key);
        persons.add(key);
    }

    /**
     * Returns true if an archived person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasArchivedPerson(Person person) {
        requireNonNull(person);
        return archivedPersons.contains(person);
    }

    /**
     * Adds an archived person directly to the archived list.
     * Used during loading from storage.
     */
    public void addArchivedPerson(Person p) {
        archivedPersons.add(p);
    }

    //// lesson-level operations

    /**
     * Returns true if a lesson with the same identity as {@code lesson} exists in the address book.
     * @param lesson
     * @return
     */
    public boolean hasLesson(Lesson lesson) {
        requireNonNull(lesson);
        return lessonList.contains(lesson);
    }

    /**
     * Adds a lesson to the address book.
     * The lesson must not already exist in the address book.
     */
    public void addLesson(Lesson lesson) {
        requireNonNull(lesson);
        lessonList.addLesson(lesson);
    }

    /**
     * Deletes the given lesson from the address book.
     * The lesson must exist in the address book.
     */
    public void deleteLesson(Lesson lesson) {
        requireNonNull(lesson);
        lessonList.deleteLesson(lesson);
    }

    /**
     * Returns the LessonList object.
     */
    public LessonList getLessonList() {
        return lessonList;
    }

    //// subject-level operations

    /**
     * Adds a subject to the address book.
     * The subject must not already exist in the address book.
     */
    public void addSubject(Subject subject) {
        requireNonNull(subject);
        subjectList.addSubject(subject);
    }

    /**
     * Deletes the given subject from the address book.
     * The subject must exist in the address book.
     */
    public void deleteSubject(Subject subject) {
        requireNonNull(subject);
        subjectList.deleteSubject(subject);
    }

    /**
     * Returns the SubjectList object.
     */
    @Override
    public SubjectList getSubjectList() {
        return subjectList;
    }

    //// util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("persons", persons)
                .toString();
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Person> getArchivedPersonList() {
        return archivedPersons.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddressBook)) {
            return false;
        }

        AddressBook otherAddressBook = (AddressBook) other;
        return persons.equals(otherAddressBook.persons);
    }

    @Override
    public int hashCode() {
        return persons.hashCode();
    }
}
