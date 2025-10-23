package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Undoes the commands that result in changes in the model starting from the most recently executed commands.
 */
public class UndoCommand extends Command {
    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_SUCCESS = "Undid %s.";
    public static final String MESSAGE_NO_OPERATION_TO_UNDO = "There is no remaining operation that can be undone.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.canUndoAddressBook()) {
            throw new CommandException(MESSAGE_NO_OPERATION_TO_UNDO);
        }

        String undoneOperation = model.undoAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, undoneOperation));
    }
}
