package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    private final UUID targetID;

    public ViewCommand(UUID targetID) {
        this.targetID = targetID;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Recruit> lastShownList = model.getAddressBook().getRecruitList();
        Optional<Recruit> recruitToView = lastShownList.stream()
                .filter(recruit -> recruit.getID().equals(this.targetID))
                .findFirst();

        if (recruitToView.isEmpty()) {
            throw new CommandException(Messages.MESSAGE_INVALID_RECRUIT_ID);
        }
        return new CommandResult(String.format(MESSAGE_VIEW_RECRUIT_SUCCESS, Messages.format(recruitToView.get())));
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
        return targetID.equals(otherViewCommand.targetID);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetID", targetID)
                .toString();
    }
}
