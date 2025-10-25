package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Clears the CommandResult box.
 */
public class DismissCommand extends Command {

    public static final String COMMAND_WORD = "dismiss";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult("");
    }

}
