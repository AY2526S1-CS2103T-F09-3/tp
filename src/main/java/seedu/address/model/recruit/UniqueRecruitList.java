package seedu.address.model.recruit;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.recruit.exceptions.DuplicateRecruitException;
import seedu.address.model.recruit.exceptions.RecruitNotFoundException;

/**
 * A list of recruits that enforces uniqueness between its elements and does not allow nulls.
 * A person is considered unique by comparing using {@code Person#isSamePerson(Person)}. As such, adding and updating of
 * persons uses Person#isSamePerson(Person) for equality so as to ensure that the person being added or updated is
 * unique in terms of identity in the UniquePersonList. However, the removal of a person uses Person#equals(Object) so
 * as to ensure that the person with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Recruit#isSameRecruit(Recruit)
 */
public class UniqueRecruitList implements Iterable<Recruit> {

    private final ObservableList<Recruit> internalList = FXCollections.observableArrayList();
    private final ObservableList<Recruit> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(Recruit toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameRecruit);
    }

    /**
     * Adds a person to the list.
     * The person must not already exist in the list.
     */
    public void add(Recruit toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateRecruitException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the list.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the list.
     */
    public void setRecruit(Recruit target, Recruit editedRecruit) {
        requireAllNonNull(target, editedRecruit);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new RecruitNotFoundException();
        }

        if (!target.isSameRecruit(editedRecruit) && contains(editedRecruit)) {
            throw new DuplicateRecruitException();
        }

        internalList.set(index, editedRecruit);
    }

    /**
     * Removes the equivalent person from the list.
     * The person must exist in the list.
     */
    public void remove(Recruit toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new RecruitNotFoundException();
        }
    }

    public void setRecruits(UniqueRecruitList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setRecruits(List<Recruit> recruits) {
        requireAllNonNull(recruits);
        if (!recruitsAreUnique(recruits)) {
            throw new DuplicateRecruitException();
        }

        internalList.setAll(recruits);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Recruit> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Recruit> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UniqueRecruitList)) {
            return false;
        }

        UniqueRecruitList otherUniqueRecruitList = (UniqueRecruitList) other;
        return internalList.equals(otherUniqueRecruitList.internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        return internalList.toString();
    }

    /**
     * Returns true if {@code persons} contains only unique persons.
     */
    private boolean recruitsAreUnique(List<Recruit> recruits) {
        for (int i = 0; i < recruits.size() - 1; i++) {
            for (int j = i + 1; j < recruits.size(); j++) {
                if (recruits.get(i).isSameRecruit(recruits.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    public void sort(Comparator<Recruit> comparator) {
        FXCollections.sort(internalList, comparator);
    }
}
