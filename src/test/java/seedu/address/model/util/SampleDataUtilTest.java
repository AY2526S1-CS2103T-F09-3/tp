package seedu.address.model.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.model.recruit.Recruit;
import seedu.address.model.tag.Tag;

public class SampleDataUtilTest {

    @Test
    public void getSampleRecruits_returnsCorrectRecruits() {
        Recruit[] recruits = SampleDataUtil.getSampleRecruits();

        assertNotNull(recruits);
        assertEquals(6, recruits.length);

        // Alex Yeoh
        Recruit alex = recruits[0];
        assertEquals("Alex Yeoh", alex.getName().toString());
        assertEquals("87438807", alex.getPhone().get().toString());
        assertEquals("alexyeoh@example.com", alex.getEmail().get().toString());
        assertEquals("Blk 30 Geylang Street 29, #06-40", alex.getAddress().get().toString());
        assertEquals("Enthusiastic software developer with a passion for open source.",
                alex.getDescription().toString());
        assertTrue(alex.getTags().stream().map(Tag::toString).collect(Collectors.toSet()).contains("[friends]"));

        // Bernice Yu
        Recruit bernice = recruits[1];
        assertEquals("Bernice Yu", bernice.getName().toString());
        assertEquals("99272758", bernice.getPhone().get().toString());
        assertEquals("berniceyu@example.com", bernice.getEmail().get().toString());
        assertEquals("Blk 30 Lorong 3 Serangoon Gardens, #07-18", bernice.getAddress().get().toString());
        assertEquals("Experienced marketing specialist with a knack for social media.",
                bernice.getDescription().toString());
        Set<String> berniceTags = bernice.getTags().stream().map(Tag::toString).collect(Collectors.toSet());
        assertTrue(berniceTags.contains("[colleagues]"));
        assertTrue(berniceTags.contains("[friends]"));

        // Charlotte Oliveiro
        Recruit charlotte = recruits[2];
        assertEquals("Charlotte Oliveiro", charlotte.getName().toString());
        assertEquals("93210283", charlotte.getPhone().get().toString());
        assertEquals("charlotte@example.com", charlotte.getEmail().get().toString());
        assertEquals("Blk 11 Ang Mo Kio Street 74, #11-04", charlotte.getAddress().get().toString());
        assertEquals("Project manager skilled in agile methodologies.",
                charlotte.getDescription().toString());
        assertTrue(charlotte.getTags().stream().map(Tag::toString)
                .collect(Collectors.toSet()).contains("[neighbours]"));

        // David Li
        Recruit david = recruits[3];
        assertEquals("David Li", david.getName().toString());
        assertEquals("91031282", david.getPhone().get().toString());
        assertEquals("lidavid@example.com", david.getEmail().get().toString());
        assertEquals("Blk 436 Serangoon Gardens Street 26, #16-43", david.getAddress().get().toString());
        assertEquals("Experienced data analyst with a focus on financial services.",
                david.getDescription().toString());
        assertTrue(david.getTags().stream().map(Tag::toString).collect(Collectors.toSet()).contains("[family]"));

        // Irfan Ibrahim
        Recruit irfan = recruits[4];
        assertEquals("Irfan Ibrahim", irfan.getName().toString());
        assertEquals("92492021", irfan.getPhone().get().toString());
        assertEquals("irfan@example.com", irfan.getEmail().get().toString());
        assertEquals("Blk 47 Tampines Street 20, #17-35", irfan.getAddress().get().toString());
        assertEquals("Recent graduate passionate about machine learning.",
                irfan.getDescription().toString());
        assertTrue(irfan.getTags().stream().map(Tag::toString).collect(Collectors.toSet()).contains("[classmates]"));

        // Roy Balakrishnan
        Recruit roy = recruits[5];
        assertEquals("Roy Balakrishnan", roy.getName().toString());
        assertEquals("92624417", roy.getPhone().get().toString());
        assertEquals("royb@example.com", roy.getEmail().get().toString());
        assertEquals("Blk 45 Aljunied Street 85, #11-31", roy.getAddress().get().toString());
        assertEquals("Experienced network engineer and cybersecurity expert.",
                roy.getDescription().toString());
        assertTrue(roy.getTags().stream().map(Tag::toString).collect(Collectors.toSet()).contains("[colleagues]"));
    }

    @Test
    public void getTagSet_createsCorrectTags() {
        Set<Tag> tags = SampleDataUtil.getTagSet("friends", "colleagues");

        assertNotNull(tags);
        assertEquals(2, tags.size());

        Set<String> tagNames = tags.stream().map(Tag::toString).collect(Collectors.toSet());
        assertTrue(tagNames.contains("[friends]"));
        assertTrue(tagNames.contains("[colleagues]"));
    }
}
