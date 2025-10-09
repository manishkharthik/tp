package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.tag.Tag;

public class StudentTest {

    @Test
    public void constructor_allFieldsPresent_success() {
        Name name = new Name("John Doe");
        Phone phone = new Phone("12345678");
        Email email = new Email("john@example.com");
        Address address = new Address("123 Main St");
        Set<Tag> tags = Set.of(new Tag("CS2103"));

        Student student = new Student(name, phone, email, address, tags);

        assertNotNull(student);
        assertEquals(name, student.getName());
        assertEquals(phone, student.getPhone());
        assertEquals(email, student.getEmail());
        assertEquals(address, student.getAddress());
        assertEquals(tags, student.getTags());
    }

    @Test
    public void constructor_nullName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new Student(null, new Phone("12345678"), new Email("john@example.com"),
                        new Address("123 Main St"), Set.of()));
    }
}

