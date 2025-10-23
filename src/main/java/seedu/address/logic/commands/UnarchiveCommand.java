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
 * Archives a recruit entry
 */
public class UnarchiveCommand extends Command {
    public static final String COMMAND_WORD = "unarchive";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Unarchives the details of an "
            + "unarchived recruit identified by the index number used in the displayed recruit list. "
            + "Parameters: INDEX (must be a positive integer) "
            + "Example: " + COMMAND_WORD + " 1 ";
    public static final String RECRUIT_ALREADY_UNARCHIVED = "This recruit is not archived.";
    public static final String MESSAGE_ARCHIVE_RECRUIT_SUCCESS = "Unarchived Recruit:\n%1$s";

    private final Index index;

    /**
     * @param index of the person in the filtered recruit list to edit
     */
    public UnarchiveCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Recruit> lastShownList = model.getFilteredRecruitList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_RECRUIT_DISPLAYED_INDEX);
        }

        Recruit recruitToUnarchive = lastShownList.get(index.getZeroBased());
        assert recruitToUnarchive != null;
        if (!recruitToUnarchive.isArchived()) {
            throw new CommandException(RECRUIT_ALREADY_UNARCHIVED);
        }

        Recruit recruit = unarchiveRecruit(recruitToUnarchive);

        model.setRecruit(recruitToUnarchive, recruit);
        model.refreshFilteredRecruitList();

        return new CommandResult(String.format(
                MESSAGE_ARCHIVE_RECRUIT_SUCCESS, Messages.format(recruitToUnarchive)));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Recruit unarchiveRecruit(Recruit recruitToEdit) {
        assert recruitToEdit != null;
        return new Recruit(recruitToEdit.getID(), recruitToEdit.getNames(), recruitToEdit.getPhones(),
                recruitToEdit.getEmails(), recruitToEdit.getAddresses(), recruitToEdit.getTags(), false);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof UnarchiveCommand otherCommand)) {
            return false;
        }

        return index.equals(otherCommand.index);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .toString();
    }
}
