package seedu.address.model.recruit;

import static seedu.address.logic.parser.CliSyntax.SEARCH_PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.SEARCH_PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.SEARCH_PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.SEARCH_PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.SEARCH_PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.SEARCH_PREFIX_TAG;

import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.parser.Prefix;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class FieldContainsKeywordsPredicate implements Predicate<Recruit> {
    private final List<String> keywords;
    private final Prefix prefix;

    /**
     * Constructor to create an instance of the FieldContainsKeywordsPredicate
     * @param keywords -  list of keywords provided for search
     * @param prefix - type of parameter being searched
     */
    public FieldContainsKeywordsPredicate(List<String> keywords, Prefix prefix) {
        this.keywords = keywords.stream().map(s -> s.toLowerCase()).toList();
        this.prefix = prefix;
    }

    @Override
    public boolean test(Recruit recruit) {
        if (prefix.equals(SEARCH_PREFIX_ID)) {
            return keywords.stream()
                    .anyMatch(keyword -> recruit.getID().equals(UUID.fromString(keyword)));
        } else if (prefix.equals(SEARCH_PREFIX_NAME)) {
            return keywords.stream()
                    .anyMatch(keyword -> recruit.getNames().stream()
                            .anyMatch(n -> n.fullName.toLowerCase().contains(keyword)));
        } else if (prefix.equals(SEARCH_PREFIX_EMAIL)) {
            return keywords.stream()
                    .anyMatch(keyword -> recruit.getEmails().stream()
                            .anyMatch(e -> e.value.toLowerCase().contains(keyword)));
        } else if (prefix.equals(SEARCH_PREFIX_PHONE)) {
            return keywords.stream()
                    .anyMatch(keyword -> recruit.getPhones().stream()
                            .anyMatch(p -> p.value.contains(keyword)));
        } else if (prefix.equals(SEARCH_PREFIX_ADDRESS)) {
            return keywords.stream()
                    .anyMatch(keyword -> recruit.getAddresses().stream()
                            .anyMatch(a -> a.value.toLowerCase().contains(keyword)));
        } else if (prefix.equals(SEARCH_PREFIX_TAG)) {
            return keywords.stream()
                    .anyMatch(keyword -> recruit.getTags().stream()
                            .anyMatch(tag -> tag.tagName.toLowerCase().contains(keyword)));
        } else {
            return false;
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FieldContainsKeywordsPredicate)) {
            return false;
        }

        FieldContainsKeywordsPredicate otherFieldContainsKeywordsPredicate =
                (FieldContainsKeywordsPredicate) other;
        return keywords.equals(otherFieldContainsKeywordsPredicate.keywords)
                && prefix.equals(otherFieldContainsKeywordsPredicate.prefix);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).add("prefix", prefix).toString();
    }
}
