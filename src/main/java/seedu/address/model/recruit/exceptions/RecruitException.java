package seedu.address.model.recruit.exceptions;

/**
 * Represents possible exceptions that can occur when using the {@code Recruit} class
 */
public class RecruitException extends RuntimeException {
    public RecruitException() {
        super();
    }

    public RecruitException(String s) {
        super(s);
    }
}
