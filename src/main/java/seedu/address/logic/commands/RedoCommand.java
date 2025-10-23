package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Undoes the commands that result in changes in the model starting from the most recently executed commands.
 */
public class RedoCommand extends Command {
    public static final String COMMAND_WORD = "redo";
    public static final String MESSAGE_SUCCESS = "Redid %s.";
    public static final String MESSAGE_NO_OPERATION_TO_REDO = "There is no remaining operation that can be redone.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.canRedoAddressBook()) {
            throw new CommandException(MESSAGE_NO_OPERATION_TO_REDO);
        }

        String redoneOperation = model.redoAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, redoneOperation));
    }
}
