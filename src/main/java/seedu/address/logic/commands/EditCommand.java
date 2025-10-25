package seedu.address.logic.commands;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_RECRUITS;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.RecruitUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.recruit.Recruit;
import seedu.address.model.recruit.RecruitBuilder;
import seedu.address.model.recruit.exceptions.FieldElementAlreadyExistsException;

/**
 * Edits the details of an existing person in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";
    public static final String OPERATION_DESCRIPTOR = "modification of recruit:\n%s";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the recruit identified "
            + "by the index number used in the displayed recruit list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_RECRUIT_SUCCESS = "Edited Recruit:\n%1$s";
    public static final String MESSAGE_NO_FIELD_PROVIDED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_RECRUIT = "This recruit already exists in the address book.";
    public static final String MESSAGE_DUPLICATE_ATTRIBUTE = "The following %s(s) are already present.";
    public static final String MESSAGE_MISSING_ATTRIBUTE = "The following %s(s) do not exist.";
    public static final String MESSAGE_INVALID_OPERATION = "Multiple edit operation type is not allowed.";

    private static final String DELTA_SEP = " -> "; // separator to show modified values in success message

    private final UUID id; // TODO: use Optional
    private final Index index; // TODO: use Optional
    private final EditRecruitDescriptor editRecruitDescriptor;

    /**
     * @param id of the person in the filtered recruit list to edit
     * @param editRecruitDescriptor details to edit the recruit with
     */
    public EditCommand(UUID id, EditRecruitDescriptor editRecruitDescriptor) {
        requireNonNull(id);
        requireNonNull(editRecruitDescriptor);

        this.id = id;
        this.index = null;
        this.editRecruitDescriptor = new EditRecruitDescriptor(editRecruitDescriptor);
    }

    /**
     * @param index of the person in the filtered recruit list to edit
     * @param editRecruitDescriptor details to edit the recruit with
     */
    public EditCommand(Index index, EditRecruitDescriptor editRecruitDescriptor) {
        // TODO: check if this method is needed or can be streamlined to remove operation parameter
        requireNonNull(index);
        requireNonNull(editRecruitDescriptor);

        this.id = null;
        this.index = index;
        this.editRecruitDescriptor = new EditRecruitDescriptor(editRecruitDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // handle edit by index
        if (id == null) {
            Recruit recruitToEdit = model.getFilteredRecruitList().get(index.getZeroBased());
            Recruit editedRecruit = createEditedRecruit(recruitToEdit, editRecruitDescriptor);

            model.setRecruit(recruitToEdit, editedRecruit);
            model.commitAddressBook(String.format(OPERATION_DESCRIPTOR, formatDelta(recruitToEdit, editedRecruit)));
            model.updateFilteredRecruitList(PREDICATE_SHOW_ALL_RECRUITS);
            return new CommandResult(String.format(
                    MESSAGE_EDIT_RECRUIT_SUCCESS, formatDelta(recruitToEdit, editedRecruit)));
        }

        // handle edit by id
        List<Recruit> recruits = model.getAddressBook().getRecruitList();
        Optional<Recruit> recruitToEdit = recruits.stream()
                .filter(recruit -> recruit.getID().equals(this.id))
                .findFirst();

        if (recruitToEdit.isEmpty()) {
            throw new CommandException(Messages.MESSAGE_INVALID_RECRUIT_ID);
        }

        Recruit editedRecruit = createEditedRecruit(recruitToEdit.get(), editRecruitDescriptor);
        if (!recruitToEdit.get().isSameRecruit(editedRecruit) && model.hasRecruit(editedRecruit)) {
            throw new CommandException(MESSAGE_DUPLICATE_RECRUIT);
        }
        model.setRecruit(recruitToEdit.get(), editedRecruit);

        model.commitAddressBook(String.format(OPERATION_DESCRIPTOR, formatDelta(recruitToEdit.get(), editedRecruit)));
        model.updateFilteredRecruitList(PREDICATE_SHOW_ALL_RECRUITS);
        return new CommandResult(String.format(
                MESSAGE_EDIT_RECRUIT_SUCCESS, formatDelta(recruitToEdit.get(), editedRecruit)));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Recruit createEditedRecruit(Recruit recruitToEdit, EditRecruitDescriptor descriptor)
            throws CommandException {
        return switch (descriptor.operation) {
        case APPEND -> createEditedRecruitWithAppendedAttributes(recruitToEdit, descriptor);
        case REMOVE -> createEditedRecruitWithRemovedAttributes(recruitToEdit, descriptor);
        case OVERWRITE -> createEditedRecruitWithOverwrittenAttributes(recruitToEdit, descriptor);
        };
    }

    private static Recruit createEditedRecruitWithAppendedAttributes(Recruit rec, EditRecruitDescriptor desc)
            throws CommandException {
        try {
            return new RecruitBuilder(rec).append(desc).build();
        } catch (FieldElementAlreadyExistsException e) {
            throw new CommandException(String.format(MESSAGE_DUPLICATE_ATTRIBUTE,
                    e.getFieldType(), e.getDuplicatedElements()));
        }
    }

    private static Recruit createEditedRecruitWithOverwrittenAttributes(Recruit rec, EditRecruitDescriptor desc)
            throws CommandException {
        return new RecruitBuilder(rec).override(desc).build();
    }

    private static Recruit createEditedRecruitWithRemovedAttributes(Recruit rec, EditRecruitDescriptor desc)
            throws CommandException {
        try {
            return new RecruitBuilder(rec).remove(desc).build();
        } catch (FieldElementAlreadyExistsException e) {
            throw new CommandException(String.format(MESSAGE_MISSING_ATTRIBUTE,
                    e.getFieldType(), e.getDuplicatedElements()));
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        EditCommand otherEditCommand = (EditCommand) other;
        return ((!isNull(id) && id.equals(otherEditCommand.id))
                || (!isNull(index) && index.equals(otherEditCommand.index)))
                && editRecruitDescriptor.equals(otherEditCommand.editRecruitDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("ID", id)
                .add("editPersonDescriptor", editRecruitDescriptor)
                .toString();
    }

    String formatDelta(Recruit oldRecruit, Recruit newRecruit) {
        final StringBuilder builder = new StringBuilder();
        builder.append("Name: ").append(oldRecruit.getNames())
                .append(RecruitUtil.hasSameName(oldRecruit, newRecruit)
                        ? "" : DELTA_SEP + newRecruit.getNames().toString())
                .append("\nPhone: ").append(oldRecruit.getPhones())
                .append(RecruitUtil.hasSamePhone(oldRecruit, newRecruit)
                        ? "" : DELTA_SEP + newRecruit.getPhones())
                .append("\nEmail: ").append(oldRecruit.getEmails())
                .append(RecruitUtil.hasSameEmail(oldRecruit, newRecruit)
                        ? "" : DELTA_SEP + newRecruit.getEmails())
                .append("\nAddress: ").append(oldRecruit.getAddresses())
                .append(RecruitUtil.hasSameAddress(oldRecruit, newRecruit)
                        ? "" : DELTA_SEP + newRecruit.getAddresses())
                .append("\nTags: ").append(oldRecruit.getTags())
                .append(RecruitUtil.hasSameTags(oldRecruit, newRecruit)
                        ? "" : DELTA_SEP + newRecruit.getTags());
        return builder.toString();
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person. Directly extends {@link RecruitBuilder}
     */
    public static class EditRecruitDescriptor extends RecruitBuilder {

        private final EditCommand.EditOperation operation;

        /**
         * Creates an EditRecruitDescriptor assigned with the following operation.
         *
         * @param op the operation of to perform during edit.
         */
        public EditRecruitDescriptor(EditOperation op) {
            super();

            requireNonNull(op);
            this.operation = op;
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditRecruitDescriptor(EditRecruitDescriptor toCopy) {
            super(toCopy);
            this.operation = toCopy.operation;
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditRecruitDescriptor)) {
                return false;
            }

            EditRecruitDescriptor otherBuilder = (EditRecruitDescriptor) other;
            return super.equals(otherBuilder) && this.operation == otherBuilder.operation;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", names)
                    .add("phone", phones)
                    .add("email", emails)
                    .add("address", addresses)
                    .add("description", description)
                    .add("tags", tags)
                    .add("operation", operation)
                    .toString();
        }
    }

    /**
     * Represents all the possible types of operation that an 'edit' command can do
     * to the attributes of a Recruit.
     */
    public static enum EditOperation {
        APPEND, OVERWRITE, REMOVE
    }
}
