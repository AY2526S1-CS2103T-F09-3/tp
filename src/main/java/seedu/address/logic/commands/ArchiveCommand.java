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
public class ArchiveCommand extends Command {
    public static final String COMMAND_WORD = "archive";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Archives the details of an "
            + "unarchived recruit identified by the index number used in the displayed recruit list. \n"
            + "Parameters: INDEX (must be a positive integer) \n"
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_DUPLICATE_RECRUIT = "This recruit is already archived in the address book.";
    public static final String MESSAGE_ARCHIVE_RECRUIT_SUCCESS = "Archived Recruit:\n%1$s";

    private final Index index;

    /**
     * @param index of the person in the filtered recruit list to edit
     */
    public ArchiveCommand(Index index) {
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

        Recruit recruitToArchive = lastShownList.get(index.getZeroBased());
        assert recruitToArchive != null;
        if (recruitToArchive.isArchived()) {
            throw new CommandException(MESSAGE_DUPLICATE_RECRUIT);
        }
        Recruit archivedRecruit = archiveRecruit(recruitToArchive);

        if (!recruitToArchive.isSameRecruit(archivedRecruit) && model.hasRecruit(archivedRecruit)) {
            throw new CommandException(MESSAGE_DUPLICATE_RECRUIT);
        }

        model.setRecruit(recruitToArchive, archivedRecruit);
        model.refreshFilteredRecruitList();

        return new CommandResult(String.format(
                MESSAGE_ARCHIVE_RECRUIT_SUCCESS, Messages.format(recruitToArchive)));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Recruit archiveRecruit(Recruit recruitToEdit) {
        assert recruitToEdit != null;
        return new Recruit(recruitToEdit.getID(), recruitToEdit.getNames(), recruitToEdit.getPhones(),
                recruitToEdit.getEmails(), recruitToEdit.getAddresses(), recruitToEdit.getTags(), true);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ArchiveCommand otherCommand)) {
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
