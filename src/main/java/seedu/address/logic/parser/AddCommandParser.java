package seedu.address.logic.parser;


import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.recruit.Recruit;
import seedu.address.model.recruit.RecruitBuilder;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                       PREFIX_DESCRIPTION, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ADDRESS, PREFIX_PHONE, PREFIX_EMAIL)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        Recruit recruit = new RecruitBuilder()
                .withNames(ParserUtil.extractValuesFromMultimap(PREFIX_NAME, argMultimap, ParserUtil::parseName))
                .withPhones(ParserUtil.extractValuesFromMultimap(PREFIX_PHONE, argMultimap, ParserUtil::parsePhone))
                .withEmails(ParserUtil.extractValuesFromMultimap(PREFIX_EMAIL, argMultimap, ParserUtil::parseEmail))
                .withAddresses(ParserUtil.extractValuesFromMultimap(PREFIX_ADDRESS, argMultimap,
                        ParserUtil::parseAddress))
                .withDescription(ParserUtil.extractValueFromMultimap(PREFIX_DESCRIPTION, argMultimap,
                        ParserUtil::parseDescription))
                .withTags(ParserUtil.extractValuesFromMultimap(PREFIX_TAG, argMultimap, ParserUtil::parseTag))
                .build();

        return new AddCommand(recruit);
    }


    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
