package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "exit";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting Tutor Track as requested ...";
    public static final String MESSAGE_NO_EXTRA_PARAMS =
            "No extra parameters allowed! Use '%1$s' only.";
    private final String args;

    public ExitCommand(String args) {
        this.args = args;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        String trimmedArgs = args.trim();
        if (!trimmedArgs.isEmpty()) {
            throw new CommandException(String.format(MESSAGE_NO_EXTRA_PARAMS, COMMAND_WORD));
        }
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true);
    }

    @Override
    public String toString() {
        return ExitCommand.class.getCanonicalName();
    }

}
