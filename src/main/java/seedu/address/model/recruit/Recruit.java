package seedu.address.model.recruit;

import static seedu.address.commons.util.CollectionUtil.requireAllNonBlankString;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.commons.util.CollectionUtil.requireNonEmpty;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Recruit {

    private boolean isArchived;

    // Identity fields
    private final UUID id;
    private final List<Name> names;
    private final List<Phone> phones;
    private final List<Email> emails;

    // Data fields
    private final List<Address> addresses;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Recruit(Name name, Phone phone, Email email, Address address, Set<Tag> tags, boolean archive) {
        this(UUID.randomUUID(), name, phone, email, address, tags, archive);
    }

    /**
     * Every field must be present and not null.
     */
    public Recruit(UUID id, Name name, Phone phone, Email email, Address address, Set<Tag> tags, boolean archive) {
        requireAllNonNull(id, name, phone, email, address, tags);
        requireAllNonBlankString(List.of(name, phone, email, address).stream().map(Object::toString).toList());
        requireAllNonBlankString(tags.stream().map(t -> t.tagName).toList());

        this.names = List.of(name);
        this.phones = List.of(phone);
        this.emails = List.of(email);
        this.addresses = List.of(address);
        this.tags.addAll(tags);
        this.id = id;
        this.isArchived = archive;
    }

    /**
     * Every field must be present and not null.
     */
    public Recruit(List<Name> names, List<Phone> phones, List<Email> emails, List<Address> addresses, Set<Tag> tags,
                   boolean archive) {
        this(UUID.randomUUID(), names, phones, emails, addresses, tags, archive);
    }

    /**
     * Every field must be present and not null.
     */
    public Recruit(UUID id, List<Name> names, List<Phone> phones, List<Email> emails,
            List<Address> addresses, Set<Tag> tags, boolean archive) {
        requireAllNonNull(id, names, phones, emails, addresses, tags);
        requireNonEmpty(names);
        requireAllNonBlankString(Stream.of(names, phones, emails, addresses)
                .flatMap(List::stream).map(Object::toString).toList());
        requireAllNonBlankString(tags.stream().map(t -> t.tagName).toList());

        this.names = names;
        this.phones = phones;
        this.emails = emails;
        this.addresses = addresses;
        this.tags.addAll(tags);
        this.id = id;
        this.isArchived = archive;
    }

    public UUID getID() {
        return id;
    }

    public Name getName() {
        return names.get(0);
    }

    public List<Name> getNames() {
        return this.names;
    }

    public Phone getPhone() {
        return phones.get(0);
    }

    public List<Phone> getPhones() {
        return this.phones;
    }

    public Email getEmail() {
        return emails.get(0);
    }

    public List<Email> getEmails() {
        return this.emails;
    }

    public Address getAddress() {
        return addresses.get(0);
    }

    public List<Address> getAddresses() {
        return this.addresses;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns boolean representing whether recruit entry is archived or not
     */
    public boolean isArchived() {
        return this.isArchived;
    }

    /**
     * Returns true if both persons have the same id.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameRecruit(Recruit otherRecruit) {
        if (otherRecruit == this) {
            return true;
        }

        return otherRecruit != null
                && otherRecruit.getID().equals(getID());
    }

    /**
     * Returns true if both recruits have the same id and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Recruit)) {
            return false;
        }

        Recruit otherRecruit = (Recruit) other;
        return id.equals(otherRecruit.id)
                && names.equals(otherRecruit.names)
                && phones.equals(otherRecruit.phones)
                && emails.equals(otherRecruit.emails)
                && addresses.equals(otherRecruit.addresses)
                && tags.equals(otherRecruit.tags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(names, phones, emails, addresses, tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("id", id)
                .add("names", names)
                .add("phones", phones)
                .add("emails", emails)
                .add("addresses", addresses)
                .add("tags", tags)
                .toString();
    }

}
