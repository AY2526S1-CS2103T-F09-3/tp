package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalRecruits.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for SortCommand.
 */
public class SortCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_sortRecruits_success() {
        SortCommand sortCommand = new SortCommand();
        expectedModel.sortRecruits();

        assertCommandSuccess(sortCommand, model, SortCommand.MESSAGE_SUCCESS, expectedModel);

        // Verify the list is sorted alphabetically by name
        var recruitList = model.getFilteredRecruitList();
        for (int i = 0; i < recruitList.size() - 1; i++) {
            String currentName = recruitList.get(i).getName().fullName;
            String nextName = recruitList.get(i + 1).getName().fullName;
            assertTrue(currentName.compareTo(nextName) <= 0,
                    "Recruits should be sorted alphabetically. Found: "
                    + currentName + " before " + nextName);
        }
    }

    @Test
    public void execute_emptyList_success() {
        model = new ModelManager();
        expectedModel = new ModelManager();

        SortCommand sortCommand = new SortCommand();
        assertCommandSuccess(sortCommand, model, SortCommand.MESSAGE_SUCCESS, expectedModel);

        // Verify empty list remains empty
        assertEquals(0, model.getFilteredRecruitList().size());
    }
}

