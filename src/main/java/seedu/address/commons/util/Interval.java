package seedu.address.commons.util;

/**
 * Represents a generic interval of anything comparable.
 */
public class Interval<T extends Comparable<T>> {
    private T start;
    private T end;

    /**
     * Constructs an interval with the given endpoints.
     * @param start
     * @param end
     */
    public Interval(T start, T end) {
        this.start = start;
        this.end = end;
    }

    public boolean contains(T t) {
        return start.compareTo(t) <= 0 && end.compareTo(t) >= 0;
    }
}
