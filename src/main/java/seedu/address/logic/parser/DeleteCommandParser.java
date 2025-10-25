package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.UUID;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        try {
            UUID id = ParserUtil.parseID(trimmedArgs);
            return new DeleteCommand(id);
        } catch (ParseException e) {
            // Not a valid UUID, try parsing as index
            try {
                Index index = ParserUtil.parseIndex(trimmedArgs);
                return new DeleteCommand(index);
            } catch (ParseException e2) {
                // Both parsing attempts failed
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE), e2);
            }
        }
    }

}
