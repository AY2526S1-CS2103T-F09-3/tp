package seedu.address.model.recruit.exceptions;

import java.util.List;

/**
 * Represents an exception that is triggered when trying to add an element that already exists to a field.
 */
public class FieldElementAlreadyExistsException extends IllegalRecruitBuilderActionException {
    private String fieldType;
    private List<String> duplicates;

    public FieldElementAlreadyExistsException(String s) {
        super(s);
    }

    public FieldElementAlreadyExistsException(String fieldType, List<String> duplicatedElements) {
        super(String.format("The following %ss are duplicated: %s", fieldType, duplicatedElements));
        this.fieldType = fieldType;
        this.duplicates = duplicatedElements;
    }

    public List<String> getDuplicatedElements() {
        return duplicates;
    }

    public String getFieldType() {
        return fieldType;
    }
}
