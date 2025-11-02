package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.model.Model;

/**
 * Lists all students in the TutorTrack to the tutor.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all students";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Lists all students in the tutor track.\n"
            + "This command does not take any parameters.\n"
            + "Example: " + COMMAND_WORD;

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        assert model != null : "Model should not be null";

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.setViewingArchived(false);

        CommandResult result = new CommandResult(MESSAGE_SUCCESS);
        assert result != null : "CommandResult should not be null";

        return result;
    }

    @Override
    public boolean equals(Object other) {
        // All ListCommand instances are identical since the command has no parameters
        return other == this || (other instanceof ListCommand);
    }

    @Override
    public String toString() {
        return ListCommand.class.getCanonicalName();
    }
}
