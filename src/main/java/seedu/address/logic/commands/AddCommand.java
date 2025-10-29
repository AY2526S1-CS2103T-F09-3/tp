package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.recruit.Recruit;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";
    public static final String OPERATION_DESCRIPTOR = "creation of new recruit:\n%s";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a recruit to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME...\n"
            + "[" + PREFIX_PHONE + "PHONE]...\n"
            + "[" + PREFIX_EMAIL + "EMAIL]...\n"
            + "[" + PREFIX_ADDRESS + "ADDRESS]...\n"
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_TAG + "friends "
            + PREFIX_TAG + "owesMoney";

    public static final String MESSAGE_SUCCESS = "New recruit added:\n%1$s";
    public static final String MESSAGE_DUPLICATE_RECRUIT = "This recruit already exists in the address book";
    public static final String MESSAGE_NO_NAME = "At least one name is required to add a recruit.\n"
            + MESSAGE_USAGE;

    private final Recruit toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddCommand(Recruit recruit) {
        requireNonNull(recruit);
        toAdd = recruit;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasRecruit(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_RECRUIT);
        }

        model.addRecruit(toAdd);
        model.commitAddressBook(String.format(OPERATION_DESCRIPTOR, Messages.format(toAdd)));
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddCommand)) {
            return false;
        }

        AddCommand otherAddCommand = (AddCommand) other;
        return toAdd.isSameRecruit(otherAddCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
