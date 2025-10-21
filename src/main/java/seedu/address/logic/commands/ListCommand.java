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

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all recruits "
            + "When used without any flags, returns all unarchived recruits"
            + "Use -all flag to view all recruits (archived & unarchived) \n"
            + "Use -archive flag to view all archived recruits"
            + "Example: " + COMMAND_WORD + " -all \n"
            + "Example: " + COMMAND_WORD + " -archive \n";

    public final Predicate<Recruit> predicate;
    public final String operation;

    /**
     * Creates a List Command Object which lists all recruits
     * @param predicate - predicate that filters out irrelevant recruits
     * @param operation - type of list operation performed
     */
    public ListCommand(Predicate<Recruit> predicate, String operation) {
        this.predicate = predicate;
        this.operation = operation;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredRecruitList(predicate);
        return new CommandResult(operation);
    }
}
