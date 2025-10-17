package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showRecruitAtID;
import static seedu.address.logic.commands.CommandTestUtil.showRecruitAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_RECRUIT;
import static seedu.address.testutil.TypicalRecruits.getTypicalAddressBook;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.recruit.Recruit;
import seedu.address.testutil.TypicalIDs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIdUnfilteredList_success() {
        Optional<Recruit> recruitToDelete = model.getFilteredRecruitByID(TypicalIDs.ID_FIRST_RECRUIT);
        assertTrue(recruitToDelete.isPresent(), Messages.MESSAGE_INVALID_RECRUIT_ID);

        DeleteCommand deleteCommand = new DeleteCommand(TypicalIDs.ID_FIRST_RECRUIT);
        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_RECRUIT_SUCCESS,
                Messages.format(recruitToDelete.get()));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteRecruit(recruitToDelete.get());

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIdUnfilteredList_throwsCommandException() {
        UUID wrongID = UUID.randomUUID();
        DeleteCommand deleteCommand = new DeleteCommand(wrongID);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_RECRUIT_ID);
    }

    @Test
    public void execute_validIdFilteredList_success() {
        showRecruitAtIndex(model, INDEX_FIRST_RECRUIT);

        Optional<Recruit>recruitToDelete = model.getFilteredRecruitByID(TypicalIDs.ID_FIRST_RECRUIT);
        assertTrue(recruitToDelete.isPresent(), Messages.MESSAGE_INVALID_RECRUIT_ID);

        DeleteCommand deleteCommand = new DeleteCommand(TypicalIDs.ID_FIRST_RECRUIT);
        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_RECRUIT_SUCCESS,
                Messages.format(recruitToDelete.get()));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteRecruit(recruitToDelete.get());
        showNoRecruit(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIdFilteredList_throwsCommandException() {
        showRecruitAtID(model, TypicalIDs.ID_FIRST_RECRUIT);

        UUID wrongID = UUID.randomUUID();

        DeleteCommand deleteCommand = new DeleteCommand(wrongID);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_RECRUIT_ID);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(TypicalIDs.ID_FIRST_RECRUIT);
        DeleteCommand deleteSecondCommand = new DeleteCommand(TypicalIDs.ID_SECOND_RECRUIT);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(TypicalIDs.ID_FIRST_RECRUIT);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        UUID targetID = UUID.randomUUID();
        DeleteCommand deleteCommand = new DeleteCommand(targetID);
        String expected = DeleteCommand.class.getCanonicalName() + "{targetID=" + targetID.toString() + "}";
        assertEquals(expected, deleteCommand.toString());
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoRecruit(Model model) {
        model.updateFilteredRecruitList(p -> false);

        assertTrue(model.getFilteredRecruitList().isEmpty());
    }
}
