package seedu.address.logic.parser;


import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.recruit.Address;
import seedu.address.model.recruit.Email;
import seedu.address.model.recruit.Name;
import seedu.address.model.recruit.Phone;
import seedu.address.model.recruit.Recruit;
import seedu.address.model.tag.Tag;

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
                ArgumentTokenizer.tokenize(args, PREFIX_ID, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                        PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ADDRESS, PREFIX_PHONE, PREFIX_EMAIL)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_ID);

        List<Name> names = ParserUtil.parseAllValues(argMultimap.getAllValues(PREFIX_NAME), ParserUtil::parseName);
        List<Phone> phones = ParserUtil.parseAllValues(argMultimap.getAllValues(PREFIX_PHONE), ParserUtil::parsePhone);
        List<Email> emails = ParserUtil.parseAllValues(argMultimap.getAllValues(PREFIX_EMAIL), ParserUtil::parseEmail);
        List<Address> addresses = ParserUtil.parseAllValues(
                argMultimap.getAllValues(PREFIX_ADDRESS), ParserUtil::parseAddress);
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Recruit recruit;

        if (argMultimap.getValue(PREFIX_ID).isPresent()) {
            UUID id = ParserUtil.parseID(argMultimap.getValue(PREFIX_ID).get());
            recruit = new Recruit(id, names, phones, emails, addresses, tagList, false);
        } else {
            recruit = new Recruit(names, phones, emails, addresses, tagList, false);
        }
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
