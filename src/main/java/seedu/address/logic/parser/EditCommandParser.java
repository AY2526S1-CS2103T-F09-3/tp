package seedu.address.logic.parser;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireExactlyOneIsTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.EDIT_PREFIX_APPEND;
import static seedu.address.logic.parser.CliSyntax.EDIT_PREFIX_OVERWRITE;
import static seedu.address.logic.parser.CliSyntax.EDIT_PREFIX_REMOVE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditRecruitDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                        PREFIX_DESCRIPTION, PREFIX_TAG,
                        EDIT_PREFIX_APPEND, EDIT_PREFIX_OVERWRITE, EDIT_PREFIX_REMOVE);

        String preamble = argMultimap.getPreamble().trim();

        UUID id = null;
        Index index = null;

        // Try parsing as UUID first
        try {
            id = ParserUtil.parseID(preamble);
        } catch (ParseException e) {
            // Not a valid UUID, try parsing as index
            try {
                index = ParserUtil.parseIndex(preamble);
            } catch (ParseException e2) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), e2);
            }
        }

        EditRecruitDescriptor.EditRecruitOperation operation = parseEditOperation(argMultimap);
        EditRecruitDescriptor editRecruitDescriptor = new EditCommand.EditRecruitDescriptor(operation);
        LogsCenter.getLogger(EditCommandParser.class).info(editRecruitDescriptor.getOperation().toString());

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editRecruitDescriptor.setNames(
                    ParserUtil.parseAllValues(argMultimap.getAllValues(PREFIX_NAME), ParserUtil::parseName));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            editRecruitDescriptor.setPhones(
                    ParserUtil.parseAllValues(argMultimap.getAllValues(PREFIX_PHONE), ParserUtil::parsePhone));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            editRecruitDescriptor.setEmails(
                    ParserUtil.parseAllValues(argMultimap.getAllValues(PREFIX_EMAIL), ParserUtil::parseEmail));
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            editRecruitDescriptor.setAddresses(
                    ParserUtil.parseAllValues(argMultimap.getAllValues(PREFIX_ADDRESS), ParserUtil::parseAddress));
        }
        if (argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            editRecruitDescriptor.setDescription(
                    ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get()));
        }
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editRecruitDescriptor::setTags);

        if (!editRecruitDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }
        if (id != null) {
            return new EditCommand(id, editRecruitDescriptor);
        } else {
            return new EditCommand(index, editRecruitDescriptor, operation);
        }
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

    private EditRecruitDescriptor.EditRecruitOperation parseEditOperation(ArgumentMultimap argMultiMap)
            throws ParseException {
        boolean isAppend = argMultiMap.getValue(EDIT_PREFIX_APPEND).isPresent();
        boolean isRemove = argMultiMap.getValue(EDIT_PREFIX_REMOVE).isPresent();
        boolean isOverwrite = argMultiMap.getValue(EDIT_PREFIX_OVERWRITE).isPresent() || (!isAppend && !isRemove);

        try {
            requireExactlyOneIsTrue(List.of(isAppend, isOverwrite, isRemove));
        } catch (IllegalArgumentException e) {
            throw new ParseException(
                    String.format(EditCommand.MESSAGE_INVALID_OPERATION, EditCommand.MESSAGE_USAGE), e);
        }

        return isAppend
                ? EditRecruitDescriptor.EditRecruitOperation.APPEND
                : isRemove
                ? EditRecruitDescriptor.EditRecruitOperation.REMOVE
                : EditRecruitDescriptor.EditRecruitOperation.OVERWRITE;
    }
}
