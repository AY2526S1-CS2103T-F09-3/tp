package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LIST_ALL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LIST_ARCHIVE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_RECRUITS;
import static seedu.address.model.Model.PREDICATE_SHOW_ARCHIVED_RECRUITS;
import static seedu.address.model.Model.PREDICATE_SHOW_UNARCHVIED_RECRUITS;

import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class ListCommandParser implements Parser<ListCommand> {
    public enum ListOperation {
        NORMAL_LIST_OP,
        ARCHIVE_LIST_OP,
        FULL_LIST_OP
    }
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_LIST_ALL, PREFIX_LIST_ARCHIVE);
        boolean isListAllPrefixPresent = argMultimap.getValue(PREFIX_LIST_ALL).isPresent();
        boolean isListArchivePrefixPresent = argMultimap.getValue(PREFIX_LIST_ARCHIVE).isPresent();
        // Both flags are provided (invalid input)
        if (isListAllPrefixPresent && isListArchivePrefixPresent) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        } else if (isListAllPrefixPresent) {
            return new ListCommand(PREDICATE_SHOW_ALL_RECRUITS, ListOperation.FULL_LIST_OP);
        } else if (isListArchivePrefixPresent) {
            return new ListCommand(PREDICATE_SHOW_ARCHIVED_RECRUITS, ListOperation.ARCHIVE_LIST_OP);
        } else {
            return new ListCommand(PREDICATE_SHOW_UNARCHVIED_RECRUITS, ListOperation.NORMAL_LIST_OP);
        }
    }
}
