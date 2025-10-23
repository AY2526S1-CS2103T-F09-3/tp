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
 * {@code ViewCommand}.
 */
public class ViewCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIdUnfilteredList_success() {
        Optional<Recruit> recruitToView = model.getUnfilteredRecruitByID(TypicalIDs.ID_FIRST_RECRUIT);
        assertTrue(recruitToView.isPresent(), Messages.MESSAGE_INVALID_RECRUIT_ID);
        ViewCommand viewCommand = new ViewCommand(TypicalIDs.ID_FIRST_RECRUIT);

        String expectedMessage = String.format(ViewCommand.MESSAGE_VIEW_RECRUIT_SUCCESS,
                Messages.format(recruitToView.get()));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        assertCommandSuccess(viewCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIdUnfilteredList_throwsCommandException() {
        UUID wrongID = UUID.randomUUID();
        ViewCommand viewCommand = new ViewCommand(wrongID);

        assertCommandFailure(viewCommand, model, Messages.MESSAGE_INVALID_RECRUIT_ID);
    }

    @Test
    public void execute_validIdFilteredList_success() {
        showRecruitAtIndex(model, INDEX_FIRST_RECRUIT);

        Optional<Recruit> recruitToView = model.getFilteredRecruitByID(TypicalIDs.ID_FIRST_RECRUIT);
        assertTrue(recruitToView.isPresent(), Messages.MESSAGE_INVALID_RECRUIT_ID);
        ViewCommand viewCommand = new ViewCommand(TypicalIDs.ID_FIRST_RECRUIT);

        String expectedMessage = String.format(ViewCommand.MESSAGE_VIEW_RECRUIT_SUCCESS,
                Messages.format(recruitToView.get()));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        showRecruitAtIndex(expectedModel, INDEX_FIRST_RECRUIT);

        assertCommandSuccess(viewCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showRecruitAtID(model, TypicalIDs.ID_FIRST_RECRUIT);
        UUID wrongID = UUID.randomUUID();

        ViewCommand viewCommand = new ViewCommand(wrongID);

        assertCommandFailure(viewCommand, model, Messages.MESSAGE_INVALID_RECRUIT_ID);
    }

    @Test
    public void equals() {
        ViewCommand viewFirstCommand = new ViewCommand(TypicalIDs.ID_FIRST_RECRUIT);
        ViewCommand viewSecondCommand = new ViewCommand(TypicalIDs.ID_SECOND_RECRUIT);

        // same object -> returns true
        assertTrue(viewFirstCommand.equals(viewFirstCommand));

        // same values -> returns true
        ViewCommand viewFirstCommandCopy = new ViewCommand(TypicalIDs.ID_FIRST_RECRUIT);
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
        UUID targetID = UUID.randomUUID();
        ViewCommand viewCommand = new ViewCommand(targetID);
        String expected = ViewCommand.class.getCanonicalName() + "{targetID=" + targetID.toString() + "}";
        assertEquals(expected, viewCommand.toString());
    }
}
