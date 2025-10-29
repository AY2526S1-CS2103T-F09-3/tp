package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedRecruit.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalRecruits.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.recruit.Address;
import seedu.address.model.recruit.Description;
import seedu.address.model.recruit.Email;
import seedu.address.model.recruit.Name;
import seedu.address.model.recruit.Phone;

public class JsonAdaptedRecruitTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_ID = BENSON.getID().toString();
    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().get().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().get().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().get().toString();
    private static final String VALID_DESCRIPTION = BENSON.getDescription().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    private static final List<String> VALID_NAME_LIST = List.of(VALID_NAME);
    private static final List<String> VALID_PHONE_LIST = List.of(VALID_PHONE);
    private static final List<String> VALID_EMAIL_LIST = List.of(VALID_EMAIL);
    private static final List<String> VALID_ADDRESS_LIST = List.of(VALID_ADDRESS);

    private static final List<String> INVALID_NAME_LIST = List.of(INVALID_NAME);
    private static final List<String> INVALID_PHONE_LIST = List.of(INVALID_PHONE);
    private static final List<String> INVALID_EMAIL_LIST = List.of(INVALID_EMAIL);
    private static final List<String> INVALID_ADDRESS_LIST = List.of(INVALID_ADDRESS);

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedRecruit person = new JsonAdaptedRecruit(BENSON);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedRecruit person =
                new JsonAdaptedRecruit(VALID_ID, INVALID_NAME_LIST, VALID_PHONE_LIST,
                        VALID_EMAIL_LIST, VALID_ADDRESS_LIST, VALID_DESCRIPTION, VALID_TAGS, false);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedRecruit person = new JsonAdaptedRecruit(VALID_ID, null, VALID_PHONE_LIST,
                VALID_EMAIL_LIST, VALID_ADDRESS_LIST, VALID_DESCRIPTION, VALID_TAGS, false);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedRecruit person =
                new JsonAdaptedRecruit(VALID_ID, VALID_NAME_LIST, INVALID_PHONE_LIST,
                        VALID_EMAIL_LIST, VALID_ADDRESS_LIST, VALID_DESCRIPTION, VALID_TAGS, false);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedRecruit person = new JsonAdaptedRecruit(VALID_ID, VALID_NAME_LIST, null,
                VALID_EMAIL_LIST, VALID_ADDRESS_LIST, VALID_DESCRIPTION, VALID_TAGS, false);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedRecruit person =
                new JsonAdaptedRecruit(VALID_ID, VALID_NAME_LIST, VALID_PHONE_LIST,
                        INVALID_EMAIL_LIST, VALID_ADDRESS_LIST, VALID_DESCRIPTION, VALID_TAGS, false);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedRecruit person = new JsonAdaptedRecruit(VALID_ID, VALID_NAME_LIST, VALID_PHONE_LIST,
            null, VALID_ADDRESS_LIST, VALID_DESCRIPTION, VALID_TAGS, false);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedRecruit person =
                new JsonAdaptedRecruit(VALID_ID, VALID_NAME_LIST, VALID_PHONE_LIST, VALID_EMAIL_LIST,
                        INVALID_ADDRESS_LIST, VALID_DESCRIPTION, VALID_TAGS, false);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedRecruit person = new JsonAdaptedRecruit(VALID_ID, VALID_NAME_LIST, VALID_PHONE_LIST,
                VALID_EMAIL_LIST, null, VALID_DESCRIPTION, VALID_TAGS, false);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullDescription_throwsIllegalValueException() {
        JsonAdaptedRecruit person = new JsonAdaptedRecruit(VALID_ID, VALID_NAME_LIST, VALID_PHONE_LIST,
                VALID_EMAIL_LIST, VALID_ADDRESS_LIST, null, VALID_TAGS, false);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedRecruit person =
                new JsonAdaptedRecruit(VALID_ID, VALID_NAME_LIST, VALID_PHONE_LIST,
                        VALID_EMAIL_LIST, VALID_ADDRESS_LIST, VALID_DESCRIPTION, invalidTags, false);
        assertThrows(IllegalValueException.class, person::toModelType);
    }

}
