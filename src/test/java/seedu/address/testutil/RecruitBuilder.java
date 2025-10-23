package seedu.address.testutil;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

import seedu.address.model.recruit.Address;
import seedu.address.model.recruit.Email;
import seedu.address.model.recruit.Name;
import seedu.address.model.recruit.Phone;
import seedu.address.model.recruit.Recruit;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class RecruitBuilder {
    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";

    private UUID id;
    private List<Name> name;
    private List<Phone> phone;
    private List<Email> email;
    private List<Address> address;
    private Set<Tag> tags;
    private boolean isArchived;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public RecruitBuilder() {
        id = UUID.randomUUID();
        name = List.of(new Name(DEFAULT_NAME));
        phone = List.of(new Phone(DEFAULT_PHONE));
        email = List.of(new Email(DEFAULT_EMAIL));
        address = List.of(new Address(DEFAULT_ADDRESS));
        tags = new HashSet<>();
        isArchived = false;
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public RecruitBuilder(Recruit recruitToCopy) {
        id = recruitToCopy.getID();
        name = recruitToCopy.getNames();
        phone = recruitToCopy.getPhones();
        email = recruitToCopy.getEmails();
        address = recruitToCopy.getAddresses();
        tags = new HashSet<>(recruitToCopy.getTags());
        isArchived = recruitToCopy.getArchiveStatus();
    }

    /**
     * Sets the {@code Id} of the {@code Recruit} that we are building.
     */
    public RecruitBuilder withID(String id) {
        this.id = UUID.fromString(id);
        return this;
    }

    /**
     * Sets the {@code Name} of the {@code Recruit} that we are building.
     */
    public RecruitBuilder withName(String name) {
        this.name = List.of(new Name(name));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Recruit} that we are building.
     */
    public RecruitBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Recruit} that we are building.
     */
    public RecruitBuilder withAddress(String address) {
        this.address = List.of(new Address(address));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Recruit} that we are building.
     */
    public RecruitBuilder withPhone(String phone) {
        this.phone = List.of(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Recruit} that we are building.
     */
    public RecruitBuilder withEmail(String email) {
        this.email = List.of(new Email(email));
        return this;
    }

    /**
     * Sets the {@code Name} of the {@code Recruit} that we are building.
     */
    public RecruitBuilder withAdditionalName(String name) {
        this.name = Stream.concat(this.name.stream(), Stream.of(new Name(name))).toList();
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Recruit} that we are building.
     */
    public RecruitBuilder withAdditionalAddress(String address) {
        this.address = Stream.concat(this.address.stream(), Stream.of(new Address(address))).toList();
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Recruit} that we are building.
     */
    public RecruitBuilder withAdditionalPhone(String phone) {
        this.phone = Stream.concat(this.phone.stream(), Stream.of(new Phone(phone))).toList();
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Recruit} that we are building.
     */
    public RecruitBuilder withAdditionalEmail(String email) {
        this.email = Stream.concat(this.email.stream(), Stream.of(new Email(email))).toList();
        return this;
    }

    /**
     * Sets the {@code Archive Status} of the {@code Recruit} that we are building.
     */
    public RecruitBuilder withArchiveStatus(boolean isArchived) {
        this.isArchived = isArchived;
        return this;
    }

    public Recruit build() {
        return new Recruit(id, name, phone, email, address, tags, isArchived);
    }

}
