package seedu.address.model;

/**
 * Represents a state of the address book in time, together with the command that
 *  modified it to it state.
 */
public class AddressBookState {
    public final ReadOnlyAddressBook addressBook;
    public final String command;

    /**
     * Creates an AddressBookState with the given AddressBook and command.
     */
    public AddressBookState(ReadOnlyAddressBook ab, String cmd) {
        this.addressBook = new AddressBook(ab);
        this.command = cmd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof AddressBookState)) {
            return false;
        }

        AddressBookState abs = (AddressBookState) o;
        return this.addressBook.equals(abs.addressBook) && this.command.equals(abs.command);
    }
}
