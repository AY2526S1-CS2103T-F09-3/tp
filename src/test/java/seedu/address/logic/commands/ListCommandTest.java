package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showRecruitAtIndex;
import static seedu.address.model.Model.PREDICATE_SHOW_UNARCHVIED_RECRUITS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_RECRUIT;
import static seedu.address.testutil.TypicalRecruits.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(PREDICATE_SHOW_UNARCHVIED_RECRUITS), model,
                ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showRecruitAtIndex(model, INDEX_FIRST_RECRUIT);
        assertCommandSuccess(new ListCommand(PREDICATE_SHOW_UNARCHVIED_RECRUITS), model,
                ListCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
