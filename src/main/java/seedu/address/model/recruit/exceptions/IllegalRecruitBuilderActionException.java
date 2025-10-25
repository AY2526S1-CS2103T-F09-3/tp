package seedu.address.model.recruit.exceptions;

/**
 * Represents exceptions that can occur when you perform an illegal action on a RecruitBuilder.
 */
public class IllegalRecruitBuilderActionException extends RecruitException {
    public IllegalRecruitBuilderActionException(String message) {
        super(message);
    }
}
