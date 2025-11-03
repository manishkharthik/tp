package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.LessonList;
import seedu.address.model.person.Person;
import seedu.address.model.student.Student;
import seedu.address.model.subject.Subject;
import seedu.address.model.subject.SubjectList;

/**
 * Represents the in-memory model of the TutorTrack data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    /** {@code Predicate} that always evaluate to true */
    private static final Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;
    private static final Predicate<Lesson> PREDICATE_SHOW_ALL_LESSONS = unused -> true;

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Person> filteredArchivedPersons;
    private final FilteredList<Lesson> filteredLessons;
    private final LessonList lessonList;
    private final SubjectList subjectList;
    private boolean isViewingArchived = false;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with TutorTrack: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        this.lessonList = this.addressBook.getLessonList();
        this.subjectList = this.addressBook.getSubjectList();
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        filteredArchivedPersons = new FilteredList<>(this.addressBook.getArchivedPersonList());
        filteredLessons = new FilteredList<>(this.lessonList.asObservableList());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasPerson(person);
    }

    @Override
    public boolean hasArchivedPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasArchivedPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        addressBook.removePerson(target);
    }

    @Override
    public void deleteArchivedPerson(Person target) {
        requireNonNull(target);
        addressBook.removeArchivedPerson(target);
        updateFilteredArchivedPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void archivePerson(Person target) {
        addressBook.archivePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void addArchivedPerson(Person person) {
        requireNonNull(person);
        addressBook.addArchivedPerson(person);
        updateFilteredArchivedPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public boolean hasSameStudentInCurrent(Person toAdd) {
        requireNonNull(toAdd);
        return addressBook.getPersonList().stream()
                .filter(existing -> existing instanceof Student)
                .anyMatch(existing -> ((Student) existing).isSameStudent((Student) toAdd));
    }

    @Override
    public boolean hasSameStudentInArchive(Person toAdd) {
        requireNonNull(toAdd);
        return addressBook.getArchivedPersonList().stream()
                .filter(existing -> existing instanceof Student)
                .anyMatch(existing -> ((Student) existing).isSameStudent((Student) toAdd));
    }

    @Override
    public String checkDuplicateStudentLocation(Person toAdd) {
        requireNonNull(toAdd);
        if (hasSameStudentInCurrent(toAdd)) {
            return "current list";
        } else if (hasSameStudentInArchive(toAdd)) {
            return "archived list";
        } else {
            return null;
        }
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);
        addressBook.setPerson(target, editedPerson);
    }

    @Override
    public void setArchivedPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);
        addressBook.setArchivedPerson(target, editedPerson);
    }

    @Override
    public void setViewingArchived(boolean isViewingArchived) {
        this.isViewingArchived = isViewingArchived;
    }

    @Override
    public boolean isViewingArchived() {
        return isViewingArchived;
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return addressBook.equals(otherModelManager.addressBook)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredPersons.equals(otherModelManager.filteredPersons);
    }

    //=========== Filtered Archived Person List Accessors =============================================================
    /**
     * Returns an unmodifiable view of the list of archived {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredArchivedPersonList() {
        return filteredArchivedPersons;
    }

    @Override
    public void updateFilteredArchivedPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredArchivedPersons.setPredicate(predicate);
    }

    //=========== Lesson List Methods =============================================================

    @Override
    public boolean hasLesson(Lesson lesson) {
        requireNonNull(lesson);
        return lessonList.contains(lesson);
    }

    @Override
    public boolean hasSubject(String subject) {
        requireNonNull(subject);
        return lessonList.containsSubject(subject);
    }

    @Override
    public void addLesson(Lesson lesson) {
        requireNonNull(lesson);
        lessonList.addLesson(lesson);
        updateFilteredLessonList(PREDICATE_SHOW_ALL_LESSONS);
    }

    @Override
    public ObservableList<Lesson> getFilteredLessonList() {
        return filteredLessons;
    }

    @Override
    public ObservableList<Lesson> getLessonList() {
        return filteredLessons;
    }

    @Override
    public void updateFilteredLessonList(Predicate<Lesson> predicate) {
        requireNonNull(predicate);
        filteredLessons.setPredicate(predicate);
    }

    @Override
    public void deleteLesson(Lesson lesson) {
        requireNonNull(lesson);
        lessonList.deleteLesson(lesson);
    }

    @Override
    public void clearCurrentStudents() {
        addressBook.clearCurrentStudents();
    }

    @Override
    public void clearArchivedStudents() {
        addressBook.clearArchivedStudents();
    }

    @Override
    public void clearLessons() {
        addressBook.clearLessons();
    }

    @Override
    public void clearSubjects() {
        addressBook.clearSubjects();
    }

    @Override
    public Subject getSubject(String subjectName) {
        requireNonNull(subjectName);
        if (!hasSubject(subjectName)) {
            throw new IllegalArgumentException("Subject not found: " + subjectName);
        }

        // Create a Subject object with the lessons that belong to this subject
        Subject subject = new Subject(subjectName);

        // Add all lessons that belong to this subject
        for (Lesson lesson : lessonList.getInternalList()) {
            if (lesson.getSubject().equals(subjectName)) {
                subject.addLesson(lesson);
            }
        }

        return subject;
    }

    @Override
    public void unarchivePerson(Person target) {
        requireNonNull(target);
        assert target != null : "Person to unarchive should not be null";

        logger.info("Unarchiving person: " + target.getName());
        addressBook.unarchivePerson(target);
    }

    @Override
    public Optional<Subject> findSubjectByName(String name) {
        requireNonNull(name);
        return subjectList.getSubjects().stream()
                .filter(s -> s.getName().equalsIgnoreCase(name))
                .findFirst();
    }
}
