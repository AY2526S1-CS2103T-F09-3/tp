package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showRecruitAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_RECRUIT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_RECRUIT;
import static seedu.address.testutil.TypicalRecruits.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.recruit.Recruit;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code ViewCommand}.
 */
public class ViewCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Recruit recruitToView = model.getFilteredRecruitList().get(INDEX_FIRST_RECRUIT.getZeroBased());
        ViewCommand viewCommand = new ViewCommand(INDEX_FIRST_RECRUIT);

        String expectedMessage = String.format(ViewCommand.MESSAGE_VIEW_RECRUIT_SUCCESS,
                Messages.format(recruitToView));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        assertCommandSuccess(viewCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredRecruitList().size() + 1);
        ViewCommand viewCommand = new ViewCommand(outOfBoundIndex);

        assertCommandFailure(viewCommand, model, Messages.MESSAGE_INVALID_RECRUIT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showRecruitAtIndex(model, INDEX_FIRST_RECRUIT);

        Recruit recruitToView = model.getFilteredRecruitList().get(INDEX_FIRST_RECRUIT.getZeroBased());
        ViewCommand viewCommand = new ViewCommand(INDEX_FIRST_RECRUIT);

        String expectedMessage = String.format(ViewCommand.MESSAGE_VIEW_RECRUIT_SUCCESS,
                Messages.format(recruitToView));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        showRecruitAtIndex(expectedModel, INDEX_FIRST_RECRUIT);

        assertCommandSuccess(viewCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showRecruitAtIndex(model, INDEX_FIRST_RECRUIT);

        Index outOfBoundIndex = INDEX_SECOND_RECRUIT;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getRecruitList().size());

        ViewCommand viewCommand = new ViewCommand(outOfBoundIndex);

        assertCommandFailure(viewCommand, model, Messages.MESSAGE_INVALID_RECRUIT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        ViewCommand viewFirstCommand = new ViewCommand(INDEX_FIRST_RECRUIT);
        ViewCommand viewSecondCommand = new ViewCommand(INDEX_SECOND_RECRUIT);

        // same object -> returns true
        assertTrue(viewFirstCommand.equals(viewFirstCommand));

        // same values -> returns true
        ViewCommand viewFirstCommandCopy = new ViewCommand(INDEX_FIRST_RECRUIT);
        assertTrue(viewFirstCommand.equals(viewFirstCommandCopy));

        // different types -> returns false
        assertFalse(viewFirstCommand.equals(1));

        // null -> returns false
        assertFalse(viewFirstCommand.equals(null));

        // different recruit -> returns false
        assertFalse(viewFirstCommand.equals(viewSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        ViewCommand viewCommand = new ViewCommand(targetIndex);
        String expected = ViewCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, viewCommand.toString());
    }
}
