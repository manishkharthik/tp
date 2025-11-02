package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

/**
 * Clears the TutorTrack.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clearcurrent";
    public static final String MESSAGE_SUCCESS = "Current students have been cleared!";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        assert model != null : "Model should not be null";
        model.clearCurrentStudents();
        CommandResult result = new CommandResult(MESSAGE_SUCCESS);
        assert result != null : "CommandResult should not be null";

        return result;
    }

    @Override
    public boolean equals(Object other) {
        // All ClearCommand instances are functionally identical
        return other instanceof ClearCommand;
    }

    @Override
    public int hashCode() {
        return ClearCommand.class.hashCode();
    }
}
