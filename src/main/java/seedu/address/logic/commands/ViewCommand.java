package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.UUID;

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
            + "Parameters: INDEX/UUID\n"
            + "Example: " + COMMAND_WORD + " 1\n"
            + "Example: " + COMMAND_WORD + " eac9b117-2ded-42c3-9264-ccf3dfaaa950";

    public static final String MESSAGE_VIEW_RECRUIT_SUCCESS = "Viewing Recruit:\n%1$s";

    private final UUID targetID;
    private Index targetIndex;
    /**
     * Creates a ViewCommand to view the specified recruit by {@code id}
     */
    public ViewCommand(UUID targetID) {
        this.targetID = targetID;
        this.targetIndex = null;
    }
    /**
     * Creates a ViewCommand to view the specified recruit by {@code index}
     */
    public ViewCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
        this.targetID = null;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Recruit recruitToView;

        if (targetID != null) {
            // View by ID
            List<Recruit> allRecruits = model.getAddressBook().getRecruitList();
            recruitToView = allRecruits.stream()
                    .filter(recruit -> recruit.getID().equals(targetID))
                    .findFirst()
                    .orElseThrow(() -> new CommandException(Messages.MESSAGE_INVALID_RECRUIT_ID));
        } else if (targetIndex != null) {
            // View by index
            List<Recruit> lastShownList = model.getFilteredRecruitList();
            if (targetIndex.getZeroBased() < 0 || targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_RECRUIT_DISPLAYED_INDEX);
            }
            recruitToView = lastShownList.get(targetIndex.getZeroBased());
        } else {
            // Neither ID nor index provided
            throw new CommandException(Messages.MESSAGE_NO_ID_OR_INDEX);
        }

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
        return targetID.equals(otherViewCommand.targetID);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetID", targetID)
                .toString();
    }
}
