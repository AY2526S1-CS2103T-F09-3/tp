package seedu.address.model.util;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.recruit.Address;
import seedu.address.model.recruit.Description;
import seedu.address.model.recruit.Email;
import seedu.address.model.recruit.Name;
import seedu.address.model.recruit.Phone;
import seedu.address.model.recruit.Recruit;
import seedu.address.model.recruit.RecruitBuilder;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Recruit[] getSampleRecruits() {
        return new Recruit[] {
                new RecruitBuilder()
                        .withName(new Name("Alex Yeoh"))
                        .withPhone(new Phone("87438807"))
                        .withEmail(new Email("alexyeoh@example.com"))
                        .withAddress(new Address("Blk 30 Geylang Street 29, #06-40"))
                        .withDescription(new Description(
                                "Enthusiastic software developer with a passion for open source."))
                        .withTags(createTagList("friends"))
                        .build(),

                new RecruitBuilder()
                        .withName(new Name("Bernice Yu"))
                        .withPhone(new Phone("99272758"))
                        .withEmail(new Email("berniceyu@example.com"))
                        .withAddress(new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"))
                        .withDescription(new Description(
                                "Experienced marketing specialist with a knack for social media."))
                        .withTags(createTagList("colleagues", "friends"))
                        .build(),

                new RecruitBuilder()
                        .withName(new Name("Charlotte Oliveiro"))
                        .withPhone(new Phone("93210283"))
                        .withEmail(new Email("charlotte@example.com"))
                        .withAddress(new Address("Blk 11 Ang Mo Kio Street 74, #11-04"))
                        .withDescription(new Description(
                                "Project manager skilled in agile methodologies."))
                        .withTags(createTagList("neighbours"))
                        .build(),

                new RecruitBuilder()
                        .withName(new Name("David Li"))
                        .withPhone(new Phone("91031282"))
                        .withEmail(new Email("lidavid@example.com"))
                        .withAddress(new Address("Blk 436 Serangoon Gardens Street 26, #16-43"))
                        .withDescription(new Description(
                                "Experienced data analyst with a focus on financial services."))
                        .withTags(createTagList("family"))
                        .build(),

                new RecruitBuilder()
                        .withName(new Name("Irfan Ibrahim"))
                        .withPhone(new Phone("92492021"))
                        .withEmail(new Email("irfan@example.com"))
                        .withAddress(new Address("Blk 47 Tampines Street 20, #17-35"))
                        .withDescription(new Description(
                                "Recent graduate passionate about machine learning."))
                        .withTags(createTagList("classmates"))
                        .build(),

                new RecruitBuilder()
                        .withName(new Name("Roy Balakrishnan"))
                        .withPhone(new Phone("92624417"))
                        .withEmail(new Email("royb@example.com"))
                        .withAddress(new Address("Blk 45 Aljunied Street 85, #11-31"))
                        .withDescription(new Description(
                                "Experienced network engineer and cybersecurity expert."))
                        .withTags(createTagList("colleagues"))
                        .build()
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Recruit sampleRecruit : getSampleRecruits()) {
            sampleAb.addRecruit(sampleRecruit);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

    /**
     * Returns a List containing all the tags created using the given strings.
     */
    public static List<Tag> createTagList(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .toList();
    }

}
