package seedu.address.logic.commands;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.combine;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_RECRUITS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.RecruitUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.recruit.Address;
import seedu.address.model.recruit.Description;
import seedu.address.model.recruit.Email;
import seedu.address.model.recruit.Name;
import seedu.address.model.recruit.Phone;
import seedu.address.model.recruit.Recruit;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing person in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";
    public static final String OPERATION_DESCRIPTOR = "modification of recruit:\n%s";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the recruit identified "
            + "by the index/uuide used in the displayed recruit list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX/UUID "
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
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_RECRUIT = "This recruit already exists in the address book.";
    public static final String MESSAGE_DUPLICATE_ATTRIBUTE = "Duplicate attribute is not allowed.";
    public static final String MESSAGE_MISSING_ATTRIBUTE = "Target attribute does not exist.";
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

    /**
     * @param index of the person in the filtered recruit list to edit
     * @param editRecruitDescriptor details to edit the recruit with
     * @param operation the type of edit operation to be performed
     */
    public EditCommand(Index index, EditRecruitDescriptor editRecruitDescriptor,
            EditRecruitDescriptor.EditRecruitOperation operation) {
        // TODO: check if this method is needed or can be streamlined to remove operation parameter
        requireNonNull(index);
        requireNonNull(editRecruitDescriptor);

        this.id = null;
        this.index = index;
        this.editRecruitDescriptor = new EditRecruitDescriptor(editRecruitDescriptor, operation);
    }


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Recruit recruitToEdit;

        if (id != null) {
            recruitToEdit = model.getAddressBook().getRecruitList().stream()
                    .filter(recruit -> recruit.getID().equals(id))
                    .findFirst()
                    .orElseThrow(() -> new CommandException(Messages.MESSAGE_INVALID_RECRUIT_ID));
        } else if (index != null) {
            List<Recruit> lastShownList = model.getFilteredRecruitList();
            if (index.getZeroBased() < 0 || index.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_RECRUIT_DISPLAYED_INDEX);
            }
            recruitToEdit = lastShownList.get(index.getZeroBased());
        } else {
            throw new CommandException(Messages.MESSAGE_NO_ID_OR_INDEX);
        }

        Recruit editedRecruit = createEditedRecruit(recruitToEdit, editRecruitDescriptor);

        if (!recruitToEdit.isSameRecruit(editedRecruit) && model.hasRecruit(editedRecruit)) {
            throw new CommandException(MESSAGE_DUPLICATE_RECRUIT);
        }

        model.setRecruit(recruitToEdit, editedRecruit);
        model.commitAddressBook(String.format(OPERATION_DESCRIPTOR, formatDelta(recruitToEdit, editedRecruit)));
        model.updateFilteredRecruitList(PREDICATE_SHOW_ALL_RECRUITS);

        return new CommandResult(String.format(MESSAGE_EDIT_RECRUIT_SUCCESS,
                formatDelta(recruitToEdit, editedRecruit)));
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
        verifyNoDuplicateAttributes(rec, desc);
        List<Name> updatedNames = combine(List.of(rec.getNames(), desc.getName().orElse(List.of())));
        List<Phone> updatedPhones = combine(List.of(rec.getPhones(), desc.getPhone().orElse(List.of())));
        List<Email> updatedEmails = combine(List.of(rec.getEmails(), desc.getEmail().orElse(List.of())));
        List<Address> updatedAddresses = combine(List.of(rec.getAddresses(), desc.getAddress().orElse(List.of())));
        Description updatedDescription = rec.getDescription()
                .appendDescription(desc.getDescription().orElse(new Description("")));
        Set<Tag> updatedTags = new HashSet<>(combine(List.of(rec.getTags(), desc.getTags().orElse(new HashSet<>()))));
        return new Recruit(rec.getID(), updatedNames, updatedPhones, updatedEmails, updatedAddresses,
                updatedDescription, updatedTags, false);
    }

    private static Recruit createEditedRecruitWithOverwrittenAttributes(Recruit rec, EditRecruitDescriptor desc)
            throws CommandException {
        List<Name> updatedNames = new ArrayList<>(desc.getName().orElse(rec.getNames()));
        List<Phone> updatedPhones = new ArrayList<>(desc.getPhone().orElse(rec.getPhones()));
        List<Email> updatedEmails = new ArrayList<>(desc.getEmail().orElse(rec.getEmails()));
        List<Address> updatedAddresses = new ArrayList<>(
                desc.getAddress().orElse(rec.getAddresses()));
        Description updatedDescription = desc.getDescription().orElse(rec.getDescription());
        Set<Tag> updatedTags = desc.getTags().orElse(rec.getTags());
        return new Recruit(rec.getID(), updatedNames, updatedPhones, updatedEmails, updatedAddresses,
                updatedDescription, updatedTags, false);
    }

    private static Recruit createEditedRecruitWithRemovedAttributes(Recruit rec, EditRecruitDescriptor desc)
            throws CommandException {
        verifyAllAttributesExist(rec, desc);
        List<Name> updatedNames = rec.getNames().stream()
                .filter(n -> !desc.getName().orElse(List.of()).contains(n)).toList();
        List<Phone> updatedPhones = rec.getPhones().stream()
                .filter(n -> !desc.getPhone().orElse(List.of()).contains(n)).toList();
        List<Email> updatedEmails = rec.getEmails().stream()
                .filter(n -> !desc.getEmail().orElse(List.of()).contains(n)).toList();
        List<Address> updatedAddresses = rec.getAddresses().stream()
                .filter(n -> !desc.getAddress().orElse(List.of()).contains(n)).toList();
        Description updatedDescription = desc.getDescription().map(d -> new Description(""))
                .orElse(rec.getDescription());
        Set<Tag> updatedTags = rec.getTags().stream()
                .filter(n -> !desc.getTags().orElse(new HashSet<>()).contains(n)).collect(Collectors.toSet());
        return new Recruit(rec.getID(), updatedNames, updatedPhones, updatedEmails, updatedAddresses,
                updatedDescription, updatedTags, false);
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

    private static void verifyNoDuplicateAttributes(Recruit r, EditRecruitDescriptor d) throws CommandException {
        assert d.operation == EditRecruitDescriptor.EditRecruitOperation.APPEND;

        Set<Name> names = new HashSet<>(r.getNames());
        Set<Phone> phones = new HashSet<>(r.getPhones());
        Set<Email> emails = new HashSet<>(r.getEmails());
        Set<Address> addresses = new HashSet<>(r.getAddresses());
        Set<Tag> tags = r.getTags();

        // TODO: add more detail regarding which specific value violates the duplicate constraint
        if (d.getName().isPresent() && d.getName().get().stream().anyMatch(names::contains)) {
            throw new CommandException(MESSAGE_DUPLICATE_ATTRIBUTE);
        }
        if (d.getPhone().isPresent() && d.getPhone().get().stream().anyMatch(phones::contains)) {
            throw new CommandException(MESSAGE_DUPLICATE_ATTRIBUTE);
        }
        if (d.getEmail().isPresent() && d.getEmail().get().stream().anyMatch(emails::contains)) {
            throw new CommandException(MESSAGE_DUPLICATE_ATTRIBUTE);
        }
        if (d.getAddress().isPresent() && d.getAddress().get().stream().anyMatch(addresses::contains)) {
            throw new CommandException(MESSAGE_DUPLICATE_ATTRIBUTE);
        }
        if (d.getTags().isPresent() && d.getTags().get().stream().anyMatch(tags::contains)) {
            throw new CommandException(MESSAGE_DUPLICATE_ATTRIBUTE);
        }
    }

    private static void verifyAllAttributesExist(Recruit r, EditRecruitDescriptor d) throws CommandException {
        assert d.operation == EditRecruitDescriptor.EditRecruitOperation.REMOVE;

        Set<Name> names = new HashSet<>(r.getNames());
        Set<Phone> phones = new HashSet<>(r.getPhones());
        Set<Email> emails = new HashSet<>(r.getEmails());
        Set<Address> addresses = new HashSet<>(r.getAddresses());
        Set<Tag> tags = r.getTags();

        // TODO: add more detail regarding which specific value violates the existence constraint
        if (d.getName().isPresent() && !d.getName().get().stream().allMatch(names::contains)) {
            throw new CommandException(MESSAGE_MISSING_ATTRIBUTE);
        }
        if (d.getPhone().isPresent() && !d.getPhone().get().stream().allMatch(phones::contains)) {
            throw new CommandException(MESSAGE_MISSING_ATTRIBUTE);
        }
        if (d.getEmail().isPresent() && !d.getEmail().get().stream().allMatch(emails::contains)) {
            throw new CommandException(MESSAGE_MISSING_ATTRIBUTE);
        }
        if (d.getAddress().isPresent() && !d.getAddress().get().stream().allMatch(addresses::contains)) {
            throw new CommandException(MESSAGE_MISSING_ATTRIBUTE);
        }
        if (d.getTags().isPresent() && !d.getTags().get().stream().allMatch(tags::contains)) {
            throw new CommandException(MESSAGE_MISSING_ATTRIBUTE);
        }
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
     * corresponding field value of the person.
     */
    public static class EditRecruitDescriptor {
        private UUID id;
        private List<Name> name;
        private List<Phone> phone;
        private List<Email> email;
        private List<Address> address;
        private Description description;
        private Set<Tag> tags;
        private EditRecruitOperation operation = EditRecruitOperation.OVERWRITE;

        public EditRecruitDescriptor() {}

        public EditRecruitDescriptor(EditRecruitOperation operation) {
            this.operation = operation;
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditRecruitDescriptor(EditRecruitDescriptor toCopy) {
            setID(toCopy.id);
            setNames(toCopy.name);
            setPhones(toCopy.phone);
            setEmails(toCopy.email);
            setAddresses(toCopy.address);
            setDescription(toCopy.description);
            setTags(toCopy.tags);
            setOperation(toCopy.operation);
        }

        /**
         * Copy constructor of an {@code EditRecruitDescriptor} with the specified {@code operation}.
         */
        public EditRecruitDescriptor(EditRecruitDescriptor toCopy, EditRecruitOperation operation) {
            this(toCopy);
            setOperation(operation);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(id, name, phone, email, address, description, tags);
        }

        public void setID(UUID id) {
            this.id = id;
        }
        public Optional<UUID> getID() {
            return Optional.ofNullable(id);
        }
        public void setNames(List<Name> name) {
            this.name = name;
        }

        public Optional<List<Name>> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhones(List<Phone> phone) {
            this.phone = phone;
        }

        public Optional<List<Phone>> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmails(List<Email> email) {
            this.email = email;
        }

        public Optional<List<Email>> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setAddresses(List<Address> address) {
            this.address = address;
        }

        public Optional<List<Address>> getAddress() {
            return Optional.ofNullable(address);
        }

        public void setDescription(Description description) {
            this.description = description;
        }

        public Optional<Description> getDescription() {
            return Optional.ofNullable(description);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        public EditRecruitOperation getOperation() {
            return operation;
        }

        public void setOperation(EditRecruitOperation op) {
            requireNonNull(op);
            this.operation = op;
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

            EditRecruitDescriptor otherEditRecruitDescriptor = (EditRecruitDescriptor) other;
            return Objects.equals(name, otherEditRecruitDescriptor.name)
                    && Objects.equals(phone, otherEditRecruitDescriptor.phone)
                    && Objects.equals(email, otherEditRecruitDescriptor.email)
                    && Objects.equals(address, otherEditRecruitDescriptor.address)
                    && Objects.equals(description, otherEditRecruitDescriptor.description)
                    && Objects.equals(tags, otherEditRecruitDescriptor.tags)
                    && Objects.equals(operation, otherEditRecruitDescriptor.operation);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("phone", phone)
                    .add("email", email)
                    .add("address", address)
                    .add("description", description)
                    .add("tags", tags)
                    .add("operation", operation)
                    .toString();
        }

        /**
         * Represents all the possible types of operation that an 'edit' command can do
         * to the attributes of a Recruit.
         */
        public static enum EditRecruitOperation {
            APPEND, OVERWRITE, REMOVE
        }
    }
}
