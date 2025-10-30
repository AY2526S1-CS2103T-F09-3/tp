package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.parser.ListCommandParser;
import seedu.address.model.Model;
import seedu.address.model.recruit.Recruit;

/**
 * Lists all recruits in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all recruits\n"
            + "When used without any flags, returns all unarchived recruits\n"
            + "Use -all flag to view all recruits (archived & unarchived)\n"
            + "Use -archived flag to view all archived recruits\n"
            + "Example: " + COMMAND_WORD + " -all \n"
            + "Example: " + COMMAND_WORD + " -archived \n";

    public final Predicate<Recruit> predicate;
    public final ListCommandParser.ListOperation operation;

    /**
     * Creates a List Command Object which lists all recruits
     * @param predicate - predicate that filters out irrelevant recruits
     * @param operation - type of list operation performed
     */
    public ListCommand(Predicate<Recruit> predicate, ListCommandParser.ListOperation operation) {
        this.predicate = predicate;
        this.operation = operation;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredRecruitList(predicate);
        switch (operation) {
        case NORMAL_LIST_OP:
            return new CommandResult("Listed all recruits!");
        case ARCHIVE_LIST_OP:
            return new CommandResult("Listed all archived recruits!");
        case FULL_LIST_OP:
            return new CommandResult("Listed all recruits (unarchived & archived)!");
        default:
            return new CommandResult("");
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ListCommand otherCommand)) {
            return false;
        }

        return predicate.equals(otherCommand.predicate) && operation.equals(otherCommand.operation);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .add("operation", operation)
                .toString();
    }
}
