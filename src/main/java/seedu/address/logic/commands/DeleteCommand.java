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
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";
    public static final String OPERATION_DESCRIPTOR = "deletion of recruit:\n%s";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the recruit identified by recruit's ID.\n"
            + "Parameters: ID (must be a UUID string)\n"
            + "Example: " + COMMAND_WORD + " eac9b117-2ded-42c3-9264-ccf3dfaaa950";

    public static final String MESSAGE_DELETE_RECRUIT_SUCCESS = "Deleted Recruit:\n%1$s";

    private final UUID targetID;

    public DeleteCommand(UUID id) {
        this.targetID = id;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Recruit> lastShownList = model.getFilteredRecruitList();
        Optional<Recruit> recruitToDelete = lastShownList.stream()
                .filter(recruit -> recruit.getID().equals(this.targetID))
                .findFirst();


        if (recruitToDelete.isEmpty()) {
            throw new CommandException(Messages.MESSAGE_INVALID_RECRUIT_ID);
        }
        Recruit targetRecruit = recruitToDelete.get();

        model.deleteRecruit(targetRecruit);
        model.commitAddressBook(String.format(OPERATION_DESCRIPTOR, Messages.format(targetRecruit)));
        return new CommandResult(String.format(MESSAGE_DELETE_RECRUIT_SUCCESS, Messages.format(recruitToDelete.get())));
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
