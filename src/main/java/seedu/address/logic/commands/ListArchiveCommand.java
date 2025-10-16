package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

/**
 *  Show the list of archived persons.
 */
public class ListArchiveCommand extends Command {

    public static final String COMMAND_WORD = "listarchive";

    public static final String MESSAGE_SUCCESS = "Listed all archived persons";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        assert model != null : "Model should not be null";

        model.updateFilteredArchivedPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);

        CommandResult result = new CommandResult(MESSAGE_SUCCESS, false, false, true);
        assert result != null : "CommandResult should not be null";
        assert result.isShowArchived() : "CommandResult should indicate showing archived persons";

        return result;
    }
}
