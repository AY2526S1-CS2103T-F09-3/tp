package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showRecruitAtIndex;
import static seedu.address.model.Model.PREDICATE_SHOW_UNARCHVIED_RECRUITS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_RECRUIT;
import static seedu.address.testutil.TypicalRecruits.getTypicalAddressBook;

import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.ListCommandParser;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.recruit.Recruit;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private static final Predicate<Recruit> SHOW_ALL_UNARCHIVED =
            recruit -> !recruit.isArchived();
    private static final Predicate<Recruit> SHOW_ALL_ARCHIVED =
            Recruit::isArchived;
    private static final Predicate<Recruit> SHOW_ALL =
            recruit -> true;

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(PREDICATE_SHOW_UNARCHVIED_RECRUITS,
                        ListCommandParser.NORMAL_LIST_OP), model, ListCommandParser.NORMAL_LIST_OP, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showRecruitAtIndex(model, INDEX_FIRST_RECRUIT);
        assertCommandSuccess(new ListCommand(PREDICATE_SHOW_UNARCHVIED_RECRUITS,
                        ListCommandParser.NORMAL_LIST_OP), model, ListCommandParser.NORMAL_LIST_OP, expectedModel);
    }

    @Test
    public void execute_listAllUnarchived_success() {
        ListCommand listCommand = new ListCommand(SHOW_ALL_UNARCHIVED, "Listed all unarchived recruits");
        expectedModel.updateFilteredRecruitList(SHOW_ALL_UNARCHIVED);

        assertCommandSuccess(listCommand, model,
                "Listed all unarchived recruits", expectedModel);
    }

    @Test
    public void execute_listAllArchived_success() {
        ListCommand listCommand = new ListCommand(SHOW_ALL_ARCHIVED, "Listed all archived recruits");
        expectedModel.updateFilteredRecruitList(SHOW_ALL_ARCHIVED);

        assertCommandSuccess(listCommand, model,
                "Listed all archived recruits", expectedModel);
    }

    @Test
    public void execute_listAll_success() {
        ListCommand listCommand = new ListCommand(SHOW_ALL, "Listed all recruits");
        expectedModel.updateFilteredRecruitList(SHOW_ALL);

        assertCommandSuccess(listCommand, model,
                "Listed all recruits", expectedModel);
    }

    @Test
    public void equals() {
        ListCommand listAll = new ListCommand(SHOW_ALL, "Listed all recruits");
        ListCommand listArchived = new ListCommand(SHOW_ALL_ARCHIVED, "Listed all archived recruits");

        // same object -> returns true
        assertTrue(listAll.equals(listAll));

        // same values -> returns true
        ListCommand listAllCopy = new ListCommand(SHOW_ALL, "Listed all recruits");
        assertTrue(listAll.equals(listAllCopy));

        // different types -> returns false
        assertFalse(listAll.equals(1));

        // null -> returns false
        assertFalse(listAll.equals(null));

        // different predicates or operation -> returns false
        assertFalse(listAll.equals(listArchived));
    }

    @Test
    public void toStringMethod() {
        ListCommand listCommand = new ListCommand(SHOW_ALL, "Listed all recruits");
        String expected = ListCommand.class.getCanonicalName()
                + "{predicate=" + SHOW_ALL + ", operation=Listed all recruits}";
        assertEquals(expected, listCommand.toString());
    }
}
