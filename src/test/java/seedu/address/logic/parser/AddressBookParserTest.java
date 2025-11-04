package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.CliSyntax.SEARCH_PREFIX_NAME;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_RECRUIT;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ArchiveCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DismissCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditRecruitDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.UnarchiveCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.ViewCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.recruit.FieldContainsKeywordsPredicate;
import seedu.address.model.recruit.NestedOrPredicate;
import seedu.address.model.recruit.Recruit;
import seedu.address.testutil.EditRecruitDescriptorBuilder;
import seedu.address.testutil.RecruitUtil;
import seedu.address.testutil.SimpleRecruitBuilder;
import seedu.address.testutil.TypicalIDs;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Recruit recruit = new SimpleRecruitBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(RecruitUtil.getAddCommand(recruit));
        assertEquals(new AddCommand(recruit), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " -confirm") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + TypicalIDs.ID_FIRST_RECRUIT);
        assertEquals(new DeleteCommand(TypicalIDs.ID_FIRST_RECRUIT), command);
    }

    @Test
    public void parseCommand_archive() throws Exception {
        ArchiveCommand command = (ArchiveCommand) parser.parseCommand(
                ArchiveCommand.COMMAND_WORD + " " + INDEX_FIRST_RECRUIT.getOneBased());
        assertEquals(new ArchiveCommand(INDEX_FIRST_RECRUIT), command);
    }

    @Test
    public void parseCommand_unarchive() throws Exception {
        UnarchiveCommand command = (UnarchiveCommand) parser.parseCommand(
                UnarchiveCommand.COMMAND_WORD + " " + INDEX_FIRST_RECRUIT.getOneBased());
        assertEquals(new UnarchiveCommand(INDEX_FIRST_RECRUIT), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        UUID targetId = UUID.randomUUID();
        Recruit recruit = new SimpleRecruitBuilder()
                .build();
        EditRecruitDescriptor descriptor = new EditRecruitDescriptorBuilder(recruit)
                .build();
        String input = EditCommand.COMMAND_WORD + " " + targetId + " "
                + RecruitUtil.getEditRecruitDescriptorDetails(descriptor);
        EditCommand command = (EditCommand) parser.parseCommand(input);
        assertEquals(new EditCommand(targetId, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " n/ " + keywords.stream().collect(Collectors.joining(" n/")));
        assertEquals(new FindCommand(new NestedOrPredicate(
                        new FieldContainsKeywordsPredicate(keywords, SEARCH_PREFIX_NAME))), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE), () -> parser.parseCommand(
                        ListCommand.COMMAND_WORD + " 3"));
    }

    @Test
    public void parseCommand_undo() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD) instanceof UndoCommand);
    }

    @Test
    public void parseCommand_redo() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD) instanceof RedoCommand);
    }

    @Test
    public void parseCommand_sort() throws Exception {
        assertTrue(parser.parseCommand(SortCommand.COMMAND_WORD) instanceof SortCommand);
        assertTrue(parser.parseCommand(SortCommand.COMMAND_WORD + " -n asc") instanceof SortCommand);
        assertTrue(parser.parseCommand(SortCommand.COMMAND_WORD + " -n asc -p desc") instanceof SortCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () ->
                parser.parseCommand("unknownCommand"));
    }

    @Test
    public void parseCommand_view() throws Exception {
        ViewCommand command = (ViewCommand) parser.parseCommand(
                ViewCommand.COMMAND_WORD + " " + TypicalIDs.ID_FIRST_RECRUIT);
        assertEquals(new ViewCommand(TypicalIDs.ID_FIRST_RECRUIT), command);
    }

    @Test
    public void parseCommand_dismiss() throws Exception {
        assertTrue(parser.parseCommand(DismissCommand.COMMAND_WORD) instanceof DismissCommand);
    }
}
