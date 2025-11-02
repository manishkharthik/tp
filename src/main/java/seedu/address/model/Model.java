package seedu.address.model;

import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.person.Person;
import seedu.address.model.subject.Subject;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Lesson> PREDICATE_SHOW_ALL_LESSONS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a student with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Returns true if an archived student with the same identity as {@code person} exists in the address book.
     */
    boolean hasArchivedPerson(Person person);

    boolean hasSameStudentInCurrent(Person toAdd);

    boolean hasSameStudentInArchive(Person toAdd);

    String checkDuplicateStudentLocation(Person toAdd);

    /**
     * Deletes the given person from the current list
     * The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Deletes the given person from the archived list
     */
    void deleteArchivedPerson(Person target);

    /**
     * Clears the current students list.
     */
    void clearCurrentStudents();

    /**
     * Clears the archived students list.
     */
    void clearArchivedStudents();

    /**
     * Archives the given person.
     * The person must exist in the address book.
     */
    void archivePerson(Person target);

    /**
     * Adds the given person to the current list
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Adds the given person to the archived list
     * {@code person} must not already exist in the address book.
     */
    void addArchivedPerson(Person person);

    /**
     * Replaces the given person {@code target} in current with {@code editedPerson}.
     * {@code target} must exist in the current list.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the current list.
     */
    void setPerson(Person target, Person editedPerson);

    /**
     * Replaces the given person {@code target} in archived list with {@code editedPerson}.
     * {@code target} must exist in the archived list.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the archived list.
     */
    void setArchivedPerson(Person target, Person editedPerson);

    /*
     * Toggles based on whether current or archived list of students are being displayed
     */
    void setViewingArchived(boolean isViewingArchived);

    /*
     * Checks whether current or archived list of students are being accessed
     */
    public boolean isViewingArchived();

    /*
     * Returns an unmodifiable view of the filtered person list
     */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Returns an unmodifiable view of the filtered archive person list
     */
    ObservableList<Person> getFilteredArchivedPersonList();

    /**
     * Updates the filter of the filtered archive person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredArchivedPersonList(Predicate<Person> predicate);

    /**
     * Returns true if a lesson with the same identity as {@code lesson} exists in the list.
     */
    boolean hasLesson(Lesson lesson);

    /**
     * Returns true if the given subject exists in the list.
     */
    boolean hasSubject(String subject);

    /**
     * Adds the given lesson.
     * {@code lesson} must not already exist in the list.
     */
    void addLesson(Lesson lesson);

    /**
     * Deletes the given lesson.
     * {@code lesson} must exist in the list.
     */
    void deleteLesson(Lesson lesson);

    /** Returns an unmodifiable view of the filtered lesson list */
    ObservableList<Lesson> getFilteredLessonList();

    /* Returns an unmodifiable view of the lesson list */
    ObservableList<Lesson> getLessonList();

    /**
     * Updates the filter of the filtered lesson list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredLessonList(Predicate<Lesson> predicate);

    /**
     * Unarchives the given person (moves from archived list back to active list).
     */
    void unarchivePerson(Person target);

    /***
     * Returns subject with the given name.
     * The subject must exist in the address book.
     */
    Subject getSubject(String subjectName);

    // /* Returns subject if it exist*/
    Optional<Subject> findSubjectByName(String name);

}
