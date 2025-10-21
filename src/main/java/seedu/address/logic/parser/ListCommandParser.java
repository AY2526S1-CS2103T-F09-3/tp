package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.LIST_ALL;
import static seedu.address.logic.parser.CliSyntax.LIST_ARCHIVE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_RECRUITS;
import static seedu.address.model.Model.PREDICATE_SHOW_ARCHIVED_RECRUITS;
import static seedu.address.model.Model.PREDICATE_SHOW_UNARCHVIED_RECRUITS;

import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class ListCommandParser implements Parser<ListCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, LIST_ALL, LIST_ARCHIVE);
        // Both flags are provided (invalid input)
        if (argMultimap.getValue(LIST_ALL).isPresent() && argMultimap.getValue(LIST_ARCHIVE).isPresent()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        } else if (argMultimap.getValue(LIST_ALL).isPresent()) {
            return new ListCommand(PREDICATE_SHOW_ALL_RECRUITS);
        } else if (argMultimap.getValue(LIST_ARCHIVE).isPresent()) {
            return new ListCommand(PREDICATE_SHOW_ARCHIVED_RECRUITS);
        } else {
            return new ListCommand(PREDICATE_SHOW_UNARCHVIED_RECRUITS);
        }
    }
}
