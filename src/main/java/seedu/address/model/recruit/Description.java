package seedu.address.model.recruit;

import static java.util.Objects.requireNonNull;

/**
 * Represents the Description of a Recruit in the address book.
 * Guarantees: immutable
 */
public class Description {
    public static final String MESSAGE_CONSTRAINTS = "Descriptions can take any values, and can be blank";

    /*
     * Descriptions can take any character.
     * For now it is redundant, but we leave it in case we want to impose
     * restrictions in the future.
     */
    public static final String VALIDATION_REGEX = "(?s).*";

    public final String value;

    /**
     * Constructs a {@code Description}.
     *
     * @param description A description for that Recruit.
     */
    public Description(String description) {
        requireNonNull(description);
        this.value = description;
    }

    /**
     * Copy constructor for {@code Description}
     * @param other
     */
    public Description(Description other) {
        this.value = other.value;
    }

    public static boolean isValidDescription(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public static Description createEmptyDescription() {
        return new Description("-");
    }

    /**
     * Combines to different description. The other description gets merged into this description
     *
     * @param otherDescription description to append
     * @return the combined descriptions
     */
    public Description appendDescription(Description otherDescription) {
        if (this.value.equals("-")) {
            return new Description(otherDescription.value);
        }

        if (otherDescription.value.equals("-")) {
            return this;
        }

        return new Description(this.value + otherDescription.value);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Description)) {
            return false;
        }

        Description otherDescription = (Description) other;
        return value.equals(otherDescription.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
