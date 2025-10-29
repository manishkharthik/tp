package seedu.address.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.person.Name;
import seedu.address.model.student.Student;
import seedu.address.model.subject.Subject;

public class MessagesTest {

    @Test
    public void getErrorMessageForDuplicatePrefixes_singlePrefix_success() {
        Prefix prefix = new Prefix("n/");
        String expected = Messages.MESSAGE_DUPLICATE_FIELDS + "n/";
        String result = Messages.getErrorMessageForDuplicatePrefixes(prefix);
        assertEquals(expected, result);
    }

    @Test
    public void getErrorMessageForDuplicatePrefixes_multiplePrefixes_success() {
        Prefix prefix1 = new Prefix("n/");
        Prefix prefix2 = new Prefix("p/");
        String result = Messages.getErrorMessageForDuplicatePrefixes(prefix1, prefix2);

        assertTrue(result.contains("n/"));
        assertTrue(result.contains("p/"));
        assertTrue(result.startsWith(Messages.MESSAGE_DUPLICATE_FIELDS));
    }


    @Test
    public void format_studentFormatsCorrectly() {
        Student student = new Student(
                new Name("Bob Tan"),
                List.of(new Subject("Math"), new Subject("Science")),
                "3B",
                "91234567",
                "Paid",
                "Completed"
        );

        String result = Messages.format(student);

        assertTrue(result.contains("Bob Tan"));
        assertTrue(result.contains("Class: 3B"));
        assertTrue(result.contains("Subjects:"));
        assertTrue(result.contains("Math"));
        assertTrue(result.contains("Science"));
        assertTrue(result.contains("Emergency Contact: 91234567"));
        assertTrue(result.contains("Payment Status: Paid"));
        assertTrue(result.contains("Assignment Status: Completed"));
    }
}
