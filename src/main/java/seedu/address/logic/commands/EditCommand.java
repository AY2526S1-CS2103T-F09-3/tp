package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_RECRUITS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.recruit.Address;
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

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the recruit identified "
            + "by the index number used in the displayed recruit list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_RECRUIT_SUCCESS = "Edited Recruit: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_RECRUIT = "This recruit already exists in the address book.";
    private static final String DELTA_FORMAT = " -> %s";

    private final Index index;
    private final EditRecruitDescriptor editRecruitDescriptor;

    /**
     * @param index of the person in the filtered recruit list to edit
     * @param editRecruitDescriptor details to edit the recruit with
     */
    public EditCommand(Index index, EditRecruitDescriptor editRecruitDescriptor) {
        requireNonNull(index);
        requireNonNull(editRecruitDescriptor);

        this.index = index;
        this.editRecruitDescriptor = new EditRecruitDescriptor(editRecruitDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Recruit> lastShownList = model.getFilteredRecruitList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_RECRUIT_DISPLAYED_INDEX);
        }

        Recruit recruitToEdit = lastShownList.get(index.getZeroBased());
        Recruit editedRecruit = createEditedRecruit(recruitToEdit, editRecruitDescriptor);

        if (!recruitToEdit.isSameRecruit(editedRecruit) && model.hasRecruit(editedRecruit)) {
            throw new CommandException(MESSAGE_DUPLICATE_RECRUIT);
        }

        model.setRecruit(recruitToEdit, editedRecruit);
        model.updateFilteredRecruitList(PREDICATE_SHOW_ALL_RECRUITS);
        return new CommandResult(String.format(
                MESSAGE_EDIT_RECRUIT_SUCCESS, formatDelta(recruitToEdit, editRecruitDescriptor)));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Recruit createEditedRecruit(Recruit recruitToEdit, EditRecruitDescriptor editRecruitDescriptor) {
        assert recruitToEdit != null;
        UUID updatedId = editRecruitDescriptor.getID().orElse(recruitToEdit.getID());
        List<Name> updatedName = editRecruitDescriptor.getName().orElse(recruitToEdit.getNames());
        List<Phone> updatedPhone = editRecruitDescriptor.getPhone().orElse(recruitToEdit.getPhones());
        List<Email> updatedEmail = editRecruitDescriptor.getEmail().orElse(recruitToEdit.getEmails());
        List<Address> updatedAddress = editRecruitDescriptor.getAddress().orElse(recruitToEdit.getAddresses());
        Set<Tag> updatedTags = editRecruitDescriptor.getTags().orElse(recruitToEdit.getTags());

        return new Recruit(updatedId, updatedName, updatedPhone, updatedEmail, updatedAddress, updatedTags);
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
        return index.equals(otherEditCommand.index)
                && editRecruitDescriptor.equals(otherEditCommand.editRecruitDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editPersonDescriptor", editRecruitDescriptor)
                .toString();
    }

    String formatDelta(Recruit person, EditRecruitDescriptor delta) {
        final StringBuilder builder = new StringBuilder();
        builder.append(person.getName())
                .append(delta.getName().map(n -> String.format(DELTA_FORMAT, n)).orElse(""))
                .append("; Phone: ")
                .append(person.getPhone())
                .append(delta.getPhone().map(p -> String.format(DELTA_FORMAT, p)).orElse(""))
                .append("; Email: ")
                .append(person.getEmail())
                .append(delta.getEmail().map(e -> String.format(DELTA_FORMAT, e)).orElse(""))
                .append("; Address: ")
                .append(person.getAddress())
                .append(delta.getAddress().map(a -> String.format(DELTA_FORMAT, a)).orElse(""))
                .append("; Tags: ");
        person.getTags().forEach(builder::append);

        Optional<Set<Tag>> deltaTags = delta.getTags();
        deltaTags.ifPresent(tags -> {
            builder.append(" -> ");
            tags.forEach(builder::append);
        });
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
        private Set<Tag> tags;

        public EditRecruitDescriptor() {}

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
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(id, name, phone, email, address, tags);
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
                    && Objects.equals(tags, otherEditRecruitDescriptor.tags);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("phone", phone)
                    .add("email", email)
                    .add("address", address)
                    .add("tags", tags)
                    .toString();
        }
    }
}
