package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import seedu.address.model.Model;
import seedu.address.model.recruit.Recruit;

/**
 * Lists all recruits in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all recruits";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all recruits "
            + "When used without any flags, returns all unarchived recruits"
            + "Use -all flag to view all recruits (archived & unarchived) \n"
            + "Use -archive flag to view all archived recruits"
            + "Example: " + COMMAND_WORD + " -all \n"
            + "Example: " + COMMAND_WORD + " -archive \n";

    public final Predicate<Recruit> predicate;

    public ListCommand(Predicate<Recruit> predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredRecruitList(predicate);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
