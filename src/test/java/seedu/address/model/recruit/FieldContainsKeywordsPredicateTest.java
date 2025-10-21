package seedu.address.model.recruit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.SEARCH_PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.SEARCH_PREFIX_TAG;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.RecruitBuilder;

public class FieldContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        FieldContainsKeywordsPredicate firstPredicate = new FieldContainsKeywordsPredicate(
                firstPredicateKeywordList, SEARCH_PREFIX_NAME);
        FieldContainsKeywordsPredicate secondPredicate = new FieldContainsKeywordsPredicate(
                secondPredicateKeywordList, SEARCH_PREFIX_NAME);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        FieldContainsKeywordsPredicate firstPredicateCopy = new FieldContainsKeywordsPredicate(
                firstPredicateKeywordList, SEARCH_PREFIX_NAME);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        FieldContainsKeywordsPredicate predicate = new FieldContainsKeywordsPredicate(
                Collections.singletonList("Alice"), SEARCH_PREFIX_NAME);
        assertTrue(predicate.test(new RecruitBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        predicate = new FieldContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"), SEARCH_PREFIX_NAME);
        assertTrue(predicate.test(new RecruitBuilder().withName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new FieldContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"), SEARCH_PREFIX_NAME);
        assertTrue(predicate.test(new RecruitBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new FieldContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"), SEARCH_PREFIX_NAME);
        assertTrue(predicate.test(new RecruitBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        FieldContainsKeywordsPredicate predicate = new FieldContainsKeywordsPredicate(
                Collections.emptyList(), SEARCH_PREFIX_NAME);
        assertFalse(predicate.test(new RecruitBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new FieldContainsKeywordsPredicate(Arrays.asList("Carol"), SEARCH_PREFIX_NAME);
        assertFalse(predicate.test(new RecruitBuilder().withName("Alice Bob").build()));

        // Keywords match phone, email and address, but does not match name
        predicate = new FieldContainsKeywordsPredicate(
                Arrays.asList("12345", "alice@email.com", "Main", "Street"), SEARCH_PREFIX_NAME);
        assertFalse(predicate.test(new RecruitBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build()));

        // Keywords match tag
        predicate = new FieldContainsKeywordsPredicate(List.of("friend", "colleague"), SEARCH_PREFIX_TAG);
        assertTrue(predicate.test(new RecruitBuilder().withName("Alice").withPhone("88888888")
                .withEmail("email@example.com").withTags("oldFriend", "colleague").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        FieldContainsKeywordsPredicate predicate = new FieldContainsKeywordsPredicate(keywords, SEARCH_PREFIX_NAME);

        String expected = FieldContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords
                + ", prefix=" + SEARCH_PREFIX_NAME + "}";
        assertEquals(expected, predicate.toString());
    }
}
