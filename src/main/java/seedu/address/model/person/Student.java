package seedu.address.model.person;

import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Creates student object.
 */
public class Student extends Person {

    /**
     * Every field must be present and not null.
     *
     * @param name Name of the student
     * @param phone Phone number of the student
     * @param email Email of the student
     * @param address Address of the student
     * @param tags Tags associated with the student
     */
    public Student(Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        super(name, phone, email, address, tags);
    }


}
