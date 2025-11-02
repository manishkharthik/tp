package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLASS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMERGENCY_CONTACT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAYMENT_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECTS;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Adds a student to the TutorTrack.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a student to the list of students. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_CLASS + "CLASS "
            + PREFIX_SUBJECTS + "SUBJECT [s/MORE_SUBJECTS]... "
            + PREFIX_EMERGENCY_CONTACT + "EMERGENCY_CONTACT "
            + "[" + PREFIX_PAYMENT_STATUS + "PAYMENT_STATUS] "
            + "[" + PREFIX_ASSIGNMENT_STATUS + "ASSIGNMENT_STATUS]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + " \"John Tan\" "
            + PREFIX_CLASS + "3B "
            + PREFIX_SUBJECTS + "Math s/Science "
            + PREFIX_EMERGENCY_CONTACT + "91234567 "
            + PREFIX_PAYMENT_STATUS + "Paid "
            + PREFIX_ASSIGNMENT_STATUS + "Completed" + "\n"
            + "Note: Use quotes for names with special characters, optional to use quotes for regular names";

    public static final String MESSAGE_SUCCESS = "New student added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This student already exists in the student list";
    public static final String MESSAGE_UNABLE_ARCHIVE =
        "Cannot add a student while viewing the archived list.\n"
        + "Use the 'archive' command to move an existing student to the archive.";

    private final Person toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddCommand(Person person) {
        requireNonNull(person);
        toAdd = person;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        assert model != null : "Model should not be null";
        assert toAdd != null : "Person to add should not be null";

        if (model.isViewingArchived()) {
            throw new CommandException(MESSAGE_UNABLE_ARCHIVE);
        } else {
            if (model.hasPerson(toAdd)) {
                throw new CommandException(MESSAGE_DUPLICATE_PERSON);
            }
            model.addPerson(toAdd);
        }

        CommandResult result = new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
        assert result != null : "CommandResult should not be null";
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddCommand)) {
            return false;
        }

        AddCommand otherAddCommand = (AddCommand) other;
        return toAdd.equals(otherAddCommand.toAdd);
    }

    @Override
    public int hashCode() {
        return toAdd.hashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("toAdd", toAdd).toString();
    }
}
