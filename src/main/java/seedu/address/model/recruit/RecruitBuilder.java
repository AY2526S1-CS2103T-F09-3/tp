package seedu.address.model.recruit;

import java.util.List;
import java.util.Objects;
import java.util.TreeSet;
import java.util.UUID;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.RecruitUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.recruit.exceptions.FieldElementAlreadyExistsException;
import seedu.address.model.recruit.exceptions.FieldElementNotFoundException;
import seedu.address.model.recruit.exceptions.IllegalRecruitBuilderActionException;
import seedu.address.model.recruit.exceptions.InvalidRecruitException;
import seedu.address.model.tag.Tag;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Builder class to build a Recruit.
 * Guarantees:
 * <ul>
 *     <li>A new {@code Recruit} with valid fields.</li>
 *     <li>The created{@code Recruit} has to have at least 1 name</li>
 *     <li>If a UUID is not provided, a new one is randomly generated</li>
 * </ul>
 */
public class RecruitBuilder {

    protected UUID id;

    protected TreeSet<Name> names;
    protected TreeSet<Phone> phones;
    protected TreeSet<Email> emails;
    protected TreeSet<Address> addresses;

    protected Description description;
    protected TreeSet<Tag> tags;

    protected boolean isArchived;

    /**
     * Constructor for Builder
     */
    public RecruitBuilder() {

    }

    /**
     * Constructor for Builder that builds upon an existing Recruit
     */
    public RecruitBuilder(Recruit recruit) {
        this.id = recruit.getID();
        this.names = new TreeSet<>(recruit.getNames());
        this.phones = new TreeSet<>(recruit.getPhones());
        this.addresses = new TreeSet<>(recruit.getAddresses());
        this.emails = new TreeSet<>(recruit.getEmails());
        this.description = new Description(recruit.getDescription());
        this.tags = new TreeSet<>(recruit.getTags());
        this.isArchived = recruit.isArchived();
    }

    /**
     * Copy constructor for Builder
     *
     * @param toCopy The other builder to copy
     */
    public RecruitBuilder(RecruitBuilder toCopy) {
        requireNonNull(toCopy, "Cannot copy a null RecruitBuilder");

        this.id = toCopy.id;
        this.names = toCopy.names == null ? null : new TreeSet<>(toCopy.names);
        this.phones = toCopy.phones == null ? null : new TreeSet<>(toCopy.phones);
        this.emails = toCopy.emails == null ? null : new TreeSet<>(toCopy.emails);
        this.addresses = toCopy.addresses == null ? null : new TreeSet<>(toCopy.addresses);
        this.description = toCopy.description == null ? null : new Description(toCopy.description);
        this.tags = toCopy.tags == null ? null : new TreeSet<>(toCopy.tags);
        this.isArchived = toCopy.isArchived;
    }

    /**
     * Sets the UUID if the provided id is not null.
     *
     * @param id the UUID to set
     * @return the Builder instance with the updated UUID
     */
    public RecruitBuilder setUuid(UUID id) {
        if (id != null) {
            this.id = id;
        }
        return this;
    }

    /**
     * Sets names to be a Set with this individual name
     *
     * @param name the name to set
     * @return this Builder instance with names updated
     */
    public RecruitBuilder withName(Name name) {
        if (name != null) {
            this.names = new TreeSet<Name>();
            this.names.add(name);
        }
        return this;
    }

    /**
     * Sets names to be a Set consisting of all names in the provided list.
     *
     * @param names the list of names to set
     * @return this Builder instance with names updated
     */
    public RecruitBuilder withNames(List<Name> names) {
        if (names != null) {
            this.names = new TreeSet<>(names);
        }
        return this;
    }

    /**
     * Appends the list of names to the current set of names in the {@code Builder}
     *
     * @param namesToAdd List of names to append
     * @return the Builder with the appended names
     */
    public RecruitBuilder appendNames(List<Name> namesToAdd) {
        if (namesToAdd == null) {
            return this;
        }

        List<Name> duplicatedNames = CollectionUtil.addListToSet(this.names, namesToAdd);

        if (!duplicatedNames.isEmpty()) {
            throw new FieldElementAlreadyExistsException("name",
                    duplicatedNames.stream().map(Name::toString).toList());
        }

        return this;
    }

