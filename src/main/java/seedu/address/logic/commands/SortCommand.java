package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.SORT_PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.SORT_PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.SORT_PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.SORT_PREFIX_PHONE;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.Model;
import seedu.address.model.recruit.Recruit;

/**
 * Sorts the recruits in the address book by specified fields in ascending or descending order.
 * Supports multiple sort criteria with priority from left to right.
 */
public class SortCommand extends Command {
    public static final String COMMAND_WORD = "sort";
    public static final String OPERATION_DESCRIPTOR = "sorting of recruit by %s";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sorts all recruits in the address book by specified fields.\n"
            + "Parameters: "
            + "[" + SORT_PREFIX_NAME + " asc/desc] "
            + "[" + SORT_PREFIX_PHONE + " asc/desc] "
            + "[" + SORT_PREFIX_EMAIL + " asc/desc] "
            + "[" + SORT_PREFIX_ADDRESS + " asc/desc]\n"
            + "Example: " + COMMAND_WORD + " "
            + SORT_PREFIX_NAME + " asc "
            + SORT_PREFIX_PHONE + " desc\n"
            + "Shorthands: " + COMMAND_WORD + " (sorts by name ascending), "
            + COMMAND_WORD + " asc/desc (sorts by name asc/desc)\n";

    public static final String MESSAGE_SUCCESS = "Recruits sorted by: %s";

    private final List<SortCriterion> sortCriteria;

    /**
     * Creates a SortCommand to sort recruits by the specified sort criteria.
     */
    public SortCommand(List<SortCriterion> sortCriteria) {
        requireNonNull(sortCriteria);
        this.sortCriteria = sortCriteria;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        Comparator<Recruit> comparator = buildComparator();
        model.sortRecruits(comparator);

        // Build success message description
        String criteriaDescription = sortCriteria.stream()
                .map(SortCriterion::toString)
                .collect(Collectors.joining(", "));

        model.commitAddressBook(String.format(OPERATION_DESCRIPTOR, criteriaDescription));
        return new CommandResult(String.format(MESSAGE_SUCCESS, criteriaDescription));
    }

    /**
     * Builds a comparator based on the sort criteria, applied in order of priority (left to right).
     */
    private Comparator<Recruit> buildComparator() {
        return sortCriteria.stream()
                .map(SortCriterion::getComparator)
                .reduce(Comparator::thenComparing)
                .get();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof SortCommand)) {
            return false;
        }

        SortCommand otherCommand = (SortCommand) other;
        return sortCriteria.equals(otherCommand.sortCriteria);
    }

    /**
     * Represents a single sort criterion consisting of a field and order.
     */
    public static class SortCriterion {
        private final Prefix prefix;
        private final SortOrder order;

        /**
         * Constructs a SortCriterion, given a prefix and sort order (asc / desc).
         */
        public SortCriterion(Prefix prefix, SortOrder order) {
            this.prefix = prefix;
            this.order = order;
        }

        /**
         * Returns a comparator for this sort criterion, depending on prefix and order.
         */
        public Comparator<Recruit> getComparator() {
            Comparator<Recruit> comparator;
            if (prefix.equals(SORT_PREFIX_NAME)) {
                comparator = Comparator.comparing(recruit -> recruit.getName().fullName.toLowerCase());
            } else if (prefix.equals(SORT_PREFIX_PHONE)) {
                comparator = Comparator.comparing(recruit -> recruit.getPhone().isEmpty() ? ""
                        : recruit.getPhone().get().value.toLowerCase());
            } else if (prefix.equals(SORT_PREFIX_EMAIL)) {
                comparator = Comparator.comparing(recruit -> recruit.getEmail().isEmpty() ? ""
                        : recruit.getEmail().get().value.toLowerCase());
            } else if (prefix.equals(SORT_PREFIX_ADDRESS)) {
                comparator = Comparator.comparing(recruit -> recruit.getAddress().isEmpty() ? ""
                        : recruit.getAddress().get().value.toLowerCase());
            } else {
                throw new IllegalArgumentException("Unknown sort prefix: " + prefix);
            }
            return order == SortOrder.DESCENDING ? comparator.reversed() : comparator;
        }

        @Override
        public String toString() {
            return prefix.toString() + " (" + order.getDisplayName() + ")";
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            if (!(other instanceof SortCriterion)) {
                return false;
            }

            SortCriterion otherCriterion = (SortCriterion) other;
            return prefix.equals(otherCriterion.prefix) && order == otherCriterion.order;
        }
    }

    /**
     * Enum representing the sort order (asc / desc)
     */
    public enum SortOrder {
        ASCENDING("ascending"),
        DESCENDING("descending");

        private final String displayName;

        SortOrder(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }
}

