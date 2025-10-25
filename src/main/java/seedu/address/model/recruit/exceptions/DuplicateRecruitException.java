package seedu.address.model.recruit.exceptions;

/**
 * Signals that the operation will result in duplicate Persons (Persons are considered duplicates if they have the same
 * identity).
 */
public class DuplicateRecruitException extends RecruitException {
    public DuplicateRecruitException() {
        super("Operation would result in duplicate recruits");
    }
}
