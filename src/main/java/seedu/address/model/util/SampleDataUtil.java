package seedu.address.model.util;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.student.Student;
import seedu.address.model.subject.Subject;
import seedu.address.model.tag.Tag;
/**
* Contains utility methods for populating {@code AddressBook} with sample data.
*/
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Student(new Name("Alex Yeoh"), List.of(new Subject("Math")), "3B",
                "87438807", "Paid", "Completed"),
            new Student(new Name("Bernice Yu"), List.of(new Subject("Science"), new Subject("Math")), "3A",
                "99272758", "Unpaid", "Incomplete"),
            new Student(new Name("Charlotte Oliveiro"), List.of(new Subject("English")), "2B",
                "93210283", "Paid", "Completed"),
            new Student(new Name("David Li"), List.of(new Subject("History")), "4C",
                "91031282", "Unpaid", "Incomplete"),
            new Student(new Name("Irfan Ibrahim"), List.of(new Subject("Physics")), "5A",
                "92492021", "Paid", "Completed"),
            new Student(new Name("Roy Balakrishnan"), List.of(new Subject("Chemistry")), "1D",
                "92624417", "Unpaid", "Incomplete")
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
