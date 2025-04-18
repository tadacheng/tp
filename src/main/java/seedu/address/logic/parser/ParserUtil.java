package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

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
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.replaceAll("\\s+", " ").trim();
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
        String trimmedPhone = phone.replaceAll("\\s+", "").trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
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
     * Parses the given date string into a {@code LocalDate}.
     */
    public static LocalDate parseDate(String dateStr) throws ParseException {
        try {
            return LocalDate.parse(dateStr);
        } catch (DateTimeParseException e) {
            throw new ParseException("Invalid date format! Expected format: yyyy-MM-dd");
        }
    }

    /**
     * Parses the given time string into a {@code LocalTime}.
     */
    public static LocalTime parseTime(String timeStr) throws ParseException {
        try {
            return LocalTime.parse(timeStr);
        } catch (DateTimeParseException e) {
            throw new ParseException("Invalid time format! Expected format: HH:mm");
        }
    }

    /**
     * Parses the given duration string into a {@code Duration}.
     */
    public static Duration parseDuration(String durationStr) throws ParseException {
        try {
            // Ensure the string matches the expected format: XhYm or Xh
            durationStr = durationStr.trim();

            // Split the duration string into hours and minutes
            String[] parts = durationStr.split("h");
            long hours = Long.parseLong(parts[0].trim()); // Get the hours

            long minutes = 0; // Default minutes is 0
            if (parts.length > 1) {
                String minutesPart = parts[1].trim();

                // Check if the minutes part ends with 'm'
                if (minutesPart.endsWith("m")) {
                    minutes = Long.parseLong(minutesPart.substring(0, minutesPart.length() - 1).trim());
                } else {
                    throw new ParseException("Invalid duration format! Expected format: XhYm (e.g., 1h30m)");
                }
            }

            // Return the Duration object with hours and minutes
            return Duration.ofHours(hours).plusMinutes(minutes);

        } catch (NumberFormatException | ArrayIndexOutOfBoundsException | StringIndexOutOfBoundsException e) {
            throw new ParseException("Invalid duration format! Expected format: XhYm (e.g., 1h30m)");
        }
    }

    /**
     * Parses a {@code String eventStr} into a valid event name.
     * Ensures that the string is non-empty and trimmed.
     * @param eventStr The string representing the event.
     * @return A trimmed, valid event string.
     * @throws ParseException If the input string is empty or invalid.
     */
    public static String parseEvent(String eventStr) throws ParseException {
        requireNonNull(eventStr);
        String trimmedEvent = eventStr.trim();
        if (trimmedEvent.isEmpty()) {
            throw new ParseException("Event name cannot be empty!");
        }
        return trimmedEvent;
    }
}
