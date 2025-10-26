package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import seedu.address.model.recruit.data.Address;
import seedu.address.model.recruit.data.Description;
import seedu.address.model.recruit.data.Email;
import seedu.address.model.recruit.data.Name;
import seedu.address.model.recruit.data.Phone;
import seedu.address.model.recruit.Recruit;
import seedu.address.model.recruit.RecruitBuilder;
import seedu.address.model.tag.Tag;

/**
 * Converts a Java object instance to CSV and vice versa
 */
public class CsvUtil {

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
        FileUtil.writeToFile(filePath, csvString);
    }

    /**
     * Converts a list of recruits into a CSV-formatted string.
     *
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
     * @throws IOException if reading the file fails.
     */
    public static List<Recruit> deserializeRecruitsFromCsvFile(Path filePath) throws IOException {
        requireNonNull(filePath);
        String csvContent = Files.readString(filePath);
        return fromCsvString(csvContent);
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
            UUID id = UUID.fromString(cols[0]);
            List<Name> names = Arrays.stream(cols[1].split(";")).map(Name::new).toList();
            List<Phone> phones = Arrays.stream(cols[2].split(";")).map(Phone::new).toList();
            List<Email> emails = Arrays.stream(cols[3].split(";")).map(Email::new).toList();
            List<Address> addresses = Arrays.stream(cols[4].split(";"))
                    .map(s -> s.replaceAll("^\"|\"$", ""))
                    .map(Address::new)
                    .toList();
            List<Tag> tags = Arrays.stream(cols[5].split(";"))
                    .map(s -> s.replaceAll("^\\[|\\]$", ""))
                    .map(Tag::new)
                    .toList();
            Description description = new Description(cols[6]);
            boolean isArchived = Boolean.parseBoolean(cols[7]);

            recruits.add(new RecruitBuilder()
                    .setId(id)
                    .withNames(names)
                    .withPhones(phones)
                    .withEmails(emails)
                    .withAddresses(addresses)
                    .withDescription(description)
                    .withTags(tags)
                    .withArchivalState(isArchived)
                    .build());
        }
        return recruits;
    }
}
