package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.model.recruit.Recruit;
import seedu.address.model.tag.Tag;

/**
 * A utility class for Person.
 */
public class RecruitUtil {

    /**
     * Returns an add command string for adding the {@code person}.
     */
    public static String getAddCommand(Recruit recruit) {
        return AddCommand.COMMAND_WORD + " " + getRecruitDetails(recruit);
    }

    /**
     * Returns the part of command string for the given {@code person}'s details.
     */
    public static String getRecruitDetails(Recruit recruit) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + recruit.getName().value + " ");
        sb.append(PREFIX_PHONE + recruit.getPhone().value + " ");
        sb.append(PREFIX_EMAIL + recruit.getEmail().value + " ");
        sb.append(PREFIX_ADDRESS + recruit.getAddress().value + " ");
        sb.append(PREFIX_DESCRIPTION + recruit.getDescription().value + " ");
        recruit.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
     */
    public static String getEditRecruitDescriptorDetails(EditCommand.EditRecruitDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getNames().ifPresent(name -> sb.append(PREFIX_NAME).append(name.first()).append(" "));
        descriptor.getPhones().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.first()).append(" "));
        descriptor.getEmails().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.first()).append(" "));
        descriptor.getAddresses().ifPresent(address -> sb.append(PREFIX_ADDRESS).append(address.first()).append(" "));
        descriptor.getDescription().ifPresent(
                description -> sb.append(PREFIX_DESCRIPTION).append(description).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}
