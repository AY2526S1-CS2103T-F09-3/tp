package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.recruit.Recruit;

/**
 * Views a person identified using it's displayed index from the address book.
 */
public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displays a detailed view of the the recruit identified by "
            + "the index number used in the displayed recruit list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_VIEW_RECRUIT_SUCCESS = "Viewing Recruit:\n%1$s";

    private final Index targetIndex;

    public ViewCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Recruit> lastShownList = model.getFilteredRecruitList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_RECRUIT_DISPLAYED_INDEX);
        }

        Recruit recruitToView = lastShownList.get(targetIndex.getZeroBased());
        return new CommandResult(String.format(MESSAGE_VIEW_RECRUIT_SUCCESS, Messages.format(recruitToView)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ViewCommand)) {
            return false;
        }

        ViewCommand otherViewCommand = (ViewCommand) other;
        return targetIndex.equals(otherViewCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
