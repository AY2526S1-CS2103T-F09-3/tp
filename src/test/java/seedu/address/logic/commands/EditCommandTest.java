package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_CASHIER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showRecruitAtID;
import static seedu.address.logic.commands.CommandTestUtil.showRecruitAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_RECRUIT;
import static seedu.address.testutil.TypicalRecruits.ALICE;
import static seedu.address.testutil.TypicalRecruits.getTypicalAddressBook;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditCommand.EditRecruitDescriptor;
import seedu.address.logic.commands.EditCommand.EditRecruitDescriptor.EditRecruitOperation;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.recruit.Recruit;
import seedu.address.testutil.EditRecruitDescriptorBuilder;
import seedu.address.testutil.RecruitBuilder;
import seedu.address.testutil.TypicalIDs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_overwriteOperationAllFieldsSpecifiedUnfilteredList_success() {
        Recruit editedRecruit = new RecruitBuilder(ALICE).build();
        EditRecruitDescriptor descriptor = new EditRecruitDescriptorBuilder(editedRecruit).build();
        EditCommand editCommand = new EditCommand(TypicalIDs.ID_FIRST_RECRUIT, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_RECRUIT_SUCCESS,
                editCommand.formatDelta(editedRecruit, editedRecruit));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setRecruit(model.getAddressBook().getRecruitList().get(0), editedRecruit);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_appendOperationAllFieldsSpecifiedUnfilteredList_success() {
        Recruit initialRecruit = new RecruitBuilder(ALICE).build();
        Recruit expectedRecruit = new RecruitBuilder(initialRecruit)
                .withAdditionalNames(VALID_NAME_AMY, VALID_NAME_BOB)
                .withAdditionalPhones(VALID_PHONE_AMY, VALID_PHONE_BOB)
                .withAdditionalEmails(VALID_EMAIL_AMY, VALID_EMAIL_BOB)
                .withAdditionalAddresses(VALID_ADDRESS_AMY, VALID_ADDRESS_BOB)
                .withAdditionalTags(VALID_TAG_CASHIER, VALID_TAG_HUSBAND)
                .build();

        EditRecruitDescriptor descriptor = new EditRecruitDescriptorBuilder()
                .withOperation(EditRecruitOperation.APPEND)
                .withNames(VALID_NAME_AMY, VALID_NAME_BOB)
                .withPhones(VALID_PHONE_AMY, VALID_PHONE_BOB)
                .withEmails(VALID_EMAIL_AMY, VALID_EMAIL_BOB)
                .withAddresses(VALID_ADDRESS_AMY, VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_CASHIER, VALID_TAG_HUSBAND)
                .build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_RECRUIT, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_RECRUIT_SUCCESS,
                editCommand.formatDelta(initialRecruit, expectedRecruit));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setRecruit(model.getFilteredRecruitList().get(0), expectedRecruit);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_appendOperationDuplicateAttributesUnfilteredList_failure() {
        // duplicate name
        EditRecruitDescriptor descriptor = new EditRecruitDescriptorBuilder()
                .withOperation(EditRecruitOperation.APPEND)
                .withNames(ALICE.getName().fullName)
                .withPhones(VALID_PHONE_AMY).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_RECRUIT, descriptor);
        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_ATTRIBUTE);

        // duplicate phone
        descriptor = new EditRecruitDescriptorBuilder()
                .withOperation(EditRecruitOperation.APPEND)
                .withNames(VALID_NAME_AMY)
                .withPhones(ALICE.getPhone().get().value, VALID_PHONE_AMY).build();
        editCommand = new EditCommand(INDEX_FIRST_RECRUIT, descriptor);
        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_ATTRIBUTE);

        // duplicate email
        descriptor = new EditRecruitDescriptorBuilder()
                .withOperation(EditRecruitOperation.APPEND)
                .withNames(VALID_NAME_AMY, VALID_NAME_BOB)
                .withPhones(VALID_PHONE_AMY)
                .withEmails(ALICE.getEmail().get().value, VALID_EMAIL_BOB).build();
        editCommand = new EditCommand(INDEX_FIRST_RECRUIT, descriptor);
        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_ATTRIBUTE);

        // duplicate address
        descriptor = new EditRecruitDescriptorBuilder()
                .withOperation(EditRecruitOperation.APPEND)
                .withNames(VALID_NAME_AMY, VALID_NAME_BOB)
                .withPhones(VALID_PHONE_AMY)
                .withEmails(VALID_EMAIL_BOB)
                .withAddresses(VALID_ADDRESS_BOB, ALICE.getAddress().get().value).build();
        editCommand = new EditCommand(INDEX_FIRST_RECRUIT, descriptor);
        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_ATTRIBUTE);

        // duplicate tags
        descriptor = new EditRecruitDescriptorBuilder()
                .withOperation(EditRecruitOperation.APPEND)
                .withNames(VALID_NAME_AMY, VALID_NAME_BOB)
                .withPhones(VALID_PHONE_AMY)
                .withEmails(VALID_EMAIL_BOB)
                .withAddresses(VALID_ADDRESS_BOB)
                .withTags("friends").build();
        editCommand = new EditCommand(INDEX_FIRST_RECRUIT, descriptor);
        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_ATTRIBUTE);
    }

    @Test
    public void execute_removeOperationNonExistentAttributesUnfilteredList_failure() {
        // non-existent name
        EditRecruitDescriptor descriptor = new EditRecruitDescriptorBuilder()
                .withOperation(EditRecruitOperation.REMOVE)
                .withNames(VALID_NAME_BOB)
                .withPhones(ALICE.getPhone().get().value).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_RECRUIT, descriptor);
        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_MISSING_ATTRIBUTE);

        // non-existent phone
        descriptor = new EditRecruitDescriptorBuilder()
                .withOperation(EditRecruitOperation.REMOVE)
                .withNames(ALICE.getName().fullName)
                .withPhones(VALID_PHONE_AMY, VALID_PHONE_BOB).build();
        editCommand = new EditCommand(INDEX_FIRST_RECRUIT, descriptor);
        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_MISSING_ATTRIBUTE);

        // non-existent email
        descriptor = new EditRecruitDescriptorBuilder()
                .withOperation(EditRecruitOperation.REMOVE)
                .withNames(ALICE.getName().fullName)
                .withEmails(VALID_EMAIL_BOB).build();
        editCommand = new EditCommand(INDEX_FIRST_RECRUIT, descriptor);
        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_MISSING_ATTRIBUTE);

        // non-existent address
        descriptor = new EditRecruitDescriptorBuilder()
                .withOperation(EditRecruitOperation.REMOVE)
                .withPhones(ALICE.getPhone().get().value)
                .withEmails(ALICE.getEmail().get().value)
                .withAddresses(VALID_ADDRESS_BOB).build();
        editCommand = new EditCommand(INDEX_FIRST_RECRUIT, descriptor);
        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_MISSING_ATTRIBUTE);

        // non-existent tags
        descriptor = new EditRecruitDescriptorBuilder()
                .withOperation(EditRecruitOperation.REMOVE)
                .withPhones(ALICE.getPhone().get().value)
                .withEmails(ALICE.getEmail().get().value)
                .withTags(VALID_TAG_CASHIER).build();
        editCommand = new EditCommand(INDEX_FIRST_RECRUIT, descriptor);
        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_MISSING_ATTRIBUTE);
    }

    @Test
    public void execute_removeOperationAllFieldsSpecifiedUnfilteredList_success() throws CommandException {
        Recruit initialRecruit = new RecruitBuilder(ALICE)
                .withAdditionalNames(VALID_NAME_AMY, VALID_NAME_BOB)
                .withAdditionalPhones(VALID_PHONE_AMY, VALID_PHONE_BOB)
                .withAdditionalEmails(VALID_EMAIL_AMY, VALID_EMAIL_BOB)
                .withAdditionalAddresses(VALID_ADDRESS_AMY, VALID_ADDRESS_BOB)
                .withAdditionalTags(VALID_TAG_CASHIER, VALID_TAG_HUSBAND)
                .build();
        Recruit editedRecruit = new RecruitBuilder(ALICE)
                .withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY)
                .withAddress(VALID_ADDRESS_AMY)
                .withTags(VALID_TAG_CASHIER)
                .build();
        Model initialModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        initialModel.setRecruit(model.getFilteredRecruitList().get(0), initialRecruit);

        EditRecruitDescriptor descriptor = new EditRecruitDescriptorBuilder()
                .withOperation(EditRecruitOperation.REMOVE)
                .withNames(ALICE.getName().fullName, VALID_NAME_BOB)
                .withPhones(ALICE.getPhone().get().value, VALID_PHONE_BOB)
                .withEmails(ALICE.getEmail().get().value, VALID_EMAIL_BOB)
                .withAddresses(ALICE.getAddress().get().value, VALID_ADDRESS_BOB)
                .withTags("friends", VALID_TAG_HUSBAND)
                .build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_RECRUIT, descriptor);
        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_RECRUIT_SUCCESS,
                editCommand.formatDelta(initialRecruit, editedRecruit));
        Model expectedModel = new ModelManager(new AddressBook(initialModel.getAddressBook()), new UserPrefs());
        expectedModel.setRecruit(initialRecruit, editedRecruit);
        expectedModel.commitAddressBook(String.format(
                EditCommand.OPERATION_DESCRIPTOR, editCommand.formatDelta(initialRecruit, editedRecruit)));

        assertCommandSuccess(editCommand, initialModel, expectedMessage, expectedModel);
    }

    @Test
    public void execute_overwriteOperationSomeFieldsSpecifiedUnfilteredList_success() {
        Optional<Recruit> recruitToEdit = model.getUnfilteredRecruitByID(TypicalIDs.ID_THIRD_RECRUIT);
        assertTrue(recruitToEdit.isPresent(), Messages.MESSAGE_INVALID_RECRUIT_ID);

        RecruitBuilder recruitInList = new RecruitBuilder(recruitToEdit.get());
        Recruit initialRecruit = recruitInList.build();
        Recruit editedRecruit = recruitInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        EditRecruitDescriptor descriptor = new EditRecruitDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();
        EditCommand editCommand = new EditCommand(TypicalIDs.ID_THIRD_RECRUIT, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_RECRUIT_SUCCESS,
                editCommand.formatDelta(initialRecruit, editedRecruit));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setRecruit(recruitToEdit.get(), editedRecruit);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_overwriteOperationNoFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(TypicalIDs.ID_FIRST_RECRUIT, new EditRecruitDescriptor());
        Optional<Recruit> recruitToEdit = model.getUnfilteredRecruitByID(TypicalIDs.ID_FIRST_RECRUIT);
        assertTrue(recruitToEdit.isPresent(), Messages.MESSAGE_INVALID_RECRUIT_ID);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_RECRUIT_SUCCESS,
                editCommand.formatDelta(recruitToEdit.get(), recruitToEdit.get()));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showRecruitAtID(model, TypicalIDs.ID_FIRST_RECRUIT);

        Recruit recruitInFilteredList = model.getFilteredRecruitList().get(INDEX_FIRST_RECRUIT.getZeroBased());
        Recruit initialRecruit = new RecruitBuilder(recruitInFilteredList).build();
        Recruit editedRecruit = new RecruitBuilder(recruitInFilteredList).withName(VALID_NAME_BOB).build();

        EditRecruitDescriptor descriptor = new EditRecruitDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(TypicalIDs.ID_FIRST_RECRUIT, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_RECRUIT_SUCCESS,
                editCommand.formatDelta(initialRecruit, editedRecruit));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setRecruit(model.getFilteredRecruitList().get(0), editedRecruit);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }


    @Test
    public void execute_invalidPersonIdUnfilteredList_failure() {
        UUID wrongID = UUID.randomUUID();
        EditRecruitDescriptor descriptor = new EditRecruitDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(wrongID, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_RECRUIT_ID);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIdFilteredList_failure() {
        showRecruitAtIndex(model, INDEX_FIRST_RECRUIT);
        UUID wrongID = UUID.randomUUID();
        EditCommand editCommand = new EditCommand(wrongID,
                new EditRecruitDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_RECRUIT_ID);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(TypicalIDs.ID_FIRST_RECRUIT, DESC_AMY);

        // same values -> returns true
        EditRecruitDescriptor copyDescriptor = new EditRecruitDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(TypicalIDs.ID_FIRST_RECRUIT, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(TypicalIDs.ID_SECOND_RECRUIT, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(TypicalIDs.ID_SECOND_RECRUIT, DESC_BOB)));
    }

    @Test
    public void toStringMethod() {
        UUID targetID = UUID.randomUUID();
        EditRecruitDescriptor editRecruitDescriptor = new EditRecruitDescriptor();
        EditCommand editCommand = new EditCommand(targetID, editRecruitDescriptor);
        String expected = EditCommand.class.getCanonicalName() + "{ID=" + targetID.toString()
                + ", editPersonDescriptor="
                + editRecruitDescriptor + "}";
        assertEquals(expected, editCommand.toString());
    }

}
