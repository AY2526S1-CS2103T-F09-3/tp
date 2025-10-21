package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.recruit.Address;
import seedu.address.model.recruit.Email;
import seedu.address.model.recruit.Name;
import seedu.address.model.recruit.Phone;
import seedu.address.model.recruit.Recruit;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Recruit}.
 */
class JsonAdaptedRecruit {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Recruit's %s field is missing!";

    private final String id;
    private final List<String> names;
    private final List<String> phones;
    private final List<String> emails;
    private final List<String> addresses;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();
    private final boolean isArchived;

    /**
     * Constructs a {@code JsonAdaptedRecruit} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedRecruit(@JsonProperty("id") String id,
                              @JsonProperty("names") List<String> name,
                              @JsonProperty("phones") List<String> phone,
                              @JsonProperty("emails") List<String> email,
                              @JsonProperty("addresses") List<String> address,
                              @JsonProperty("tags") List<JsonAdaptedTag> tags,
                              @JsonProperty("isArchived") boolean isArchived) {
        this.id = id;
        this.names = name;
        this.phones = phone;
        this.emails = email;
        this.addresses = address;
        if (tags != null) {
            this.tags.addAll(tags);
        }
        this.isArchived = isArchived;
    }

    /**
     * Converts a given {@code Recruit} into this class for Jackson use.
     */
    public JsonAdaptedRecruit(Recruit source) {
        id = source.getID().toString();
        names = source.getNames().stream().map(n -> n.fullName).toList();
        phones = source.getPhones().stream().map(p -> p.value).toList();
        emails = source.getEmails().stream().map(e -> e.value).toList();
        addresses = source.getAddresses().stream().map(a -> a.value).toList();
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        isArchived = source.getArchiveStatus();
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Recruit toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            personTags.add(tag.toModelType());
        }
        if (id == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, UUID.class.getSimpleName()));
        }
        final UUID modelId = UUID.fromString(id);

        if (names == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (names.stream().anyMatch(n -> !Name.isValidName(n))) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final List<Name> modelNames = names.stream().map(Name::new).toList();

        if (phones == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (phones.stream().anyMatch(p -> !Phone.isValidPhone(p))) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final List<Phone> modelPhones = phones.stream().map(Phone::new).toList();

        if (emails == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (emails.stream().anyMatch(e -> !Email.isValidEmail(e))) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final List<Email> modelEmails = emails.stream().map(Email::new).toList();

        if (addresses == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (addresses.stream().anyMatch(a -> !Address.isValidAddress(a))) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final List<Address> modelAddresses = addresses.stream().map(Address::new).toList();

        final Set<Tag> modelTags = new HashSet<>(personTags);
        return new Recruit(modelId, modelNames, modelPhones, modelEmails, modelAddresses, modelTags, isArchived);
    }

}
