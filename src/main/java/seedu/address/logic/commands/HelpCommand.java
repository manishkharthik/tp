package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Formats full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";
    public static final String MESSAGE_NO_EXTRA_PARAMS =
            "No extra parameters allowed! Use '%1$s' only.";
    private final String args;

    public HelpCommand(String args) {
        this.args = args;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        String trimmedArgs = args.trim();
        if (!trimmedArgs.isEmpty()) {
            throw new CommandException(String.format(MESSAGE_NO_EXTRA_PARAMS, COMMAND_WORD));
        }
        return new CommandResult(SHOWING_HELP_MESSAGE, true, false);
    }

    @Override
    public boolean equals(Object other) {
        return other == this || (other instanceof HelpCommand);
    }

    @Override
    public String toString() {
        return HelpCommand.class.getCanonicalName();
    }
}
