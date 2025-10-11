package seedu.address.model.recruit;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class EmailContainsKeywordPredicate implements Predicate<Recruit> {
    private final List<String> keywords;

    public EmailContainsKeywordPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Recruit recruit) {
        return keywords.stream()
                .anyMatch(keyword -> recruit.getEmail().value.toLowerCase()
                        .contains(keyword.toLowerCase()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EmailContainsKeywordPredicate)) {
            return false;
        }

        EmailContainsKeywordPredicate otherNameContainsKeywordsPredicate = (EmailContainsKeywordPredicate) other;
        return keywords.equals(otherNameContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
