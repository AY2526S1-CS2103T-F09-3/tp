package seedu.address.model.recruit.exceptions;

/**
 * Signals that the operation is unable to find the specified person.
 */
public class RecruitNotFoundException extends RecruitException {
    public RecruitNotFoundException() {
        super();
    }

    public RecruitNotFoundException(String s) {
        super(s);
    }
}
