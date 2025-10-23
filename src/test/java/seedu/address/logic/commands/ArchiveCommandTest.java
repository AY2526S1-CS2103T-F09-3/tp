package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_RECRUIT_DISPLAYED_INDEX;
import static seedu.address.logic.commands.ArchiveCommand.MESSAGE_ARCHIVE_RECRUIT_SUCCESS;
import static seedu.address.logic.commands.ArchiveCommand.MESSAGE_DUPLICATE_RECRUIT;
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

public class ArchiveCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @BeforeEach
    public void setUp() {
        // Ensure model is in a clean state before each test
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_validIndexUnarchivedRecruit_success() throws Exception {
        Recruit recruitToArchive = model.getFilteredRecruitList().get(INDEX_FIRST_RECRUIT.getZeroBased());
        ArchiveCommand archiveCommand = new ArchiveCommand(INDEX_FIRST_RECRUIT);

        String expectedMessage = String.format(MESSAGE_ARCHIVE_RECRUIT_SUCCESS, Messages.format(recruitToArchive));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Recruit archivedRecruit = new Recruit(
                recruitToArchive.getID(),
                recruitToArchive.getNames(),
                recruitToArchive.getPhones(),
                recruitToArchive.getEmails(),
                recruitToArchive.getAddresses(),
                recruitToArchive.getTags(),
                true
        );
        expectedModel.setRecruit(recruitToArchive, archivedRecruit);
        expectedModel.refreshFilteredRecruitList();

        CommandResult result = archiveCommand.execute(model);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(expectedModel.getAddressBook(), model.getAddressBook());
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        int outOfBoundIndex = model.getFilteredRecruitList().size() + 1;
        ArchiveCommand archiveCommand = new ArchiveCommand(Index.fromOneBased(outOfBoundIndex));

        assertThrows(CommandException.class, MESSAGE_INVALID_RECRUIT_DISPLAYED_INDEX, () ->
                archiveCommand.execute(model));
    }

    @Test
    public void execute_alreadyArchivedRecruit_throwsCommandException() throws Exception {
        Recruit recruitToArchive = model.getFilteredRecruitList().get(INDEX_FIRST_RECRUIT.getZeroBased());
        // Manually mark as archived
        Recruit archivedRecruit = new Recruit(
                recruitToArchive.getNames(),
                recruitToArchive.getPhones(),
                recruitToArchive.getEmails(),
                recruitToArchive.getAddresses(),
                recruitToArchive.getTags(),
                true
        );
        model.setRecruit(recruitToArchive, archivedRecruit);
        model.updateFilteredRecruitList(Model.PREDICATE_SHOW_ARCHIVED_RECRUITS);

        ArchiveCommand archiveCommand = new ArchiveCommand(INDEX_FIRST_RECRUIT);
        assertThrows(CommandException.class, MESSAGE_DUPLICATE_RECRUIT, () -> archiveCommand.execute(model));
    }

    @Test
    public void equals() {
        ArchiveCommand archiveFirstCommand = new ArchiveCommand(INDEX_FIRST_RECRUIT);
        ArchiveCommand archiveSecondCommand = new ArchiveCommand(INDEX_SECOND_RECRUIT);

        // same object -> returns true
        assertTrue(archiveFirstCommand.equals(archiveFirstCommand));

        // same values -> returns true
        ArchiveCommand archiveFirstCommandCopy = new ArchiveCommand(INDEX_FIRST_RECRUIT);
        assertTrue(archiveFirstCommand.equals(archiveFirstCommandCopy));

        // different types -> returns false
        assertFalse(archiveFirstCommand.equals(1));

        // null -> returns false
        assertFalse(archiveFirstCommand.equals(null));

        // different index -> returns false
        assertFalse(archiveFirstCommand.equals(archiveSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index index = INDEX_FIRST_RECRUIT;
        ArchiveCommand archiveCommand = new ArchiveCommand(index);
        String expected = ArchiveCommand.class.getCanonicalName() + "{index=" + index + "}";
        assertEquals(expected, archiveCommand.toString());
    }
}
