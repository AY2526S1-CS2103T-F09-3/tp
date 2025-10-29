package seedu.address.model.recruit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalRecruits.ALICE;
import static seedu.address.testutil.TypicalRecruits.BOB;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import seedu.address.model.tag.Tag;
import seedu.address.testutil.RecruitBuilder;

public class RecruitTest {

    @Test
    public void constructors() {

        // recruit with random uuid and non-list params
        Recruit r = new Recruit(new Name(VALID_NAME_AMY), new Phone(VALID_PHONE_AMY),
                new Email(VALID_EMAIL_AMY), new Address(VALID_ADDRESS_AMY),
                new Description(VALID_DESCRIPTION_AMY),
                new HashSet<>(List.of(new Tag(VALID_TAG_FRIEND))), false);

        assertEquals(new Name(VALID_NAME_AMY), r.getName());
        assertEquals(new Phone(VALID_PHONE_AMY), r.getPhone().get());
        assertEquals(new Email(VALID_EMAIL_AMY), r.getEmail().get());
        assertEquals(new Address(VALID_ADDRESS_AMY), r.getAddress().get());
        assertEquals(new Description(VALID_DESCRIPTION_AMY), r.getDescription());
        assertEquals(new HashSet<>(List.of(new Tag(VALID_TAG_FRIEND))), r.getTags());

        // recruit with random uuid and list params
        r = new Recruit(List.of(new Name(VALID_NAME_AMY)), List.of(new Phone(VALID_PHONE_AMY)),
                List.of(new Email(VALID_EMAIL_AMY)), List.of(new Address(VALID_ADDRESS_AMY)),
                new Description(VALID_DESCRIPTION_AMY),
                new HashSet<>(List.of(new Tag(VALID_TAG_FRIEND))), false);

        assertEquals(new Name(VALID_NAME_AMY), r.getName());
        assertEquals(new Phone(VALID_PHONE_AMY), r.getPhone().get());
        assertEquals(new Email(VALID_EMAIL_AMY), r.getEmail().get());
        assertEquals(new Address(VALID_ADDRESS_AMY), r.getAddress().get());
        assertEquals(new HashSet<>(List.of(new Tag(VALID_TAG_FRIEND))), r.getTags());
    }

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

        // same name, all other attributes different -> returns false
        Recruit editedAlice = new RecruitBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.isSameRecruit(editedAlice));

        // different name, all other attributes same -> returns true
        editedAlice = new RecruitBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSameRecruit(editedAlice));

        // name differs in case, all other attributes same -> returns true
        Recruit editedBob = new RecruitBuilder(BOB).withName(VALID_NAME_BOB.toLowerCase()).build();
        assertFalse(BOB.isSameRecruit(editedBob));

        // name has trailing spaces, all other attributes same -> returns true
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBob = new RecruitBuilder(BOB).withName(nameWithTrailingSpaces).build();
        assertFalse(BOB.isSameRecruit(editedBob));

        // same fields, different id -> returns true
        Recruit editedBobWithNewId = new RecruitBuilder(BOB).withID(UUID.randomUUID().toString())
                .withName(VALID_NAME_BOB.toLowerCase()).build();
        assertFalse(BOB.isSameRecruit(editedBobWithNewId));
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
        String expected = String.format("%s{id=%s, names=%s, phones=%s, emails=%s, addresses=%s, tags=%s}",
                Recruit.class.getCanonicalName(), ALICE.getID(), ALICE.getNames(), ALICE.getPhones(),
                ALICE.getEmails(), ALICE.getAddresses(), ALICE.getTags());
        assertEquals(expected, ALICE.toString());
    }
}
