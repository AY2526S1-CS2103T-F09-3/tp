package seedu.address.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.recruit.Recruit;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_RECRUIT_DISPLAYED_INDEX = "The recruit index provided is invalid";
    public static final String MESSAGE_INVALID_RECRUIT_ID = "The recruit id provided is invalid";
    public static final String MESSAGE_RECRUITS_LISTED_OVERVIEW = "%1$d recruits listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";

    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Formats the {@code person} for display to the user.
     */
    public static String format(Recruit recruit) {
        final StringBuilder builder = new StringBuilder();
        builder.append(recruit.getName());

        recruit.getPhones().stream().findFirst().ifPresent(phone ->
                builder.append("\n Phone: ").append(phone)
        );

        recruit.getEmails().stream().findFirst().ifPresent(email ->
                builder.append("\n Email: ").append(email)
        );

        recruit.getAddresses().stream().findFirst().ifPresent(address ->
                builder.append("\n Address: ").append(address)
        );

        builder.append("\n Description: ").append(recruit.getDescription());

        builder.append("\n Tags: ");
        recruit.getTags().forEach(tag -> builder.append(tag).append(" "));

        return builder.toString();
    }

}
