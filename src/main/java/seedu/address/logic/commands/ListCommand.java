package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.model.Model;

/**
 * Lists all students in the address book to the tutor.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all students";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        assert model != null : "Model should not be null";

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        CommandResult result = new CommandResult(MESSAGE_SUCCESS);
        assert result != null : "CommandResult should not be null";

        return result;
    }
}
