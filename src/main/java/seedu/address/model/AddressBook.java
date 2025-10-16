package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.recruit.Recruit;
import seedu.address.model.recruit.UniqueRecruitList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniqueRecruitList recruits;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        recruits = new UniqueRecruitList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the recruit list with {@code recruits}.
     * {@code persons} must not contain duplicate recruits.
     */
    public void setRecruits(List<Recruit> recruits) {
        this.recruits.setRecruits(recruits);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setRecruits(newData.getRecruitList());
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasRecruit(Recruit recruit) {
        requireNonNull(recruit);
        return recruits.contains(recruit);
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addRecruit(Recruit p) {
        recruits.add(p);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void setRecruit(Recruit target, Recruit editedRecruit) {
        requireNonNull(editedRecruit);

        recruits.setRecruit(target, editedRecruit);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeRecruit(Recruit key) {
        recruits.remove(key);
    }

    /**
     * Sorts the recruits in the address book by name in alphabetical order.
     */
    public void sortRecruits() {
        Comparator<Recruit> comparator;
        comparator = Comparator.comparing(Recruit::getName);
        recruits.sort(comparator);
    }

    //// util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("recruits", recruits)
                .toString();
    }

    @Override
    public ObservableList<Recruit> getRecruitList() {
        return recruits.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddressBook)) {
            return false;
        }
        System.out.println(this.recruits);
        System.out.println(((AddressBook) other).recruits);
        AddressBook otherAddressBook = (AddressBook) other;
        return recruits.equals(otherAddressBook.recruits);
    }

    @Override
    public int hashCode() {
        return recruits.hashCode();
    }
}
