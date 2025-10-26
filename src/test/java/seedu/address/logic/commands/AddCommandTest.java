package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.student.Student;
import seedu.address.model.subject.Subject;
import seedu.address.testutil.PersonBuilder;

public class AddCommandTest {

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Person validPerson = new PersonBuilder().build();

        CommandResult commandResult = new AddCommand(validPerson).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(validPerson)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validPerson), modelStub.personsAdded);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Person validPerson = new PersonBuilder().build();
        AddCommand addCommand = new AddCommand(validPerson);
        ModelStub modelStub = new ModelStubWithPerson(validPerson);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_PERSON, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Person alice = new PersonBuilder().withName("Alice").build();
        Person bob = new PersonBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different person -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    @Test
    public void execute_studentWithMultipleSubjects_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Student studentWithMultipleSubjects = new Student(new Name("John"),
                new ArrayList<>(Arrays.asList("Math", "Science")),
                "3A", "91234567",
                "Paid", "Completed");

        CommandResult commandResult = new AddCommand(studentWithMultipleSubjects).execute(modelStub);

        String successPrefix = String.format(AddCommand.MESSAGE_SUCCESS.split(": ")[0]);
        assertTrue(commandResult.getFeedbackToUser().startsWith(successPrefix),
                "Success message should start with 'New student added'");

        boolean found = modelStub.personsAdded.stream()
                .filter(p -> p instanceof Student)
                .map(p -> (Student) p)
                .anyMatch(s -> s.isSameStudent(studentWithMultipleSubjects));
        assertTrue(found, "Added student should be in the list");
    }

    @Test
    public void execute_addToNonEmptyList_success() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Student existingStudent = new Student(new Name("John"), new ArrayList<>(Arrays.asList("Math", "Science")),
                "3A", "91234567",
                "Paid", "Completed");

        modelStub.addPerson(existingStudent);

        Student newStudent = new Student(new Name("Bob"), new ArrayList<>(Arrays.asList("Math", "Science")),
                "3A", "91234567",
                "Paid", "Completed");

        CommandResult commandResult = new AddCommand(newStudent).execute(modelStub);

        String successPrefix = String.format(AddCommand.MESSAGE_SUCCESS.split(": ")[0]);
        assertTrue(commandResult.getFeedbackToUser().startsWith(successPrefix),
                "Success message should start with 'New student added'");

        assertEquals(2, modelStub.personsAdded.size(),
                "Model should contain exactly 2 students");

        boolean containsExistingStudent = modelStub.personsAdded.stream()
                .filter(p -> p instanceof Student)
                .map(p -> (Student) p)
                .anyMatch(s -> s.isSameStudent(existingStudent));
        assertTrue(containsExistingStudent, "Model should contain the existing student");

        boolean containsNewStudent = modelStub.personsAdded.stream()
                .filter(p -> p instanceof Student)
                .map(p -> (Student) p)
                .anyMatch(s -> s.isSameStudent(newStudent));
        assertTrue(containsNewStudent, "Model should contain the new student");
    }

    @Test
    public void execute_addStudentWithSameNameDifferentDetails_success() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Student student1 = new Student(new Name("John"), new ArrayList<>(Arrays.asList("Math", "Science")),
                "3A", "91234567",
                "Paid", "Completed");

        Student student2 = new Student(new Name("John"), new ArrayList<>(Arrays.asList("Math", "Science")),
                "3B", "91234568",
                "Paid", "Completed");

        new AddCommand(student1).execute(modelStub);
        CommandResult commandResult = new AddCommand(student2).execute(modelStub);

        assertEquals(2, modelStub.personsAdded.size());
        assertFalse(student1.isSameStudent(student2)); // Verify they're different students
        assertTrue(modelStub.personsAdded.contains(student1));
        assertTrue(modelStub.personsAdded.contains(student2));
    }

    @Test
    public void toStringMethod() {
        AddCommand addCommand = new AddCommand(ALICE);
        String expected = AddCommand.class.getCanonicalName() + "{toAdd=" + ALICE + "}";
        assertEquals(expected, addCommand.toString());
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        Person validPerson = new PersonBuilder().build();
        AddCommand addCommand = new AddCommand(validPerson);

        assertThrows(NullPointerException.class, () -> addCommand.execute(null));
    }

    @Test
    public void hashCodeMethod() {
        Person alice = new PersonBuilder().withName("Alice").build();
        Person bob = new PersonBuilder().withName("Bob").build();
        AddCommand addAliceCommand1 = new AddCommand(alice);
        AddCommand addAliceCommand2 = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same person -> same hashCode
        assertEquals(addAliceCommand1.hashCode(), addAliceCommand2.hashCode());

        // different person -> different hashCode
        assertFalse(addAliceCommand1.hashCode() == addBobCommand.hashCode());

        // hashCode should be consistent with equals
        assertTrue(addAliceCommand1.equals(addAliceCommand2));
        assertEquals(addAliceCommand1.hashCode(), addAliceCommand2.hashCode());
    }

    @Test
    public void getCommandWord() {
        assertEquals("add", AddCommand.COMMAND_WORD);
    }

    @Test
    public void getMessageUsage() {
        assertTrue(AddCommand.MESSAGE_USAGE.contains("add"));
        assertTrue(AddCommand.MESSAGE_USAGE.contains("student"));
        // Test that new student-specific fields are included in usage message
        assertTrue(AddCommand.MESSAGE_USAGE.contains("c/")); // class
        assertTrue(AddCommand.MESSAGE_USAGE.contains("s/")); // subjects
        assertTrue(AddCommand.MESSAGE_USAGE.contains("ec/")); // emergency contact
        // removed: attendance prefix assertion (feature not in constructor anymore)
        assertTrue(AddCommand.MESSAGE_USAGE.contains("pay/")); // payment status
        assertTrue(AddCommand.MESSAGE_USAGE.contains("asg/")); // assignment status
    }

    @Test
    public void getMessageSuccess() {
        assertTrue(AddCommand.MESSAGE_SUCCESS.contains("student added"));
    }

    @Test
    public void getMessageDuplicatePerson() {
        assertTrue(AddCommand.MESSAGE_DUPLICATE_PERSON.contains("student already exists"));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    @Nested
    public class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void archivePerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Lesson> getFilteredLessonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredLessonList(Predicate<Lesson> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Lesson> getLessonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredArchivedPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredArchivedPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasLesson(Lesson lesson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void unarchivePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteLesson(Lesson lesson) {
            throw new AssertionError("This method should not be called.");
        }

        @Test
        public void execute_validPerson_assertionsPass() throws CommandException {
            ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
            Person validPerson = new PersonBuilder().build();

            AddCommand command = new AddCommand(validPerson);

            // This executes all assertion lines in execute()
            CommandResult result = command.execute(modelStub);

            assertNotNull(result);
            assertNotNull(result.getFeedbackToUser());
            assertTrue(result.getFeedbackToUser().contains(validPerson.getName().toString()));
        }

        @Override
        public boolean hasSubject(String subject) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addLesson(Lesson lesson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Subject getSubject(String subjectName) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithPerson extends ModelStub {
        private final Person person;

        ModelStubWithPerson(Person person) {
            requireNonNull(person);
            this.person = person;
        }

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return this.person.isSamePerson(person);
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<Person> personsAdded = new ArrayList<>();

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            if (person instanceof Student) {
                return personsAdded.stream()
                        .filter(p -> p instanceof Student)
                        .anyMatch(p -> ((Student) p).isSameStudent((Student) person));
            }
            return personsAdded.stream().anyMatch(person::isSamePerson);
        }

        @Override
        public void addPerson(Person person) {
            requireNonNull(person);
            personsAdded.add(person);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}
