package seedu.address.model.recruit.exceptions;

/**
 * Represents an Exception that can occur if one tries to make an <b>invalid</b> {@code Recruit}
 */
public class InvalidRecruitException extends RecruitException {
    public InvalidRecruitException(String s) {
        super(s);
    }
}
