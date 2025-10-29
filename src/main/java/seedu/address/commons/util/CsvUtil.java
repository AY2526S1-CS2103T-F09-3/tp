package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.recruit.Address;
import seedu.address.model.recruit.Description;
import seedu.address.model.recruit.Email;
import seedu.address.model.recruit.Name;
import seedu.address.model.recruit.Phone;
import seedu.address.model.recruit.Recruit;
import seedu.address.model.tag.Tag;

/**
 * Converts a Java object instance to CSV and vice versa
 */
public class CsvUtil {
    private static final Logger logger = LogsCenter.getLogger(CsvUtil.class);

    /**
     * Serializes a list of recruits to a CSV file at the given path.
     * Overwrites the file if it already exists.
     *
     * @param filePath The target CSV file path.
     * @param recruits The list of recruits to export.
     * @throws IOException if writing to the file fails.
     */
    public static void serializeRecruitsToCsvFile(Path filePath, List<Recruit> recruits) throws IOException {
        requireNonNull(filePath);
        requireNonNull(recruits);

        String csvString = recruitsToCsvString(recruits);
        try {
            FileUtil.writeToFile(filePath, csvString);
        } catch (IOException e) {
            logger.warning("Error writing to csvFile file " + filePath + ": " + e);
            throw new IOException(e);
        }
    }

    /**
     * Converts a list of recruits into a CSV-formatted string.
     * Each recruit may have multiple names, phones, emails, and addresses.
     * These are concatenated with ';' within the same CSV cell.
     *
     * @param recruits List of recruits to convert.
     * @return CSV string representing the recruits.
     */
    public static String recruitsToCsvString(List<Recruit> recruits) {
        StringBuilder sb = new StringBuilder();
        sb.append("ID,Names,Phones,Emails,Addresses,Tags,Description,IsArchived\n");

        for (Recruit r : recruits) {
            String names = escapeCsvField(r.getNames().stream().map(Object::toString)
                    .collect(Collectors.joining(";")));
            String phones = escapeCsvField(r.getPhones().stream().map(Object::toString)
                    .collect(Collectors.joining(";")));
            String emails = escapeCsvField(r.getEmails().stream().map(Object::toString)
                    .collect(Collectors.joining(";")));
            String addresses = escapeCsvField(r.getAddresses().stream().map(Object::toString)
                    .collect(Collectors.joining(";")));
            String tags = escapeCsvField(r.getTags().stream().map(Object::toString)
                    .collect(Collectors.joining(";")));
            String description = escapeCsvField(r.getDescription().toString());
            String isArchived = Boolean.toString(r.isArchived());

            sb.append(String.join(",",
                    r.getID().toString(), names, phones, emails, addresses, tags, description, isArchived));
            sb.append("\n");
        }

        return sb.toString();
    }

    private static String escapeCsvField(String value) {
        if (value == null) {
            return "";
        }
        if (value.contains(",") || value.contains("\"")) {
            value = value.replace("\"", "\"\""); // escape quotes
            return "\"" + value + "\"";
        }
        return value;
    }

    /**
     * Reads the CSV file and parses it into a list of recruits.
     *
     * @param filePath Path to the CSV file.
     * @return List of recruits from the CSV.
     * @throws DataLoadingException if reading the file fails.
     */
    public static List<Recruit> deserializeRecruitsFromCsvFile(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);
        try {
            String csvContent = Files.readString(filePath);
            logger.info("CSV file " + filePath + " found.");
            return fromCsvString(csvContent);
        } catch (IOException e) {
            logger.warning("Error reading from csvFile file " + filePath + ": " + e);
            throw new DataLoadingException(e);
        }
    }

    /**
     * Converts a CSV string into a list of recruits.
     * Each row corresponds to a recruit. Fields with multiple values
     * (names, phones, emails, addresses, tags) are separated by ';'.
     *
     * @param csv CSV string to parse.
     * @return List of recruits represented in the CSV string.
     */
    public static List<Recruit> fromCsvString(String csv) {
        requireNonNull(csv);
        String[] lines = csv.split("\n");

        if (lines.length <= 1) {
            return new ArrayList<>();
        }

        List<Recruit> recruits = new ArrayList<>();

        // skip header
        for (int i = 1; i < lines.length; i++) {
            String line = lines[i].trim();
            if (line.isEmpty()) {
                continue;
            }
            String[] cols = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
            if (cols.length < 8) {
                logger.warning("Skipping malformed line (expected 8 columns): " + line);
                continue;
            }
            UUID id = UUID.fromString(cols[0]);
            List<Name> names = Arrays.stream(cols[1].split(";")).map(Name::new).toList();
            List<Phone> phones = Arrays.stream(cols[2].split(";")).map(Phone::new).toList();
            List<Email> emails = Arrays.stream(cols[3].split(";")).map(Email::new).toList();
            List<Address> addresses = Arrays.stream(cols[4].split(";"))
                    .map(s -> s.replaceAll("^\"|\"$", ""))
                    .map(Address::new)
                    .toList();
            Set<Tag> tags = Arrays.stream(cols[5].split(";"))
                    .map(s -> s.replaceAll("^\\[|]$", ""))
                    .map(Tag::new)
                    .collect(Collectors.toSet());
            Description description = new Description(cols[6]);
            boolean isArchived = Boolean.parseBoolean(cols[7]);

            recruits.add(new Recruit(id, names, phones, emails, addresses, description, tags, isArchived));
        }
        return recruits;
    }
}
