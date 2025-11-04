package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.SEARCH_PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.SEARCH_PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.SEARCH_PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.SEARCH_PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.SEARCH_PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.SEARCH_PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.SEARCH_PREFIX_TAG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.recruit.FieldContainsKeywordsPredicate;
import seedu.address.model.recruit.NestedOrPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, SEARCH_PREFIX_ID, SEARCH_PREFIX_NAME, SEARCH_PREFIX_PHONE,
                        SEARCH_PREFIX_EMAIL, SEARCH_PREFIX_ADDRESS, SEARCH_PREFIX_DESCRIPTION, SEARCH_PREFIX_TAG);

        boolean isInvalidCommand = (!arePrefixesPresent(argMultimap, SEARCH_PREFIX_ID)
                && !arePrefixesPresent(argMultimap, SEARCH_PREFIX_NAME)
                && !arePrefixesPresent(argMultimap, SEARCH_PREFIX_PHONE)
                && !arePrefixesPresent(argMultimap, SEARCH_PREFIX_EMAIL)
                && !arePrefixesPresent(argMultimap, SEARCH_PREFIX_ADDRESS)
                && !arePrefixesPresent(argMultimap, SEARCH_PREFIX_TAG)
                && argMultimap.getPreamble().isEmpty());

        if (isInvalidCommand) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        String[] idKeywords = getKeywords(argMultimap, SEARCH_PREFIX_ID);
        // If both flag and preamble provided, the keyword after flag will override the preamble
        String preamble = argMultimap.getPreamble().trim();
        String[] nameKeywords = getKeywords(argMultimap, SEARCH_PREFIX_NAME).length > 0
                ? getKeywords(argMultimap, SEARCH_PREFIX_NAME)
                : preamble.isEmpty() ? new String[]{} : new String[]{argMultimap.getPreamble().trim()};
        String[] phoneKeywords = getKeywords(argMultimap, SEARCH_PREFIX_PHONE);
        String[] emailKeywords = getKeywords(argMultimap, SEARCH_PREFIX_EMAIL);
        String[] addressKeywords = getKeywords(argMultimap, SEARCH_PREFIX_ADDRESS);
        String[] tagKeywords = getKeywords(argMultimap, SEARCH_PREFIX_TAG);

        List<FieldContainsKeywordsPredicate> params = new ArrayList<>();

        if (idKeywords.length > 0) {
            params.add(new FieldContainsKeywordsPredicate(
                    Arrays.asList(idKeywords), SEARCH_PREFIX_ID));
        }

        if (nameKeywords.length > 0) {
            params.add(new FieldContainsKeywordsPredicate(
                    Arrays.asList(nameKeywords), SEARCH_PREFIX_NAME));
        }

        if (phoneKeywords.length > 0) {
            params.add(new FieldContainsKeywordsPredicate(
                    Arrays.asList(phoneKeywords), SEARCH_PREFIX_PHONE));
        }

        if (emailKeywords.length > 0) {
            params.add(new FieldContainsKeywordsPredicate(
                    Arrays.asList(emailKeywords), SEARCH_PREFIX_EMAIL));
        }

        if (addressKeywords.length > 0) {
            params.add(new FieldContainsKeywordsPredicate(
                    Arrays.asList(addressKeywords), SEARCH_PREFIX_ADDRESS));
        }

        if (tagKeywords.length > 0) {
            params.add(new FieldContainsKeywordsPredicate(
                    Arrays.asList(tagKeywords), SEARCH_PREFIX_TAG));
        }

        return new FindCommand(
                new NestedOrPredicate(params.toArray(new FieldContainsKeywordsPredicate[0])));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Returns string array of keywords given multimap and prefix
     */
    private static String[] getKeywords(ArgumentMultimap argumentMultimap, Prefix prefix) {
        if (argumentMultimap.getValue(prefix).isPresent()) {
            return argumentMultimap.getAllValues(prefix).stream()
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .toArray(String[]::new);
        } else {
            return new String[0];
        }
    }
}
