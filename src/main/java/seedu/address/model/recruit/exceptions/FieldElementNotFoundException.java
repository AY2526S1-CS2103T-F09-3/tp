package seedu.address.model.recruit.exceptions;

import java.util.List;

/**
 * Represents an exception that is triggered when attempting to perform an action on
 * a field element that cannot be found.
 */
public class FieldElementNotFoundException extends IllegalRecruitBuilderActionException {
    private List<String> missingElements;
    private String fieldType;

    public FieldElementNotFoundException(String s) {
        super(s);
    }

    public FieldElementNotFoundException(String fieldType, List<String> missingElements) {
        super(String.format("The following %ss cannot be found: %s", fieldType, missingElements));
        this.fieldType = fieldType;
        this.missingElements = missingElements;
    }

    public List<String> getMissingElements() {
        return missingElements;
    }

    public String getFieldType() {
        return fieldType;
    }
}
