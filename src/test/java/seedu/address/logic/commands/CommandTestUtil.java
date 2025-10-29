package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.EDIT_PREFIX_APPEND;
import static seedu.address.logic.parser.CliSyntax.EDIT_PREFIX_OVERWRITE;
import static seedu.address.logic.parser.CliSyntax.EDIT_PREFIX_REMOVE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.SEARCH_PREFIX_NAME;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.recruit.FieldContainsKeywordsPredicate;
import seedu.address.model.recruit.Recruit;
import seedu.address.model.recruit.data.Address;
import seedu.address.model.recruit.data.Description;
import seedu.address.model.recruit.data.Email;
import seedu.address.model.recruit.data.Name;
import seedu.address.model.recruit.data.Phone;
import seedu.address.model.tag.Tag;


/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {
    public static final String VALID_ID_AMY = "eac9b117-2ded-42c3-9264-ccf3dfaaa957";
    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_ID_BOB = "eac9b117-2ded-42c3-9264-ccf3dfaaa958";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_DESCRIPTION_AMY = "Up and coming go-getter. Potential HR hire";
    public static final String VALID_DESCRIPTION_BOB = "Typical NUS CS Graduate, GPA lower than 4.99, so unlikely hire";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";
    public static final String VALID_TAG_CASHIER = "cashier";
    public static final String VALID_SORT_ASCENDING = "asc";
    public static final String VALID_SORT_DESCENDING = "desc";

    public static final String ID_DESC_AMY = " " + PREFIX_ID + VALID_ID_AMY;
    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String ID_DESC_BOB = " " + PREFIX_ID + VALID_ID_BOB;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String DESCRIPTION_DESC_AMY = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_AMY;
    public static final String DESCRIPTION_DESC_BOB = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String EDIT_OP_FLAG_APPEND = " " + EDIT_PREFIX_APPEND;
    public static final String EDIT_OP_FLAG_OVERWRITE = " " + EDIT_PREFIX_OVERWRITE;
    public static final String EDIT_OP_FLAG_REMOVE = " " + EDIT_PREFIX_REMOVE;

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String SEARCH_NAME_FLAG = "-n";
    public static final String SEARCH_ALT_NAME = "|";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditRecruitDescriptor DESC_AMY;
    public static final EditCommand.EditRecruitDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditCommand.EditRecruitDescriptor(EditCommand.EditOperation.OVERWRITE);
        DESC_AMY.withName(new Name(VALID_NAME_AMY));
        DESC_AMY.withPhone(new Phone(VALID_PHONE_AMY));
        DESC_AMY.withEmail(new Email(VALID_EMAIL_AMY));
        DESC_AMY.withAddress(new Address(VALID_ADDRESS_AMY));
        DESC_AMY.withDescription(new Description(VALID_DESCRIPTION_AMY));
        DESC_AMY.withTag(new Tag(VALID_TAG_FRIEND));

        DESC_BOB = new EditCommand.EditRecruitDescriptor(EditCommand.EditOperation.OVERWRITE);
        DESC_BOB.withName(new Name(VALID_NAME_BOB));
        DESC_BOB.withPhone(new Phone(VALID_PHONE_BOB));
        DESC_BOB.withEmail(new Email(VALID_EMAIL_BOB));
        DESC_BOB.withAddress(new Address(VALID_ADDRESS_BOB));
        DESC_BOB.withDescription(new Description(VALID_DESCRIPTION_BOB));
        DESC_BOB.withTags(List.of(new Tag(VALID_TAG_HUSBAND), new Tag(VALID_TAG_FRIEND)));
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
                                            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered recruit list and selected recruit in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Recruit> expectedFilteredList = new ArrayList<>(actualModel.getFilteredRecruitList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getAddressBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredRecruitList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showRecruitAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredRecruitList().size());

        Recruit recruit = model.getFilteredRecruitList().get(targetIndex.getZeroBased());
        final String[] splitName = recruit.getName().value.split("\\s+");
        model.updateFilteredRecruitList(new FieldContainsKeywordsPredicate(
                Arrays.asList(splitName[0]), SEARCH_PREFIX_NAME));

        assertEquals(1, model.getFilteredRecruitList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show only the person at the given {@code targetId} in the
     * {@code model}'s address book.
     */
    public static void showRecruitAtID(Model model, UUID targetId) {
        Optional<Recruit> recruit = model.getFilteredRecruitByID(targetId);
        assertTrue(recruit.isPresent());
        final String[] splitName = recruit.get().getName().value.split("\\s+");
        model.updateFilteredRecruitList(new FieldContainsKeywordsPredicate(Arrays.asList(splitName[0]),
                SEARCH_PREFIX_NAME));

        assertEquals(1, model.getFilteredRecruitList().size());
    }

}
