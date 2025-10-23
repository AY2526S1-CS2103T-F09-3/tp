package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

/**
 * An AddressBook with version history to support undo operation
 */
public class VersionedAddressBook extends AddressBook {
    public static final String INITIAL_STATE_MARKER = "INITIAL STATE";
    public static final int MAX_UNDO_HISTORY_SIZE = 200;

    private final List<AddressBookState> addressBookStateList = new ArrayList<>();
    private int currentStatePtr;

    /**
     * Creates a VersionedAddressBook using the Recruits in the {@code toBeCopied}
     */
    public VersionedAddressBook(ReadOnlyAddressBook toBeCopied) {
        super(toBeCopied);

        this.addressBookStateList.add(new AddressBookState(toBeCopied, INITIAL_STATE_MARKER));
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
        } else {
            currentStatePtr++;
        }

    }

    public boolean canUndoAddressBook() {
        return currentStatePtr > 0;
    }

    public boolean canRedoAddressBook() {
        return currentStatePtr < addressBookStateList.size() - 1;
    }

    /**
     * Undoes the last performed operation and restores the previous address book state.
     *
     * @return the command string of the operation that was undone
     */
    public String undo() {
        if (currentStatePtr <= 0) {
            throw new IllegalStateException();
        }

        String undoneOperation = addressBookStateList.get(currentStatePtr).getOperationDescriptor();
        currentStatePtr--;
        AddressBookState addressBookStateToRestore = addressBookStateList.get(currentStatePtr);
        this.setRecruits(addressBookStateToRestore.getAddressBook().getRecruitList());

        return undoneOperation;
    }

    /**
     * Redoes the last undone operation and restores this 'future' address book state.
     *
     * @return the command string of the operation that was undone
     */
    public String redo() {
        if (currentStatePtr == addressBookStateList.size() - 1) {
            throw new IllegalStateException();
        }

        currentStatePtr++;
        String redoneOperation = addressBookStateList.get(currentStatePtr).getOperationDescriptor();
        AddressBookState addressBookStateToRestore = addressBookStateList.get(currentStatePtr);
        this.setRecruits(addressBookStateToRestore.getAddressBook().getRecruitList());

        return redoneOperation;
    }

    public int getCurrentStatePtr() {
        return currentStatePtr;
    }

    /**
     * Returns a defensive copy of the state list.
     */
    public List<AddressBookState> getAddressBookStateList() {
        return List.copyOf(addressBookStateList);
    }

    /**
     * Deletes all AddressBook states after the currently pointed state.
     */
    private void purgeFutureStates() {
        if (currentStatePtr < addressBookStateList.size() - 1) {
            addressBookStateList.subList(currentStatePtr + 1, addressBookStateList.size()).clear();
        }
    }
}
