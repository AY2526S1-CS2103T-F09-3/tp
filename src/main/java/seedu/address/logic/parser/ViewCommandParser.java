package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.UUID;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ViewCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ViewCommand object
 */
public class ViewCommandParser implements Parser<ViewCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewCommand
     * and returns a ViewCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmedArgs = args.trim();

        // Try parsing as UUID first
        try {
            UUID id = ParserUtil.parseID(trimmedArgs);
            return new ViewCommand(id);
        } catch (ParseException e) {
            // Not a valid UUID, try parsing as index
            try {
                Index index = ParserUtil.parseIndex(trimmedArgs);
                return new ViewCommand(index);
            } catch (ParseException e2) {
                // Both parsing attempts failed
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE), e2);
            }
        }
    }
}
