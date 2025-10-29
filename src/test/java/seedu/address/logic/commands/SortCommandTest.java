package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.parser.CliSyntax.SORT_PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.SORT_PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.SORT_PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.SORT_PREFIX_PHONE;
import static seedu.address.testutil.TypicalRecruits.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SortCommand.SortCriterion;
import seedu.address.logic.commands.SortCommand.SortOrder;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.recruit.Recruit;

public class SortCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

    @Test
    public void execute_sortByNameAscending_success() {
        List<SortCriterion> criteria = new ArrayList<>();
        criteria.add(new SortCriterion(SORT_PREFIX_NAME, SortOrder.ASCENDING));

        SortCommand sortCommand = new SortCommand(criteria);

        Comparator<Recruit> comparator = Comparator.comparing(recruit -> recruit.getName().fullName.toLowerCase());
        expectedModel.sortRecruits(comparator);

        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, "-n (ascending)");
        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);

        // Verify the list is sorted alphabetically by name
        var recruitList = model.getFilteredRecruitList();
        for (int i = 0; i < recruitList.size() - 1; i++) {
            String currentName = recruitList.get(i).getName().fullName.toLowerCase();
            String nextName = recruitList.get(i + 1).getName().fullName.toLowerCase();
            assertFalse(currentName.compareTo(nextName) > 0,
                    "Recruits should be sorted alphabetically. Found: "
                    + currentName + " before " + nextName);
        }
    }

    @Test
    public void execute_sortByNameDescending_success() {
        List<SortCriterion> criteria = new ArrayList<>();
        criteria.add(new SortCriterion(SORT_PREFIX_NAME, SortOrder.DESCENDING));

        SortCommand sortCommand = new SortCommand(criteria);

        Comparator<Recruit> comparator = Comparator.comparing((
                Recruit recruit) -> recruit.getName().fullName.toLowerCase()).reversed();
        expectedModel.sortRecruits(comparator);

        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, "-n (descending)");
        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);

        // Verify the list is sorted in reverse alphabetical order by name
        var recruitList = model.getFilteredRecruitList();
        for (int i = 0; i < recruitList.size() - 1; i++) {
            String currentName = recruitList.get(i).getName().fullName.toLowerCase();
            String nextName = recruitList.get(i + 1).getName().fullName.toLowerCase();
            assertFalse(currentName.compareTo(nextName) < 0,
                    "Recruits should be sorted in reverse alphabetically. Found: "
                    + currentName + " before " + nextName);
        }
    }

    @Test
    public void execute_sortByPhoneAscending_success() {
        List<SortCriterion> criteria = new ArrayList<>();
        criteria.add(new SortCriterion(SORT_PREFIX_PHONE, SortOrder.ASCENDING));

        SortCommand sortCommand = new SortCommand(criteria);

        Comparator<Recruit> comparator = Comparator.comparing(recruit -> recruit.getPhone().get().value);
        expectedModel.sortRecruits(comparator);

        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, "-p (ascending)");
        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);

        // Verify the list is sorted by phone number
        var recruitList = model.getFilteredRecruitList();
        for (int i = 0; i < recruitList.size() - 1; i++) {
            String currentPhone = recruitList.get(i).getPhone().get().value;
            String nextPhone = recruitList.get(i + 1).getPhone().get().value;
            assertFalse(currentPhone.compareTo(nextPhone) > 0,
                    "Recruits should be sorted by phone number. Found: "
                    + currentPhone + " before " + nextPhone);
        }
    }

    @Test
    public void execute_sortByEmailDescending_success() {
        List<SortCriterion> criteria = new ArrayList<>();
        criteria.add(new SortCriterion(SORT_PREFIX_EMAIL, SortOrder.DESCENDING));

        SortCommand sortCommand = new SortCommand(criteria);

        Comparator<Recruit> comparator = Comparator.comparing((
                Recruit recruit) -> recruit.getEmail().get().value.toLowerCase()).reversed();
        expectedModel.sortRecruits(comparator);

        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, "-e (descending)");
        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);

        // Verify the list is sorted in reverse order by email
        var recruitList = model.getFilteredRecruitList();
        for (int i = 0; i < recruitList.size() - 1; i++) {
            String currentEmail = recruitList.get(i).getEmail().get().value.toLowerCase();
            String nextEmail = recruitList.get(i + 1).getEmail().get().value.toLowerCase();
            assertFalse(currentEmail.compareTo(nextEmail) < 0,
                    "Recruits should be sorted by email in reverse order. Found: "
                    + currentEmail + " before " + nextEmail);
        }
    }

    @Test
    public void execute_sortByAddressAscending_success() {
        List<SortCriterion> criteria = new ArrayList<>();
        criteria.add(new SortCriterion(SORT_PREFIX_ADDRESS, SortOrder.ASCENDING));

        SortCommand sortCommand = new SortCommand(criteria);

        Comparator<Recruit> comparator = Comparator.comparing(
                recruit -> recruit.getAddress().get().value.toLowerCase());
        expectedModel.sortRecruits(comparator);

        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, "-a (ascending)");
        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_sortByMultipleFields_success() {
        List<SortCriterion> criteria = new ArrayList<>();
        criteria.add(new SortCriterion(SORT_PREFIX_NAME, SortOrder.ASCENDING));
        criteria.add(new SortCriterion(SORT_PREFIX_PHONE, SortOrder.DESCENDING));
        criteria.add(new SortCriterion(SORT_PREFIX_EMAIL, SortOrder.ASCENDING));

        SortCommand sortCommand = new SortCommand(criteria);

        Comparator<Recruit> comparator = Comparator
                .comparing((Recruit recruit) -> recruit.getName().fullName.toLowerCase())
                .thenComparing(Comparator.comparing((Recruit r) -> r.getPhone().get().value).reversed())
                .thenComparing(recruit -> recruit.getEmail().get().value.toLowerCase());
        expectedModel.sortRecruits(comparator);

        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS,
                "-n (ascending), -p (descending), -e (ascending)");
        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_sortByAllFields_success() {
        List<SortCriterion> criteria = new ArrayList<>();
        criteria.add(new SortCriterion(SORT_PREFIX_NAME, SortOrder.ASCENDING));
        criteria.add(new SortCriterion(SORT_PREFIX_PHONE, SortOrder.ASCENDING));
        criteria.add(new SortCriterion(SORT_PREFIX_EMAIL, SortOrder.ASCENDING));
        criteria.add(new SortCriterion(SORT_PREFIX_ADDRESS, SortOrder.ASCENDING));

        SortCommand sortCommand = new SortCommand(criteria);

        Comparator<Recruit> comparator = Comparator
                .comparing((Recruit recruit) -> recruit.getName().fullName.toLowerCase())
                .thenComparing(recruit -> recruit.getPhone().get().value)
                .thenComparing(recruit -> recruit.getEmail().get().value.toLowerCase())
                .thenComparing(recruit -> recruit.getAddress().get().value.toLowerCase());
        expectedModel.sortRecruits(comparator);

        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS,
                "-n (ascending), -p (ascending), -e (ascending), -a (ascending)");
        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_emptyList_success() {
        model = new ModelManager();
        expectedModel = new ModelManager();

        List<SortCriterion> criteria = new ArrayList<>();
        criteria.add(new SortCriterion(SORT_PREFIX_NAME, SortOrder.ASCENDING));

        SortCommand sortCommand = new SortCommand(criteria);

        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, "-n (ascending)");
        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);

        // Verify empty list remains empty
        assertEquals(0, model.getFilteredRecruitList().size());
    }

    @Test
    public void toString_sortCriterion_correctFormat() {
        SortCriterion criterion = new SortCriterion(SORT_PREFIX_NAME, SortOrder.ASCENDING);
        assertEquals("-n (ascending)", criterion.toString());

        SortCriterion criterion2 = new SortCriterion(SORT_PREFIX_PHONE, SortOrder.DESCENDING);
        assertEquals("-p (descending)", criterion2.toString());
    }
}

