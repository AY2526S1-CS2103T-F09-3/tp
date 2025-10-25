package seedu.address.commons.util;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.logic.commands.CommandTestUtil;
import seedu.address.model.recruit.Address;
import seedu.address.model.recruit.Description;
import seedu.address.model.recruit.Email;
import seedu.address.model.recruit.Name;
import seedu.address.model.recruit.Phone;
import seedu.address.model.recruit.Recruit;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.TestUtil;

/**
 * Tests CSV Read and Write
 */
public class CsvUtilTest {

    private static final Path CSV_FILE = TestUtil.getFilePathInSandboxFolder("recruits.csv");

    private Recruit testRecruit;

    @BeforeEach
    public void setUp() {
        testRecruit = new Recruit(
                UUID.randomUUID(),
                List.of(new Name("Alice"), new Name("Ally")),
                List.of(new Phone("12345678"), new Phone("87654321")),
                List.of(new Email("alice@example.com")),
                List.of(new Address("123, Wonderland Street")),
                new Description("Description"),
                Set.of(new Tag("friend"), new Tag("colleague")),
                false
        );
    }

    @Test
    public void serializeRecruitsToCsvFile_noExceptionThrown() throws IOException {
        List<Recruit> recruits = List.of(testRecruit);

        CsvUtil.serializeRecruitsToCsvFile(CSV_FILE, recruits);

        String csvContent = FileUtil.readFromFile(CSV_FILE);
        String expectedHeader = "ID,Names,Phones,Emails,Addresses,Tags";
        assertEquals(true, csvContent.startsWith(expectedHeader));
        assertEquals(true, csvContent.contains("Alice;Ally"));
        assertEquals(true, csvContent.contains("12345678;87654321"));
        assertEquals(true, csvContent.contains("[friend];[colleague]"));
    }

    @Test
    public void deserializeRecruitsFromCsvFile_noExceptionThrown() throws IOException, DataLoadingException {
        List<Recruit> recruits = List.of(testRecruit);
        CsvUtil.serializeRecruitsToCsvFile(CSV_FILE, recruits);

        List<Recruit> deserializedRecruits = CsvUtil.deserializeRecruitsFromCsvFile(CSV_FILE);

        assertEquals(1, deserializedRecruits.size());

        Recruit r = deserializedRecruits.get(0);
        System.out.println(r);
        assertEquals(testRecruit.getID(), r.getID());
        assertEquals(testRecruit.getNames(), r.getNames());
        assertEquals(testRecruit.getPhones(), r.getPhones());
        assertEquals(testRecruit.getEmails(), r.getEmails());
        assertEquals(testRecruit.getAddresses(), r.getAddresses());
        assertEquals(testRecruit.getTags(), r.getTags());
    }

    @Test
    public void fromCsvString_correctlyParsesCsvString() {
        String csvString = CsvUtil.recruitsToCsvString(List.of(testRecruit));

        List<Recruit> parsedRecruits = CsvUtil.fromCsvString(csvString);

        assertEquals(1, parsedRecruits.size());

        Recruit r = parsedRecruits.get(0);
        assertEquals(testRecruit.getID(), r.getID());
        assertEquals(testRecruit.getNames(), r.getNames());
        assertEquals(testRecruit.getPhones(), r.getPhones());
        assertEquals(testRecruit.getEmails(), r.getEmails());
        assertEquals(testRecruit.getAddresses(), r.getAddresses());
        assertEquals(testRecruit.getTags(), r.getTags());
    }

    @Test
    public void escapeCsvField_handlesSpecialCharacters() {
        String fieldWithComma = "Hello,World";
        String fieldWithQuote = "He said \"Hi\"";

        // CSV with comma
        Recruit recruitWithComma = new Recruit(UUID.randomUUID(),
                List.of(new Name(CommandTestUtil.VALID_NAME_AMY)),
                List.of(), List.of(),
                List.of(new Address(fieldWithComma)),
                new Description("Description"),
                Set.of(),
                false);
        String csvComma = CsvUtil.recruitsToCsvString(List.of(recruitWithComma));
        String csvLineComma = csvComma.split("\n")[1];
        String[] colsComma = csvLineComma.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
        assertEquals("\"Hello,World\"", colsComma[4]);

        // CSV with quotes
        Recruit recruitWithQuote = new Recruit(UUID.randomUUID(),
                List.of(new Name(CommandTestUtil.VALID_NAME_AMY)),
                List.of(), List.of(),
                List.of(new Address(fieldWithQuote)),
                new Description("Description"),
                Set.of(),
                false);
        String csvQuote = CsvUtil.recruitsToCsvString(List.of(recruitWithQuote));
        String csvLineQuote = csvQuote.split("\n")[1];
        String[] colsQuote = csvLineQuote.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
        assertEquals("\"He said \"\"Hi\"\"\"", colsQuote[4]);
    }

    @Test
    public void fromCsvString_emptyCsv_returnsEmptyList() {
        List<Recruit> recruits = CsvUtil.fromCsvString("ID,Names,Phones,Emails,Addresses,Tags\n");
        assertEquals(0, recruits.size());
    }

    @Test
    public void fromCsvString_skipsEmptyLines() {
        String csv = CsvUtil.recruitsToCsvString(List.of(testRecruit)) + "\n\n";
        List<Recruit> recruits = CsvUtil.fromCsvString(csv);
        assertEquals(1, recruits.size());
    }
}
