package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

/**
 *  Show the list of archived persons.
 */
public class ListArchiveCommand extends Command {

    public static final String COMMAND_WORD = "listarchive";

    public static final String MESSAGE_SUCCESS = "Listed all archived persons";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Lists all archived persons.\n"
        + "This command does not take any parameters.\n"
        + "Example: " + COMMAND_WORD;

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        assert model != null : "Model should not be null";

        model.updateFilteredArchivedPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        model.setViewingArchived(true);

        CommandResult result = new CommandResult(MESSAGE_SUCCESS, false, false, true);
        assert result != null : "CommandResult should not be null";
        assert result.isShowArchived() : "CommandResult should indicate showing archived persons";

        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ListArchiveCommand)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return COMMAND_WORD.hashCode();
    }
}
