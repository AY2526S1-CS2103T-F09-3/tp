package seedu.address.model;

/**
 * Represents a state of the address book in time, together with the command that
 *  modified it to it state.
 */
public class AddressBookState {
    private final ReadOnlyAddressBook addressBook;
    private final String operationDescriptor;

    /**
     * Creates an AddressBookState with the given AddressBook and command.
     */
    public AddressBookState(ReadOnlyAddressBook ab, String operationDescriptor) {
        this.addressBook = new AddressBook(ab);
        this.operationDescriptor = operationDescriptor;
    }

    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    public String getOperationDescriptor() {
        return operationDescriptor;
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
        return this.addressBook.equals(abs.addressBook) && this.operationDescriptor.equals(abs.operationDescriptor);
    }
}
