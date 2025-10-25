package seedu.address.model.recruit;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Recruit {

    private final boolean isArchived;

    // Identity fields
    private final UUID id;
    private final TreeSet<Name> names;

    // Data fields
    private final TreeSet<Phone> phones;
    private final TreeSet<Email> emails;
    private final TreeSet<Address> addresses;
    private final Description description;
    private final TreeSet<Tag> tags;

    protected Recruit(RecruitBuilder builder) {
        this.id = builder.id;
        this.names = builder.names;
        this.phones = builder.phones;
        this.emails = builder.emails;
        this.addresses = builder.addresses;
        this.description = builder.description;
        this.tags = builder.tags;
        this.isArchived = builder.isArchived;
    }

    public UUID getID() {
        return id;
    }

    public Name getName() {
        return names.first();
    }

    public List<Name> getNames() {
        return this.names.stream().toList();
    }

    public Phone getPhone() {
        return phones.first();
    }

    public List<Phone> getPhones() {
        return this.phones.stream().toList();
    }

    public Email getEmail() {
        return emails.first();
    }

    public List<Email> getEmails() {
        return this.emails.stream().toList();
    }

    public Description getDescription() {
        return this.description;
    }

    public Address getAddress() {
        return addresses.first();
    }

    public List<Address> getAddresses() {
        return this.addresses.stream().toList();
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons have the same fields except id.
     * Returns boolean representing whether recruit entry is archived or not
     */
    public boolean isArchived() {
        return this.isArchived;
    }

    /**
     * Sets the {@code Recruit} as "archived".
     */
    public Recruit archive() {
        return new RecruitBuilder(this).withArchivalState(true).build();
    }

    /**
     * Sets the {@code Recruit} as "not archived". This is the default state.
     */
    public Recruit unarchive() {
        return new RecruitBuilder(this).withArchivalState(false).build();
    }

    /**
     * Returns true if both persons have the same fields except id.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameRecruit(Recruit otherRecruit) {
        if (otherRecruit == this) {
            return true;
        }

        return otherRecruit != null
                && names.equals(otherRecruit.names)
                && phones.equals(otherRecruit.phones)
                && emails.equals(otherRecruit.emails)
                && addresses.equals(otherRecruit.addresses)
                && tags.equals(otherRecruit.tags);
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
                && description.equals(otherRecruit.description)
                && tags.equals(otherRecruit.tags)
                && this.isArchived() == otherRecruit.isArchived();
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
