package seedu.address.model.recruit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalRecruits.ALICE;
import static seedu.address.testutil.TypicalRecruits.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.RecruitBuilder;

public class RecruitTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Recruit recruit = new RecruitBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> recruit.getTags().remove(0));
    }

    @Test
    public void isSameRecruit() {
        // same object -> returns true
        assertTrue(ALICE.isSameRecruit(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameRecruit(null));

        // same name, all other attributes different -> returns true
        Recruit editedAlice = new RecruitBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameRecruit(editedAlice));

        // different name, all other attributes same -> returns true
        editedAlice = new RecruitBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertTrue(ALICE.isSameRecruit(editedAlice));

        // name differs in case, all other attributes same -> returns true
        Recruit editedBob = new RecruitBuilder(BOB).withName(VALID_NAME_BOB.toLowerCase()).build();
        assertTrue(BOB.isSameRecruit(editedBob));

        // name has trailing spaces, all other attributes same -> returns true
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBob = new RecruitBuilder(BOB).withName(nameWithTrailingSpaces).build();
        assertTrue(BOB.isSameRecruit(editedBob));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Recruit aliceCopy = new RecruitBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different person -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Recruit editedAlice = new RecruitBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new RecruitBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new RecruitBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new RecruitBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new RecruitBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void toStringMethod() {
        String expected = Recruit.class.getCanonicalName() + "{id=" + ALICE.getID() + ", name=[" + ALICE.getName()
                + "], phone=[" + ALICE.getPhone() + "], email=[" + ALICE.getEmail() + "], address=["
                + ALICE.getAddress() + "], tags=" + ALICE.getTags() + "}";
        assertEquals(expected, ALICE.toString());
    }
}
