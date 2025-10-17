package seedu.address.testutil;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand;
import seedu.address.model.recruit.Address;
import seedu.address.model.recruit.Email;
import seedu.address.model.recruit.Name;
import seedu.address.model.recruit.Phone;
import seedu.address.model.recruit.Recruit;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditPersonDescriptor objects.
 */
public class EditRecruitDescriptorBuilder {

    private EditCommand.EditRecruitDescriptor descriptor;

    public EditRecruitDescriptorBuilder() {
        descriptor = new EditCommand.EditRecruitDescriptor();
    }

    public EditRecruitDescriptorBuilder(EditCommand.EditRecruitDescriptor descriptor) {
        this.descriptor = new EditCommand.EditRecruitDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPersonDescriptor} with fields containing {@code person}'s details
     */
    public EditRecruitDescriptorBuilder(Recruit recruit) {
        descriptor = new EditCommand.EditRecruitDescriptor();
        descriptor.setID(recruit.getID());
        descriptor.setNames(recruit.getNames());
        descriptor.setPhones(recruit.getPhones());
        descriptor.setEmails(recruit.getEmails());
        descriptor.setAddresses(recruit.getAddresses());
        descriptor.setTags(recruit.getTags());
    }
    /**
     * Sets the {@code Name} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditRecruitDescriptorBuilder withID(String id) {
        descriptor.setID(UUID.fromString(id));
        return this;
    }

    /**
     * Sets the {@code Name} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditRecruitDescriptorBuilder withName(String name) {
        descriptor.setNames(List.of(new Name(name)));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditRecruitDescriptorBuilder withPhone(String phone) {
        descriptor.setPhones(List.of(new Phone(phone)));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditRecruitDescriptorBuilder withEmail(String email) {
        descriptor.setEmails(List.of(new Email(email)));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditRecruitDescriptorBuilder withAddress(String address) {
        descriptor.setAddresses(List.of(new Address(address)));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditPersonDescriptor}
     * that we are building.
     */
    public EditRecruitDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditCommand.EditRecruitDescriptor build() {
        return descriptor;
    }
}
