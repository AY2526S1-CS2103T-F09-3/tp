package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.SEARCH_PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.SEARCH_PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.SEARCH_PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.SEARCH_PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.SEARCH_PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.SEARCH_PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.recruit.FieldContainsKeywordsPredicate;
import seedu.address.model.recruit.NestedOrPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_withoutFlag_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new NestedOrPredicate(
                        new FieldContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"), SEARCH_PREFIX_NAME)));
        assertParseSuccess(parser, "Alice|Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "\n Alice| \n \t Bob  \t", expectedFindCommand);
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new NestedOrPredicate(
                        new FieldContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"), SEARCH_PREFIX_NAME)));
        assertParseSuccess(parser, " -n Alice|Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "\n -n Alice| \n \t Bob  \t", expectedFindCommand);
    }

    @Test
    public void parse_addressArgs_returnsFindCommand() {
        FindCommand expectedFindCommand =
                new FindCommand(new NestedOrPredicate(
                        new FieldContainsKeywordsPredicate(Arrays.asList("Jurong", "Clementi"),
                                SEARCH_PREFIX_ADDRESS)));
        assertParseSuccess(parser, " -a Jurong|Clementi", expectedFindCommand);
        assertParseSuccess(parser, "\n -a Jurong| \n \t Clementi  \t", expectedFindCommand);
    }

    @Test
    public void parse_idArgs_returnsFindCommand() {
        FindCommand expectedFindCommand =
                new FindCommand(new NestedOrPredicate(
                        new FieldContainsKeywordsPredicate(Arrays.asList(
                                "eac9b117-2ded-42c3-9264-ccf3dfaaa950",
                                "eac9b117-2ded-42c3-9264-ccf3dfaaa951"), SEARCH_PREFIX_ID)));
        assertParseSuccess(parser,
                " -id eac9b117-2ded-42c3-9264-ccf3dfaaa950|eac9b117-2ded-42c3-9264-ccf3dfaaa951",
                expectedFindCommand);
        assertParseSuccess(parser,
                "\n -id eac9b117-2ded-42c3-9264-ccf3dfaaa950| \n "
                            + "\t eac9b117-2ded-42c3-9264-ccf3dfaaa951  \t",
                expectedFindCommand);
    }

    @Test
    public void parse_phoneArgs_returnsFindCommand() {
        FindCommand expectedFindCommand =
                new FindCommand(new NestedOrPredicate(
                        new FieldContainsKeywordsPredicate(Arrays.asList("94351253", "98765432"),
                                SEARCH_PREFIX_PHONE)));
        assertParseSuccess(parser, " -p 94351253|98765432", expectedFindCommand);
        assertParseSuccess(parser, "\n -p 94351253| \n \t 98765432  \t", expectedFindCommand);
    }

    @Test
    public void parse_emailArgs_returnsFindCommand() {
        FindCommand expectedFindCommand =
                new FindCommand(new NestedOrPredicate(
                        new FieldContainsKeywordsPredicate(Arrays.asList("alice@example.com", "johnd@example.com"),
                                SEARCH_PREFIX_EMAIL)));
        assertParseSuccess(parser, " -e alice@example.com|johnd@example.com", expectedFindCommand);
        assertParseSuccess(parser, "\n -e alice@example.com| \n \t johnd@example.com  \t",
                expectedFindCommand);
    }

    @Test
    public void parse_tagArgs_returnsFindCommand() {
        FindCommand expectedFindCommand =
                new FindCommand(new NestedOrPredicate(
                        new FieldContainsKeywordsPredicate(Arrays.asList("friends", "owesMoney"), SEARCH_PREFIX_TAG)));
        assertParseSuccess(parser, " -t friends|owesMoney", expectedFindCommand);
        assertParseSuccess(parser, "\n -t friends| \n \t owesMoney  \t", expectedFindCommand);
    }
}
