package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.recruit.Address;
import seedu.address.model.recruit.Description;
import seedu.address.model.recruit.Email;
import seedu.address.model.recruit.Name;
import seedu.address.model.recruit.Phone;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INVALID_ID = "ID is not in UUID format";
    public static final String MESSAGE_EMPTY_FILEPATH = "The file path provided is empty";
    public static final String MESSAGE_INVALID_FILEPATH = "The file path provided is invalid";
    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String id} into a {@code ID}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code id} is invalid.
     */
    public static UUID parseID(String id) throws ParseException {
        requireNonNull(id);
        String trimmedId = id.trim();
        try {
            return UUID.fromString(trimmedId);
        } catch (IllegalArgumentException e) {
            throw new ParseException(MESSAGE_INVALID_ID);
        }
    }
    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String description} into an {@code Description}.
     * Leading and trailing whitespaces will be trimmed.
     * Note that we allow null to be returned as a {@code Description},
     * indicating no description provided
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Description parseDescription(String description) throws ParseException {
        if (description == null) {
            return null;
        }
        String trimmedDescription = description.trim();
        if (!Description.isValidDescription(trimmedDescription)) {
            throw new ParseException(Description.MESSAGE_CONSTRAINTS);
        }
        return new Description(trimmedDescription);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses a {@code String pathStr} into a {@code Path} object after validating it.
     * The method ensures that the provided path string is non-empty, syntactically valid,
     * and that its parent directory (if any) exists in the file system.
     *
     * @param pathStr The string representing the file path to parse.
     * @return A {@code Path} object representing the specified file path.
     * @throws ParseException If the path string is invalid, empty, or refers to a non-existent directory.
     */
    public static Path parsePath(String pathStr) throws ParseException {
        try {
            requireNonNull(pathStr);
            String trimmed = pathStr.trim();
            if (trimmed.isEmpty()) {
                throw new ParseException(MESSAGE_EMPTY_FILEPATH);
            }
            Path path = Paths.get(trimmed);
            if (Files.exists(path) && !Files.isWritable(path)) {
                throw new ParseException(MESSAGE_INVALID_FILEPATH);
            }
            Path parent = path.getParent();
            if (parent != null && !Files.exists(parent)) {
                throw new ParseException(MESSAGE_INVALID_FILEPATH);
            }
            return path;
        } catch (InvalidPathException e) {
            throw new ParseException(MESSAGE_INVALID_FILEPATH, e);
        }
    }

    /**
     * Parses all values from ArgumentMultimap for the given prefix using the given parser.
     */
    private static <T> List<T> parseAllValues(
            List<String> values, ParserFunction<String, T> parser) throws ParseException {
        List<T> parsedValues = new ArrayList<>();
        for (String s : values) {
            parsedValues.add(parser.apply(s));
        }

        return parsedValues;
    }

    /**
     * Uses the result of extracting {@link Prefix} values from the provided {@link ArgumentMultimap}
     * and uses the provided {@link ParserFunction} to parse the values into the appropriate types.
     * It stores these values in a {@link List} of that type.
     *
     * @param prefix The prefix to search for within the ArgumentMultimap
     * @param map The processed ArgumentMultimap
     * @param parserFunction the function that converts the List of Strings extracted from
     *                       the ArgumentMultiMap into it's appropriate type
     * @return null if the corresponding prefix does not exist in the map.
     *         A List of items of type T if the prefix exists in the map.
     * @throws ParseException If the ParserFunction is unable to parse the List of Strings extracted
     *                        from the ArgumentMultimap
     */
    public static <T> List<T> extractValuesFromMultimap(
            Prefix prefix, ArgumentMultimap map,
            ParserFunction<String, T> parserFunction)
            throws ParseException {
        if (!map.hasValue(prefix)) {
            return null;
        }

        return ParserUtil.parseAllValues(map.getAllValues(prefix), parserFunction);
    }

    /**
     * Uses the result of extracting {@link Prefix} values from the provided {@link ArgumentMultimap}
     * and uses the provided {@link ParserFunction} to parse the values into the appropriate types.
     * It returns only parses and returns the last element.
     *
     * @param prefix The prefix to search for within the ArgumentMultimap
     * @param map The processed ArgumentMultimap
     * @param parserFunction the function that converts the List of Strings extracted from
     *                       the ArgumentMultiMap into it's appropriate type
     * @return null if the corresponding prefix does not exist in the map.
     *         The last element of type T that is found.
     * @throws ParseException If the ParserFunction is unable to parse the List of Strings extracted
     *                        from the ArgumentMultimap
     */
    public static <T> T extractValueFromMultimap(
            Prefix prefix, ArgumentMultimap map,
            ParserFunction<String, T> parserFunction) throws ParseException {
        if (!map.hasValue(prefix)) {
            return null;
        }

        return parserFunction.apply(map.getValue(prefix).orElse(null));
    }

    /**
     * Functional interface with checked exceptions.
     */
    @FunctionalInterface
    public interface ParserFunction<T, R> {
        R apply(T t) throws ParseException;
    }
}
