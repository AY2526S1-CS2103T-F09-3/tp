package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.recruit.Recruit;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";
    public static final String OPERATION_DESCRIPTOR = "deletion of recruit:\n%s";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the recruit identified by recruit's INDEX/UUID.\n"
            + "Parameters: INDEX/UUID\n"
            + "Example: " + COMMAND_WORD + " 2\n"
            + "Example: " + COMMAND_WORD + " eac9b117-2ded-42c3-9264-ccf3dfaaa950";

    public static final String MESSAGE_DELETE_RECRUIT_SUCCESS = "Deleted Recruit:\n%1$s";

    private final UUID targetID;
    private final Index targetIndex;
    /**
     * Creates a DeleteCommand to delete the specified recruit by {@code id}
     */
    public DeleteCommand(UUID id) {
        this.targetID = id;
        this.targetIndex = null;
    }
    /**
     * Creates a DeleteCommand to delete the specified recruit by {@code index}
     */
    public DeleteCommand(Index index) {
        this.targetIndex = index;
        this.targetID = null;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Recruit> lastShownList = model.getFilteredRecruitList();
        Recruit targetRecruit;
        if (targetID != null) {
            Optional<Recruit> recruitToDelete = lastShownList.stream()
                    .filter(recruit -> recruit.getID().equals(targetID))
                    .findFirst();
            if (recruitToDelete.isEmpty()) {
                throw new CommandException(Messages.MESSAGE_INVALID_RECRUIT_ID);
            }
            targetRecruit = recruitToDelete.get();
        } else if (targetIndex != null) {
            if (targetIndex.getZeroBased() < 0 || targetIndex.getOneBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_RECRUIT_DISPLAYED_INDEX);
            }
            targetRecruit = lastShownList.get(targetIndex.getZeroBased());
        } else {
            throw new CommandException(Messages.MESSAGE_NO_ID_OR_INDEX);
        }
        model.deleteRecruit(targetRecruit);
        model.commitAddressBook(String.format(OPERATION_DESCRIPTOR, Messages.format(targetRecruit)));
        return new CommandResult(String.format(MESSAGE_DELETE_RECRUIT_SUCCESS, Messages.format(targetRecruit)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteCommand)) {
            return false;
        }

        DeleteCommand otherDeleteCommand = (DeleteCommand) other;
        return targetID.equals(otherDeleteCommand.targetID);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetID", targetID)
                .toString();
    }
}
