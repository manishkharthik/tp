package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.lesson.LessonList;
import seedu.address.model.person.Person;
import seedu.address.model.subject.SubjectList;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Person> getPersonList();

    /**
     * Returns an unmodifiable view of the archived persons list.
     */
    ObservableList<Person> getArchivedPersonList();

    SubjectList getSubjectList();

    /**
     * Returns the LessonList object.
     */
    LessonList getLessonList();
}
