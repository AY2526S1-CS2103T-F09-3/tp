package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.recruit.NestedOrPredicate;
/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all recruits who contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Searches for name by default unless keyword(s) are provided using the following flags"
            + "Parameters: FLAG ( -id for ID, -n for name, -a for address, -p for phone, \n"
            + "-e for email -a for address & -t for tag ) \n"
            + "KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " -n alice|bob|charlie -a Clementi -p 98765432";

    private final NestedOrPredicate predicate;

    public FindCommand(NestedOrPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredRecruitList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_RECRUITS_LISTED_OVERVIEW, model.getFilteredRecruitList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindCommand)) {
            return false;
        }

        FindCommand otherFindCommand = (FindCommand) other;
        return predicate.equals(otherFindCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }

}
