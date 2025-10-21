package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Unarchives a person identified using its displayed index from the archived list.
 */
public class UnarchiveCommand extends Command {

    public static final String COMMAND_WORD = "unarchive";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Unarchives the person identified by the index number used in the displayed archived person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_UNARCHIVE_PERSON_SUCCESS = "Unarchived Person: %1$s";

    private static final Logger logger = LogsCenter.getLogger(UnarchiveCommand.class);

    private final Index targetIndex;

    /**
     * Creates an UnarchiveCommand to unarchive the person at the specified {@code Index}
     */
    public UnarchiveCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        assert targetIndex != null : "Target index should not be null";
        assert targetIndex.getZeroBased() >= 0 : "Index should be non-negative";

        this.targetIndex = targetIndex;
        logger.fine("UnarchiveCommand created with index: " + targetIndex.getOneBased());
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        assert model != null : "Model should not be null";

        logger.info("Executing UnarchiveCommand for index: " + targetIndex.getOneBased());

        List<Person> lastShownArchivedList = model.getFilteredArchivedPersonList();
        assert lastShownArchivedList != null : "Archived person list should not be null";

        if (targetIndex.getZeroBased() >= lastShownArchivedList.size()) {
            logger.warning("Invalid index: " + targetIndex.getOneBased()
                    + ", list size: " + lastShownArchivedList.size());
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToUnarchive = lastShownArchivedList.get(targetIndex.getZeroBased());
        assert personToUnarchive != null : "Person to unarchive should not be null";

        logger.info("Unarchiving person: " + personToUnarchive.getName());

        model.unarchivePerson(personToUnarchive);

        CommandResult result = new CommandResult(
                String.format(MESSAGE_UNARCHIVE_PERSON_SUCCESS, Messages.format(personToUnarchive)));
        assert result != null : "CommandResult should not be null";
        assert result.getFeedbackToUser() != null : "Feedback message should not be null";

        logger.info("Successfully unarchived person: " + personToUnarchive.getName());

        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UnarchiveCommand)) {
            return false;
        }

        UnarchiveCommand otherUnarchiveCommand = (UnarchiveCommand) other;
        assert otherUnarchiveCommand != null : "Other command should not be null";
        return targetIndex.equals(otherUnarchiveCommand.targetIndex);
    }

    @Override
    public int hashCode() {
        assert targetIndex != null : "Target index should not be null";
        return targetIndex.hashCode();
    }

    @Override
    public String toString() {
        assert targetIndex != null : "Target index should not be null";
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
