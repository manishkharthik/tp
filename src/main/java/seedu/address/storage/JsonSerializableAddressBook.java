package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;

@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {
    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();
    private final List<JsonAdaptedPerson> archivedPersons = new ArrayList<>();
    private final List<JsonAdaptedSubject> subjectList = new ArrayList<>();
    private final List<JsonAdaptedLesson> lessons = new ArrayList<>();

    @JsonCreator
    public JsonSerializableAddressBook(
            @JsonProperty("persons") List<JsonAdaptedPerson> persons,
            @JsonProperty("students") List<JsonAdaptedPerson> students,
            @JsonProperty("archivedPersons") List<JsonAdaptedPerson> archivedPersons,
            @JsonProperty("lessons") List<JsonAdaptedLesson> lessons) {
        if (persons != null) {
            this.persons.addAll(persons);
        } else if (students != null) {
            this.persons.addAll(students);
        }
        if (archivedPersons != null) {
            this.archivedPersons.addAll(archivedPersons);
        }
        if (lessons != null) {
            this.lessons.addAll(lessons);
        }
    }

    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
        persons.addAll(source.getPersonList().stream()
                .map(JsonAdaptedPerson::new).collect(Collectors.toList()));
        archivedPersons.addAll(source.getArchivedPersonList().stream()
                .map(JsonAdaptedPerson::new).collect(Collectors.toList()));
        subjectList.addAll(source.getSubjectList().getSubjects().stream()
                .map(JsonAdaptedSubject::new).collect(Collectors.toList()));
        lessons.addAll(source.getLessonList().getInternalList().stream()
                .map(JsonAdaptedLesson::new).collect(Collectors.toList()));
    }

    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();
        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Person person = jsonAdaptedPerson.toModelType();
            if (addressBook.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            addressBook.addPerson(person);
        }
        for (JsonAdaptedPerson jsonAdaptedPerson : archivedPersons) {
            Person person = jsonAdaptedPerson.toModelType();
            if (addressBook.hasArchivedPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            addressBook.addArchivedPerson(person);
        }
        for (JsonAdaptedLesson jsonAdaptedLesson : lessons) {
            addressBook.addLesson(jsonAdaptedLesson.toModelType());
        }
        return addressBook;
    }
}