    /**
     * Removes the list of names from the current set of names in the {@code Builder}
     *
     * @param namesToRemove List of names to remove
     * @return the Builder with the removed names
     */
    public RecruitBuilder removeNames(List<Name> namesToRemove) {
        if (this.names == null && namesToRemove != null && !namesToRemove.isEmpty()) {
            throw new IllegalRecruitBuilderActionException(
                    "Cannot remove names from a RecruitBuilder for which names has not been set");
        }

        List<Name> missingNames = CollectionUtil.removeListFromSet(this.names, namesToRemove);

        if (!missingNames.isEmpty()) {
            throw new FieldElementNotFoundException("name",
                    missingNames.stream().map(Name::toString).toList());
        }

        return this;
    }

    /**
     * Sets phones to be a Set with this individual phone number.
     *
     * @param phone the phone number to set
     * @return this Builder instance with phones updated
     */
    public RecruitBuilder withPhone(Phone phone) {
        if (phone != null) {
            this.phones = new TreeSet<>();
            this.phones.add(phone);
        }
        return this;
    }

    /**
     * Sets phones to be a Set consisting of all phone numbers in the provided list.
     *
     * @param phones the list of phone numbers to set
     * @return this Builder instance with phones updated
     */
    public RecruitBuilder withPhones(List<Phone> phones) {
        if (phones != null) {
            this.phones = new TreeSet<>(phones);
        }
        return this;
    }

    /**
     * Appends the list of phone numbers to the current set of phone numbers in the {@code Builder},
     * only if the provided list is not null.
     *
     * @param phonesToAdd List of phone numbers to append
     * @return the Builder with the appended phone numbers
     */
    public RecruitBuilder appendPhones(List<Phone> phonesToAdd) {
        if (phonesToAdd == null) {
            return this;
        }

        List<Phone> duplicatedPhones = CollectionUtil.addListToSet(this.phones, phonesToAdd);

        if (!duplicatedPhones.isEmpty()) {
            throw new FieldElementAlreadyExistsException("phone",
                    duplicatedPhones.stream().map(Phone::toString).toList());
        }

        return this;
    }

    /**
     * Removes the list of phone numbers from the current set of phone numbers in the {@code Builder}.
     *
     * @param phonesToRemove List of phone numbers to remove
     * @return the Builder with the removed phone numbers
     */
    public RecruitBuilder removePhones(List<Phone> phonesToRemove) {
        if (this.phones == null && phonesToRemove != null && !phonesToRemove.isEmpty()) {
            throw new IllegalRecruitBuilderActionException(
                    "Cannot remove phone numbers from a RecruitBuilder for which phones have not been set");
        }

        List<Phone> missingPhones = CollectionUtil.removeListFromSet(this.phones, phonesToRemove);

        if (!missingPhones.isEmpty()) {
            throw new FieldElementNotFoundException("phone",
                    missingPhones.stream().map(Phone::toString).toList());
        }

        return this;
    }

    /**
     * Sets emails to be a Set with this individual email.
     *
     * @param email the email to set
     * @return this Builder instance with emails updated
     */
    public RecruitBuilder withEmail(Email email) {
        if (email != null) {
            this.emails = new TreeSet<>();
            this.emails.add(email);
        }
        return this;
    }

    /**
     * Sets emails to be a Set consisting of all emails in the provided list.
     *
     * @param emails the list of emails to set
     * @return this Builder instance with emails updated
     */
    public RecruitBuilder withEmails(List<Email> emails) {
        if (emails != null) {
            this.emails = new TreeSet<>(emails);
        }
        return this;
    }

    /**
     * Appends the list of emails to the current set of emails in the {@code Builder},
     * only if the provided list is not null.
     *
     * @param emailsToAdd List of emails to append
     * @return the Builder with the appended emails
     */
    public RecruitBuilder appendEmails(List<Email> emailsToAdd) {
        if (emailsToAdd == null) {
            return this;
        }

        List<Email> duplicatedEmails = CollectionUtil.addListToSet(this.emails, emailsToAdd);

        if (!duplicatedEmails.isEmpty()) {
            throw new FieldElementAlreadyExistsException("email",
                    duplicatedEmails.stream().map(Email::toString).toList());
        }

        return this;
    }

    /**
     * Removes the list of emails from the current set of emails in the {@code Builder}.
     *
     * @param emailsToRemove List of emails to remove
     * @return the Builder with the removed emails
     */
    public RecruitBuilder removeEmails(List<Email> emailsToRemove) {
        if (this.emails == null && emailsToRemove != null && !emailsToRemove.isEmpty()) {
            throw new IllegalRecruitBuilderActionException(
                    "Cannot remove emails from a RecruitBuilder for which emails have not been set");
        }

        List<Email> missingEmails = CollectionUtil.removeListFromSet(this.emails, emailsToRemove);

        if (!missingEmails.isEmpty()) {
            throw new FieldElementNotFoundException("email",
                    missingEmails.stream().map(Email::toString).toList());
        }

        return this;
    }

