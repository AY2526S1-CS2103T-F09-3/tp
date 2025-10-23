package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ListCommandParser.ListOperation;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_RECRUITS;
import static seedu.address.model.Model.PREDICATE_SHOW_ARCHIVED_RECRUITS;
import static seedu.address.model.Model.PREDICATE_SHOW_UNARCHVIED_RECRUITS;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ListCommand;

public class ListCommandParserTest {

    private ListCommandParser parser = new ListCommandParser();

    @Test
    public void parse_noArgs_returnsListCommand() {
        ListCommand expectedListCommand = new ListCommand(PREDICATE_SHOW_UNARCHVIED_RECRUITS, ListOperation.NORMAL_LIST_OP);
        assertParseSuccess(parser, "list", expectedListCommand);
    }

    @Test
    public void parse_allArg_returnsListCommand() {
        ListCommand expectedListCommand = new ListCommand(PREDICATE_SHOW_ALL_RECRUITS, ListOperation.FULL_LIST_OP);
        assertParseSuccess(parser, "list -all", expectedListCommand);
        assertParseSuccess(parser, "list \n -all \t", expectedListCommand);
    }

    @Test
    public void parse_archivedArg_returnsListCommand() {
        ListCommand expectedListCommand = new ListCommand(PREDICATE_SHOW_ARCHIVED_RECRUITS, ListOperation.ARCHIVE_LIST_OP);
        assertParseSuccess(parser, "list -archived", expectedListCommand);
        assertParseSuccess(parser, "list \n -archived \t", expectedListCommand);
    }

    @Test
    public void parse_allAndArchivedArgs_throwsParseException() {
        assertParseFailure(parser, "list -all -archived",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "list -archived -all",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
    }
}
