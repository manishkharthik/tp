package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Clears the address book.
 */
public class ClearArchiveCommand extends Command {

    public static final String COMMAND_WORD = "cleararchive";
    public static final String MESSAGE_SUCCESS = "Archived students have been cleared!";
    public static final String MESSAGE_NO_EXTRA_PARAMS = "No extra parameters allowed! Use '%1$s' only.";

    private final String args;

    public ClearArchiveCommand(String args) {
        this.args = args;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        String trimmedArgs = args.trim();
        if (!trimmedArgs.isEmpty()) {
            throw new CommandException(String.format(MESSAGE_NO_EXTRA_PARAMS, COMMAND_WORD));
        }

        model.clearArchivedStudents();
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