    /**
     * Sets addresses to be a Set with this individual address.
     *
     * @param address the address to set
     * @return this Builder instance with addresses updated
     */
    public RecruitBuilder withAddress(Address address) {
        if (address != null) {
            this.addresses = new TreeSet<>();
            this.addresses.add(address);
        }
        return this;
    }

    /**
     * Sets addresses to be a Set consisting of all addresses in the provided list.
     *
     * @param addresses the list of addresses to set
     * @return this Builder instance with addresses updated
     */
    public RecruitBuilder withAddresses(List<Address> addresses) {
        if (addresses != null) {
            this.addresses = new TreeSet<>(addresses);
        }
        return this;
    }

    /**
     * Appends the list of addresses to the current set of addresses in the {@code Builder},
     * only if the provided list is not null.
     *
     * @param addressesToAdd List of addresses to append
     * @return the Builder with the appended addresses
     */
    public RecruitBuilder appendAddresses(List<Address> addressesToAdd) {
        if (addressesToAdd == null) {
            return this;
        }

        List<Address> duplicatedAddresses = CollectionUtil.addListToSet(this.addresses, addressesToAdd);

        if (!duplicatedAddresses.isEmpty()) {
            throw new FieldElementAlreadyExistsException("address",
                    duplicatedAddresses.stream().map(Address::toString).toList());
        }

        return this;
    }

    /**
     * Removes the list of addresses from the current set of addresses in the {@code Builder}.
     *
     * @param addressesToRemove List of addresses to remove
     * @return the Builder with the removed addresses
     */
    public RecruitBuilder removeAddresses(List<Address> addressesToRemove) {
        if (this.addresses == null && addressesToRemove != null && !addressesToRemove.isEmpty()) {
            throw new IllegalRecruitBuilderActionException(
                    "Cannot remove addresses from a RecruitBuilder for which addresses have not been set");
        }

        List<Address> missingAddresses = CollectionUtil.removeListFromSet(this.addresses, addressesToRemove);

        if (!missingAddresses.isEmpty()) {
            throw new FieldElementNotFoundException("address",
                    missingAddresses.stream().map(Address::toString).toList());
        }

        return this;
    }

    /**
     * Sets the description if the provided description is not null.
     *
     * @param description the description to set
     * @return the Builder instance with the updated description
     */
    public RecruitBuilder withDescription(Description description) {
        if (description != null) {
            this.description = new Description(description);
        }
        return this;
    }

    /**
     * Appends a different description to the current description stored in the {@code Builder},
     * only if the provided description is not null.
     * Uses {@link Description#appendDescription} to perform this operation.
     *
     * @param description The description to append
     * @return the Builder with a new Description that is the previous description and the provided
     *         descriptions combined.
     */
    public RecruitBuilder appendDescription(Description description) {
        if (description != null) {
            if (this.description == null) {
                this.description = new Description(description);
            } else {
                this.description.appendDescription(description);
            }
        }
        return this;
    }

    /**
     * Sets tags to be a Set with this individual tag.
     *
     * @param tag the tag to set
     * @return this Builder instance with tags updated
     */
    public RecruitBuilder withTag(Tag tag) {
        if (tag != null) {
            this.tags = new TreeSet<>();
            this.tags.add(tag);
        }
        return this;
    }

    /**
     * Sets tags to be a Set consisting of all tags in the provided list.
     *
     * @param tags the list of tags to set
     * @return this Builder instance with tags updated
     */
    public RecruitBuilder withTags(List<Tag> tags) {
        if (tags != null) {
            this.tags = new TreeSet<>(tags);
        }
        return this;
    }

    /**
     * Appends the list of tags to the current set of tags in the {@code Builder},
     * only if the provided list is not null.
     *
     * @param tagsToAdd List of tags to append
     * @return the Builder with the appended tags
     */
    public RecruitBuilder appendTags(List<Tag> tagsToAdd) {
        if (tagsToAdd == null) {
            return this;
        }

        List<Tag> duplicatedTags = CollectionUtil.addListToSet(this.tags, tagsToAdd);

        if (!duplicatedTags.isEmpty()) {
            throw new FieldElementAlreadyExistsException("tag",
                    duplicatedTags.stream().map(Tag::toString).toList());
        }

        return this;
    }

