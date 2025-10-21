package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Utility methods related to Collections
 */
public class CollectionUtil {

    /** @see #requireAllNonNull(Collection) */
    public static void requireAllNonNull(Object... items) {
        requireNonNull(items);
        Stream.of(items).forEach(Objects::requireNonNull);
    }

    /**
     * Throws NullPointerException if {@code items} or any element of {@code items} is null.
     */
    public static void requireAllNonNull(Collection<?> items) {
        requireNonNull(items);
        items.forEach(Objects::requireNonNull);
    }

    /**
     * Throws IllegalArgumentException if {@code items} is empty.
     */
    public static void requireNonEmpty(Collection<?> items) throws IllegalArgumentException {
        if (items.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Throws IllegalArgumentException if any element of {@code items} is an empty string or
     * string containing only whitespace codepoints.
     */
    public static void requireAllNonBlankString(Collection<? extends String> items) throws IllegalArgumentException {
        if (items.stream().anyMatch(s -> s.isBlank())) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Returns true if {@code items} contain any elements that are non-null.
     */
    public static boolean isAnyNonNull(Object... items) {
        return items != null && Arrays.stream(items).anyMatch(Objects::nonNull);
    }
}
