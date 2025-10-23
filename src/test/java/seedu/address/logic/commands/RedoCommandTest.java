package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalRecruits.ALICE;
import static seedu.address.testutil.TypicalRecruits.AMY;
import static seedu.address.testutil.TypicalRecruits.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.parser.ListCommandParser.ListOperation;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for RedoCommand.
 */
public class RedoCommandTest {
    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_redoableOperationExists_success() throws Exception {
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        new AddCommand(AMY).execute(model);
        new DeleteCommand(ALICE.getID()).execute(model);
        new ListCommand(ModelManager.PREDICATE_SHOW_ALL_RECRUITS, ListOperation.FULL_LIST_OP).execute(model);
        new UndoCommand().execute(model);
        new UndoCommand().execute(model);

        expectedModel.addRecruit(AMY);
        expectedModel.commitAddressBook(String.format(AddCommand.OPERATION_DESCRIPTOR, AMY.getName(), AMY.getID()));
        expectedModel.updateFilteredRecruitList(ModelManager.PREDICATE_SHOW_ALL_RECRUITS);
        CommandResult expectedCommandResult = new CommandResult(String.format(
                RedoCommand.MESSAGE_SUCCESS, String.format(
                            AddCommand.OPERATION_DESCRIPTOR, Messages.format(AMY))));
        assertCommandSuccess(new RedoCommand(), model, expectedCommandResult, expectedModel);

        expectedModel.deleteRecruit(ALICE);
        expectedModel.commitAddressBook(String.format(DeleteCommand.OPERATION_DESCRIPTOR,
                ALICE.getName(), ALICE.getID()));
        expectedModel.updateFilteredRecruitList(ModelManager.PREDICATE_SHOW_ALL_RECRUITS);

        expectedCommandResult = new CommandResult(String.format(
                RedoCommand.MESSAGE_SUCCESS, String.format(
                            DeleteCommand.OPERATION_DESCRIPTOR, Messages.format(ALICE))));
        assertCommandSuccess(new RedoCommand(), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_noRedoableOperationExists_failure() throws Exception {
        new AddCommand(AMY).execute(model);
        new DeleteCommand(ALICE.getID()).execute(model);
        new ListCommand(ModelManager.PREDICATE_SHOW_ALL_RECRUITS, ListOperation.FULL_LIST_OP).execute(model);

        assertCommandFailure(new RedoCommand(), model, RedoCommand.MESSAGE_NO_OPERATION_TO_REDO);
    }
}
