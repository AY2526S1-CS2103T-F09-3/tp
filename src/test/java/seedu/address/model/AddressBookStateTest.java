package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalRecruits.AMY;
import static seedu.address.testutil.TypicalRecruits.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

public class AddressBookStateTest {
    @Test
    public void equals() {
        // same address book and message -> return true
        ReadOnlyAddressBook ab1 = getTypicalAddressBook();
        ReadOnlyAddressBook ab2 = getTypicalAddressBook();
        AddressBookState abs1 = new AddressBookState(ab2, "INITIAL STATE");
        AddressBookState abs2 = new AddressBookState(ab2, "INITIAL STATE");
        assertTrue(abs1.equals(abs2));

        // same object -> return true
        assertTrue(abs1.equals(abs1));;

        // null -> return false
        assertFalse(abs1.equals(null));

        // different types -> return false
        assertFalse(abs1.equals(""));

        // different address book and same message -> return false
        AddressBook ab3 = getTypicalAddressBook();
        ab3.addRecruit(AMY);
        AddressBookState abs3 = new AddressBookState(ab3, "INITIAL STATE");
        assertFalse(abs1.equals(abs3));

        // different message and same address book -> return false
        AddressBookState abs4 = new AddressBookState(ab1, "FINAL STATE");
        assertFalse(abs1.equals(abs4));
    }
}
