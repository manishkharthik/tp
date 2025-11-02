package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.lesson.LessonList;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.student.Student;
import seedu.address.model.subject.Subject;
import seedu.address.model.subject.SubjectList;
import seedu.address.model.subject.exceptions.DuplicateSubjectException;
import seedu.address.model.subject.exceptions.SubjectNotFoundException;
import seedu.address.testutil.PersonBuilder;

public class AddressBookTest {

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getPersonList());
        assertEquals(Collections.emptyList(), addressBook.getArchivedPersonList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Person> newPersons = Arrays.asList(ALICE, editedAlice);
        AddressBookStub newData = new AddressBookStub(newPersons);

        assertThrows(DuplicatePersonException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        addressBook.addPerson(ALICE);
        assertTrue(addressBook.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addPerson(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(addressBook.hasPerson(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getPersonList().remove(0));
    }

    // ============== Archive Tests ==============

    @Test
    public void archivePerson_validPerson_success() {
        addressBook.addPerson(ALICE);
        addressBook.archivePerson(ALICE);

        assertFalse(addressBook.hasPerson(ALICE));
        assertTrue(addressBook.hasArchivedPerson(ALICE));
        assertEquals(0, addressBook.getPersonList().size());
        assertEquals(1, addressBook.getArchivedPersonList().size());
    }

    @Test
    public void archivePerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.archivePerson(null));
    }

    @Test
    public void unarchivePerson_validPerson_success() {
        addressBook.addPerson(ALICE);
        addressBook.archivePerson(ALICE);
        addressBook.unarchivePerson(ALICE);

        assertTrue(addressBook.hasPerson(ALICE));
        assertFalse(addressBook.hasArchivedPerson(ALICE));
        assertEquals(1, addressBook.getPersonList().size());
        assertEquals(0, addressBook.getArchivedPersonList().size());
    }

    @Test
    public void unarchivePerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.unarchivePerson(null));
    }

    @Test
    public void hasArchivedPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasArchivedPerson(null));
    }

    @Test
    public void hasArchivedPerson_personNotInArchivedList_returnsFalse() {
        assertFalse(addressBook.hasArchivedPerson(ALICE));
    }

    @Test
    public void hasArchivedPerson_personInArchivedList_returnsTrue() {
        addressBook.addPerson(ALICE);
        addressBook.archivePerson(ALICE);
        assertTrue(addressBook.hasArchivedPerson(ALICE));
    }

    @Test
    public void addArchivedPerson_validPerson_success() {
        addressBook.addArchivedPerson(ALICE);
        assertTrue(addressBook.hasArchivedPerson(ALICE));
        assertEquals(1, addressBook.getArchivedPersonList().size());
    }

    @Test
    public void archiveMultiplePersons_success() {
        addressBook.addPerson(ALICE);
        addressBook.addPerson(BOB);
        addressBook.addPerson(CARL);

        addressBook.archivePerson(ALICE);
        addressBook.archivePerson(BOB);

        assertEquals(1, addressBook.getPersonList().size());
        assertEquals(2, addressBook.getArchivedPersonList().size());
        assertTrue(addressBook.hasArchivedPerson(ALICE));
        assertTrue(addressBook.hasArchivedPerson(BOB));
        assertTrue(addressBook.hasPerson(CARL));
    }

    @Test
    public void resetData_withArchivedPersons_replacesData() {
        // Setup original data with archived persons
        addressBook.addPerson(ALICE);
        addressBook.archivePerson(ALICE);

        // Create new data with different archived persons
        AddressBook newData = new AddressBook();
        newData.addPerson(BOB);
        newData.archivePerson(BOB);

        addressBook.resetData(newData);

        assertEquals(0, addressBook.getPersonList().size());
        assertEquals(1, addressBook.getArchivedPersonList().size());
        assertFalse(addressBook.hasArchivedPerson(ALICE));
        assertTrue(addressBook.hasArchivedPerson(BOB));
    }

    @Test
    public void getArchivedPersonList_modifyList_throwsUnsupportedOperationException() {
        addressBook.addPerson(ALICE);
        addressBook.archivePerson(ALICE);
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getArchivedPersonList().remove(0));
    }

    @Test
    public void archiveAndUnarchive_multipleTimes_success() {
        addressBook.addPerson(ALICE);

        // Archive
        addressBook.archivePerson(ALICE);
        assertTrue(addressBook.hasArchivedPerson(ALICE));
        assertFalse(addressBook.hasPerson(ALICE));

        // Unarchive
        addressBook.unarchivePerson(ALICE);
        assertFalse(addressBook.hasArchivedPerson(ALICE));
        assertTrue(addressBook.hasPerson(ALICE));

        // Archive again
        addressBook.archivePerson(ALICE);
        assertTrue(addressBook.hasArchivedPerson(ALICE));
        assertFalse(addressBook.hasPerson(ALICE));
    }

    @Test
    public void setArchivedPersons_validList_success() {
        List<Person> archivedPersons = Arrays.asList(ALICE, BOB);
        addressBook.setArchivedPersons(archivedPersons);

        assertEquals(2, addressBook.getArchivedPersonList().size());
        assertTrue(addressBook.hasArchivedPerson(ALICE));
        assertTrue(addressBook.hasArchivedPerson(BOB));
    }

    @Test
    public void toStringMethod() {
        String expected = AddressBook.class.getCanonicalName() + "{persons=" + addressBook.getPersonList() + "}";
        assertEquals(expected, addressBook.toString());
    }

    /**
     * A stub ReadOnlyAddressBook whose persons list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();
        private final ObservableList<Person> archivedPersons = FXCollections.observableArrayList();
        private SubjectList subjectList = new SubjectList();

        AddressBookStub(Collection<Person> persons) {
            this.persons.setAll(persons);
        }

        AddressBookStub(Collection<Person> persons, Collection<Person> archivedPersons) {
            this.persons.setAll(persons);
            this.archivedPersons.setAll(archivedPersons);
        }

        AddressBookStub(Collection<Person> persons, Collection<Person> archivedPersons, SubjectList subjectList) {
            this.persons.setAll(persons);
            this.archivedPersons.setAll(archivedPersons);
            this.subjectList = subjectList;
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }

        @Override
        public ObservableList<Person> getArchivedPersonList() {
            return archivedPersons;
        }

        @Override
        public SubjectList getSubjectList() {
            return subjectList;
        }

        @Override
        public LessonList getLessonList() {
            return new LessonList();
        }
    }

    @Test
    public void addPerson_student_addsSubjectsToSubjectList() {
        AddressBook addressBook = new AddressBook();
        Student student = new Student(
                new Name("John"),
                List.of(new Subject("Math"), new Subject("Science")),
                "3A", "91234567", "Paid", "Completed"
        );

        addressBook.addPerson(student);

        SubjectList subjects = addressBook.getSubjectList();
        assertTrue(subjects.contains(new Subject("Math")));
        assertTrue(subjects.contains(new Subject("Science")));
    }

    @Test
    public void addPerson_duplicateStudentSubjects_doesNotDuplicateSubjectList() {
        AddressBook addressBook = new AddressBook();
        Student student1 = new Student(
                new Name("John"),
                List.of(new Subject("Math")),
                "3A", "91234567", "Paid", "Completed"
        );
        Student student2 = new Student(
                new Name("Greg"),
                List.of(new Subject("Math")),
                "3B", "99887766", "Paid", "Completed"
        );

        addressBook.addPerson(student1);
        addressBook.addPerson(student2);

        SubjectList subjects = addressBook.getSubjectList();
        assertEquals(1, subjects.getSubjects().size());
    }

    @Test
    public void addSubject_validSubject_success() throws DuplicateSubjectException {
        AddressBook addressBook = new AddressBook();
        Subject math = new Subject("Math");
        addressBook.addSubject(math);

        assertTrue(addressBook.getSubjectList().contains(math));
    }

    @Test
    public void addSubject_duplicateSubject_throwsDuplicateSubjectException() throws DuplicateSubjectException {
        AddressBook addressBook = new AddressBook();
        Subject math = new Subject("Math");
        addressBook.addSubject(math);

        assertThrows(DuplicateSubjectException.class, () -> addressBook.addSubject(math));
    }

    @Test
    public void addSubject_nullSubject_throwsNullPointerException() {
        AddressBook addressBook = new AddressBook();
        assertThrows(NullPointerException.class, () -> addressBook.addSubject(null));
    }

    @Test
    public void deleteSubject_existingSubject_success() throws DuplicateSubjectException, SubjectNotFoundException {
        AddressBook addressBook = new AddressBook();
        Subject math = new Subject("Math");
        addressBook.addSubject(math);
        addressBook.deleteSubject(math);

        assertFalse(addressBook.getSubjectList().contains(math));
    }

    @Test
    public void deleteSubject_nonExistentSubject_throwsSubjectNotFoundException() {
        AddressBook addressBook = new AddressBook();
        Subject fake = new Subject("Science");
        assertThrows(SubjectNotFoundException.class, () -> addressBook.deleteSubject(fake));
    }

    @Test
    public void getSubjectList_returnsDefensiveCopy() throws DuplicateSubjectException {
        AddressBook addressBook = new AddressBook();
        Subject physics = new Subject("Physics");
        addressBook.addSubject(physics);

        List<Subject> subjects = addressBook.getSubjectList().getSubjects();
        subjects.clear();
        assertEquals(1, addressBook.getSubjectList().getSubjects().size());
    }

    @Test
    public void addPerson_nonStudent_doesNotAddSubjects() {
        AddressBook addressBook = new AddressBook();
        addressBook.addPerson(ALICE);
        assertEquals(0, addressBook.getSubjectList().getSubjects().size());
    }
}
