package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.attendance.AttendanceStatus;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.LessonList;
import seedu.address.model.person.Name;
import seedu.address.model.student.Student;
import seedu.address.model.subject.Subject;

/**
 * Unit tests for {@link ListAttendanceCommand}.
 */
public class ListAttendanceCommandTest {

    private Model model;
    private Student studentWithMath;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        // Create test data
        Subject math = new Subject("Math");
        Lesson lesson1 = new Lesson("L1", "Math", AttendanceStatus.PRESENT);
        Lesson lesson2 = new Lesson("L2", "Math", AttendanceStatus.ABSENT);
        LessonList lessonList = new LessonList(math.getName());
        lessonList.addLesson(lesson1);
        lessonList.addLesson(lesson2);

        studentWithMath = new Student(
                new Name("John Doe"),
                List.of(math),
                "Class 1A",
                "91234567",
                "Paid",
                "Submitted"
        );

        // Add to model
        model.addPerson(studentWithMath);
    }

    @Test
    public void execute_studentNotFound_throwsCommandException() {
        ListAttendanceCommand command = new ListAttendanceCommand("Alice", "Math");
        assertThrows(CommandException.class, 
            () -> command.execute(model), 
            String.format(ListAttendanceCommand.MESSAGE_STUDENT_NOT_FOUND, "Alice"));
    }

    @Test
    public void execute_subjectNotFound_throwsCommandException() {
        ListAttendanceCommand command = new ListAttendanceCommand("John", "Science");
        assertThrows(CommandException.class, 
            () -> command.execute(model),
            String.format(ListAttendanceCommand.MESSAGE_SUBJECT_NOT_FOUND, "Science", "John"));
    }

    @Test
    public void execute_noLessons_throwsCommandException() {
        Student student = new Student(new Name("Mary"),
                Arrays.asList(new Subject("Chemistry"), new Subject("Physics")),
                "Class 2B",
                "98765432",
                "Unpaid",
                "Not Submitted");
        Subject emptySubject = new Subject("Physics");
        model.addPerson(student);

        ListAttendanceCommand command = new ListAttendanceCommand("Mary", "Physics");
        assertThrows(CommandException.class,
            () -> command.execute(model),
            String.format(ListAttendanceCommand.MESSAGE_NO_LESSONS, "Mary", "Physics"));
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        ListAttendanceCommand first = new ListAttendanceCommand("John", "Math");
        ListAttendanceCommand second = new ListAttendanceCommand("john", "MATH");
        assertEquals(first, second);
    }

    @Test
    public void equals_differentValues_returnsFalse() {
        ListAttendanceCommand first = new ListAttendanceCommand("John", "Math");
        ListAttendanceCommand second = new ListAttendanceCommand("Alice", "Science");
        assertEquals(false, first.equals(second));
    }

    @Test
    public void execute_partialNameDoesNotMatch_throwsCommandException() {
        // "John Doe" exists, but we pass only "John"
        ListAttendanceCommand command = new ListAttendanceCommand("John Doe", "Math");

        assertThrows(CommandException.class,
                () -> command.execute(model),
                String.format(ListAttendanceCommand.MESSAGE_STUDENT_NOT_FOUND, "John Doe"));
    }  
}
