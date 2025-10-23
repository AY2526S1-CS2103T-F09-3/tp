package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_RECRUIT_DISPLAYED_INDEX;
import static seedu.address.logic.commands.UnarchiveCommand.MESSAGE_ARCHIVE_RECRUIT_SUCCESS;
import static seedu.address.logic.commands.UnarchiveCommand.RECRUIT_ALREADY_UNARCHIVED;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_RECRUIT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_RECRUIT;
import static seedu.address.testutil.TypicalRecruits.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.recruit.Recruit;

public class UnarchiveCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @BeforeEach
    public void setUp() {
        // Reset model to a fresh copy before each test
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_validIndexArchivedRecruit_success() throws Exception {
        Recruit recruitToUnarchive = model.getFilteredRecruitList().get(INDEX_FIRST_RECRUIT.getZeroBased());

        // Manually mark as archived
        Recruit archivedRecruit = new Recruit(
                recruitToUnarchive.getID(),
                recruitToUnarchive.getNames(),
                recruitToUnarchive.getPhones(),
                recruitToUnarchive.getEmails(),
                recruitToUnarchive.getAddresses(),
                recruitToUnarchive.getTags(),
                true
        );
        model.setRecruit(recruitToUnarchive, archivedRecruit);

        model.updateFilteredRecruitList(Model.PREDICATE_SHOW_ARCHIVED_RECRUITS);
        UnarchiveCommand unarchiveCommand = new UnarchiveCommand(INDEX_FIRST_RECRUIT);
        String expectedMessage = String.format(MESSAGE_ARCHIVE_RECRUIT_SUCCESS, Messages.format(recruitToUnarchive));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Recruit unarchivedRecruit = new Recruit(
                archivedRecruit.getID(),
                archivedRecruit.getNames(),
                archivedRecruit.getPhones(),
                archivedRecruit.getEmails(),
                archivedRecruit.getAddresses(),
                archivedRecruit.getTags(),
                false
        );
        expectedModel.setRecruit(archivedRecruit, unarchivedRecruit);
        expectedModel.refreshFilteredRecruitList();

        CommandResult result = unarchiveCommand.execute(model);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(expectedModel.getAddressBook(), model.getAddressBook());
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        int outOfBoundIndex = model.getFilteredRecruitList().size() + 1;
        UnarchiveCommand unarchiveCommand = new UnarchiveCommand(Index.fromOneBased(outOfBoundIndex));

        assertThrows(CommandException.class, MESSAGE_INVALID_RECRUIT_DISPLAYED_INDEX, () ->
                unarchiveCommand.execute(model));
    }

    @Test
    public void execute_recruitAlreadyUnarchived_throwsCommandException() {
        Recruit recruitToUnarchive = model.getFilteredRecruitList().get(INDEX_FIRST_RECRUIT.getZeroBased());

        // Recruit is already unarchived (default state)
        UnarchiveCommand unarchiveCommand = new UnarchiveCommand(INDEX_FIRST_RECRUIT);
        assertThrows(CommandException.class, RECRUIT_ALREADY_UNARCHIVED, () -> unarchiveCommand.execute(model));
    }

    @Test
    public void equals() {
        UnarchiveCommand unarchiveFirstCommand = new UnarchiveCommand(INDEX_FIRST_RECRUIT);
        UnarchiveCommand unarchiveSecondCommand = new UnarchiveCommand(INDEX_SECOND_RECRUIT);

        // same object -> returns true
        assertTrue(unarchiveFirstCommand.equals(unarchiveFirstCommand));

        // same values -> returns true
        UnarchiveCommand unarchiveFirstCommandCopy = new UnarchiveCommand(INDEX_FIRST_RECRUIT);
        assertTrue(unarchiveFirstCommand.equals(unarchiveFirstCommandCopy));

        // different types -> returns false
        assertFalse(unarchiveFirstCommand.equals(1));

        // null -> returns false
        assertFalse(unarchiveFirstCommand.equals(null));

        // different index -> returns false
        assertFalse(unarchiveFirstCommand.equals(unarchiveSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index index = INDEX_FIRST_RECRUIT;
        UnarchiveCommand unarchiveCommand = new UnarchiveCommand(index);
        String expected = UnarchiveCommand.class.getCanonicalName() + "{index=" + index + "}";
        assertEquals(expected, unarchiveCommand.toString());
    }
}
