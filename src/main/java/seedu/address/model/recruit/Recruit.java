package seedu.address.model.recruit;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Recruit {

    // Identity fields
    private final UUID id;
    private final ArrayList<Name> names;
    private final ArrayList<Phone> phones;
    private final ArrayList<Email> emails;

    // Data fields
    private final ArrayList<Address> addresses;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field except for id must be present and not null.
     */
    public Recruit(Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, tags);
        this.names = new ArrayList<>(List.of(name));
        this.phones = new ArrayList<>(List.of(phone));
        this.emails = new ArrayList<>(List.of(email));
        this.addresses = new ArrayList<>(List.of(address));
        this.tags.addAll(tags);
        this.id = UUID.randomUUID();
    }

    /**
     * Every field except for id must be present and not null.
     */
    public Recruit(UUID id, Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, tags);
        this.names = new ArrayList<>(List.of(name));
        this.phones = new ArrayList<>(List.of(phone));
        this.emails = new ArrayList<>(List.of(email));
        this.addresses = new ArrayList<>(List.of(address));
        this.tags.addAll(tags);
        this.id = id;
    }

    /**
     * Every field except for id must be present and not null.
     */
    public Recruit(List<Name> name, List<Phone> phone, List<Email> email, List<Address> address, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, tags);
        this.names = new ArrayList<>(name);
        this.phones = new ArrayList<>(phone);
        this.emails = new ArrayList<>(email);
        this.addresses = new ArrayList<>(address);
        this.tags.addAll(tags);
        this.id = UUID.randomUUID();
    }

    /**
     * Every field must be present and not null.
     */
    public Recruit(UUID id, List<Name> name, List<Phone> phone, List<Email> email,
            List<Address> address, Set<Tag> tags) {
        requireAllNonNull(id, name, phone, email, address, tags);
        this.names = new ArrayList<>(name);
        this.phones = new ArrayList<>(phone);
        this.emails = new ArrayList<>(email);
        this.addresses = new ArrayList<>(address);
        this.tags.addAll(tags);
        this.id = id;
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
                .add("name", names)
                .add("phone", phones)
                .add("email", emails)
                .add("address", addresses)
                .add("tags", tags)
                .toString();
    }

}
