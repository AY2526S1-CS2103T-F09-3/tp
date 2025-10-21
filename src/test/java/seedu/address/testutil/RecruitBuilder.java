package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

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
    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Set<Tag> tags;
    private boolean isArchived;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public RecruitBuilder() {
        id = UUID.randomUUID();
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        tags = new HashSet<>();
        isArchived = false;
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public RecruitBuilder(Recruit recruitToCopy) {
        id = recruitToCopy.getID();
        name = recruitToCopy.getName();
        phone = recruitToCopy.getPhone();
        email = recruitToCopy.getEmail();
        address = recruitToCopy.getAddress();
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
        this.name = new Name(name);
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
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Recruit} that we are building.
     */
    public RecruitBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Recruit} that we are building.
     */
    public RecruitBuilder withEmail(String email) {
        this.email = new Email(email);
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