    /**
     * Removes the list of tags from the current set of tags in the {@code Builder}.
     *
     * @param tagsToRemove List of tags to remove
     * @return the Builder with the removed tags
     */
    public RecruitBuilder removeTags(List<Tag> tagsToRemove) {
        if (this.tags == null && tagsToRemove != null && !tagsToRemove.isEmpty()) {
            throw new IllegalRecruitBuilderActionException(
                    "Cannot remove tags from a RecruitBuilder for which tags have not been set");
        }

        List<Tag> missingTags = CollectionUtil.removeListFromSet(this.tags, tagsToRemove);

        if (!missingTags.isEmpty()) {
            throw new FieldElementNotFoundException("tag",
                    missingTags.stream().map(Tag::toString).toList());
        }

        return this;
    }


    public RecruitBuilder withArchivalState(boolean isArchived) {
        this.isArchived = isArchived;
        return this;
    }

    /**
     * Overrides the fields in the current {@code RecruitBuilder} with the fields in the other RecruitBuilder.
     * If the other RecruitBuilder has fields that have not been modified, ignore those fields.
     *
     * @param other the other builder to override the fields of this builder using.
     * @return this builder but with fields that have been overridden.
     */
    public RecruitBuilder override(RecruitBuilder other) {
        this.id = other.id == null ? this.id : other.id;
        this.phones = other.phones == null ? this.phones : other.phones;
        this.emails = other.emails == null ? this.emails : other.emails;
        this.addresses = other.addresses == null ? this.addresses : other.addresses;
        this.description = other.description == null ? this.description : other.description;
        this.tags = other.tags == null ? this.tags : other.tags;
        return this;
    }

    /**
     * Overrides the fields in the current {@code RecruitBuilder} with the fields in the other RecruitBuilder.
     * If the other RecruitBuilder has fields that have not been modified, ignore those fields.
     *
     * @param other the other builder to override the fields of this builder using.
     * @return this builder but with fields that have been overridden.
     */
    public RecruitBuilder append(RecruitBuilder other) {
        this.appendNames(other.names.stream().toList());
        this.appendPhones(other.phones.stream().toList());
        this.appendEmails(other.emails.stream().toList());
        this.appendAddresses(other.addresses.stream().toList());
        this.appendDescription(other.description);
        this.appendTags(other.tags.stream().toList());

        return this;
    }

    /**
     * Overrides the fields in the current {@code RecruitBuilder} with the fields in the other RecruitBuilder.
     * If the other RecruitBuilder has fields that have not been modified, ignore those fields.
     *
     * @param other the other builder to override the fields of this builder using.
     * @return this builder but with fields that have been overridden.
     */
    public RecruitBuilder remove(RecruitBuilder other) {
        this.removeNames(other.names.stream().toList());
        this.removePhones(other.phones.stream().toList());
        this.removeEmails(other.emails.stream().toList());
        this.removeAddresses(other.addresses.stream().toList());
        this.description = other.description == null ? this.description : Description.createEmptyDescription();
        this.appendTags(other.tags.stream().toList());

        return this;
    }

    /**
     * Checks if the {@code Builder} has been modified before.
     * A builder has been modified if any of the "set" or "append" methods have been called.
     *
     * @return boolean indicating whether the Builder has been modified.
     */
    public boolean hasBeenModified() {
        return CollectionUtil.isAnyNonNull(id, names, phones, emails, addresses, description, tags);
    }

    /**
     * Builds a {@code Recruit} using the attributes specified in the {@code Builder}
     *
     * @return the recruit with matching builder attributes
     * @throws InvalidRecruitException if the user attempts to build a recruit without a valid name.
     */
    public Recruit build() {
        RecruitUtil.requireNonEmptyField(names);

        this.id = this.id == null ? UUID.randomUUID() : this.id;
        this.phones = this.phones == null ? new TreeSet<>() : this.phones;
        this.emails = this.emails == null ? new TreeSet<>() : this.emails;
        this.addresses = this.addresses == null ? new TreeSet<>() : this.addresses;
        this.description = this.description == null ? Description.createEmptyDescription() : this.description;
        this.tags = this.tags == null ? new TreeSet<>() : this.tags;
        return new Recruit(this);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RecruitBuilder)) {
            return false;
        }

        RecruitBuilder otherBuilder = (RecruitBuilder) other;
        return Objects.equals(names, otherBuilder.names)
                && Objects.equals(phones, otherBuilder.phones)
                && Objects.equals(emails, otherBuilder.emails)
                && Objects.equals(addresses, otherBuilder.addresses)
                && Objects.equals(description, otherBuilder.description)
                && Objects.equals(tags, otherBuilder.tags);
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
                .toString();
    }
}
