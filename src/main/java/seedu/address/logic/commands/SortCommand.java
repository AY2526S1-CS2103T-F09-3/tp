package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

/**
 * Sorts the recruits in the address book by name in alphabetical order.
 * Next iteration: implement logic to sort by parameters other than name, as well as in ascending / descending order.
 */
public class SortCommand extends Command {
    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts all recruits in the address book.\n";

    public static final String MESSAGE_SUCCESS = "Recruits sorted by name from A to Z.";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.sortRecruits();
        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }
}

