package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECTS;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddLessonCommand;
import seedu.address.model.lesson.Lesson;

public class AddLessonCommandParserTest {

    private AddLessonCommandParser parser = new AddLessonCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        // normal lesson
        Lesson expectedLesson = new Lesson("Algebra", "Math");
        assertParseSuccess(parser, " " + PREFIX_SUBJECTS + "Math "
                + PREFIX_NAME + "Algebra", new AddLessonCommand(expectedLesson));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddLessonCommand.MESSAGE_USAGE);

        // missing subject prefix
        assertParseFailure(parser, PREFIX_NAME + "Algebra", expectedMessage);

        // missing name prefix
        assertParseFailure(parser, PREFIX_SUBJECTS + "Math", expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, "Math Algebra", expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // empty subject
        assertParseFailure(parser, " " + PREFIX_SUBJECTS + " "
                + PREFIX_NAME + "Algebra", Lesson.MESSAGE_CONSTRAINTS);

        // empty name
        assertParseFailure(parser, " " + PREFIX_SUBJECTS + "Math "
                + PREFIX_NAME + " ", Lesson.MESSAGE_CONSTRAINTS);
    }
}
