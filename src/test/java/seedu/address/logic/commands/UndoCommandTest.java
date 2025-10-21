package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalRecruits.ALICE;
import static seedu.address.testutil.TypicalRecruits.AMY;
import static seedu.address.testutil.TypicalRecruits.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integratio tests (interaction with the Model) and unit tests for UndoCommand.
 */
public class UndoCommandTest {
    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_undoableOperationExists_success() throws Exception {
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        new AddCommand(AMY).execute(model);
        new DeleteCommand(ALICE.getID()).execute(model);
        new ListCommand().execute(model);

        expectedModel.addRecruit(AMY);
        expectedModel.commitAddressBook(String.format(AddCommand.OPERATION_DESCRIPTOR, AMY.getName(), AMY.getID()));
        expectedModel.deleteRecruit(ALICE);
        expectedModel.commitAddressBook(String.format(DeleteCommand.OPERATION_DESCRIPTOR,
                ALICE.getName(), ALICE.getID()));
        expectedModel.undoAddressBook();
        expectedModel.updateFilteredRecruitList(ModelManager.PREDICATE_SHOW_ALL_RECRUITS);

        CommandResult expectedCommandResult = new CommandResult(String.format(
                UndoCommand.MESSAGE_SUCCESS, String.format(
                            DeleteCommand.OPERATION_DESCRIPTOR, Messages.format(ALICE))));

        assertCommandSuccess(new UndoCommand(), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_noUndoableOperationExists_failure() throws Exception {
        new AddCommand(AMY).execute(model);
        new DeleteCommand(ALICE.getID()).execute(model);
        new ListCommand().execute(model);
        new UndoCommand().execute(model);
        new UndoCommand().execute(model);

        assertCommandFailure(new UndoCommand(), model, UndoCommand.MESSAGE_NO_OPERATION_TO_UNDO);
    }
}
