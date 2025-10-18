package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

/**
 * An AddressBook with version history to support undo operation
 */
public class VersionedAddressBook extends AddressBook {
    private static final int MAX_UNDO_HISTORY_SIZE = 200;

    private final List<AddressBookState> addressBookStateList = new ArrayList<>();
    private int currentStatePtr;

    /**
     * Creates a VersionedAddressBook using the Recruits in the {@code toBeCopied}
     */
    public VersionedAddressBook(ReadOnlyAddressBook toBeCopied) {
        super(toBeCopied);

        this.addressBookStateList.add(new AddressBookState(toBeCopied, "INITIAL STATE"));
        currentStatePtr = 0;
    }

    /**
     * Saves the current address book state in history.
     */
    public void commit(String command) {
        purgeFutureStates();
        addressBookStateList.add(new AddressBookState(this, command));

        if (addressBookStateList.size() > MAX_UNDO_HISTORY_SIZE) {
            addressBookStateList.remove(0);
        }

        currentStatePtr++;
    }

    public boolean canUndoAddressBook() {
        return currentStatePtr > 0;
    }


    /**
     * Undoes the last performed operation and restores the previous address book state.
     *
     * @return the command string of the operation that was undone
     */
    public String undo() {
        assert currentStatePtr > 0;

        String undoneOperation = addressBookStateList.get(currentStatePtr).command;
        currentStatePtr--;
        AddressBookState addressBookStateToRestore = addressBookStateList.get(currentStatePtr);
        this.setRecruits(addressBookStateToRestore.addressBook.getRecruitList());

        return undoneOperation;
    }

    /**
     * Deletes all AddressBook states after the currently pointed state.
     */
    private void purgeFutureStates() {
        final int removeIndex = addressBookStateList.size();
        while (addressBookStateList.size() > currentStatePtr + 1) {
            addressBookStateList.remove(removeIndex);
        }
    }
}
