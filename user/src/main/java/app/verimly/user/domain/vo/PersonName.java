package app.verimly.user.domain.vo;

import app.verimly.commons.core.domain.exception.ErrorMessage;
import app.verimly.commons.core.domain.exception.InvalidDomainObjectException;
import lombok.Getter;

import java.util.Objects;

/**
 * Value object representing a person's name.
 * <p>
 * Encapsulates the first and last name, with normalization and validation logic.
 * </p>
 */
@Getter
public class PersonName {

    public static final int NAME_PART_MAX_LENGTH = 50;
    private final String firstName;
    private final String lastName;


    protected PersonName(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Creates a new PersonName value object after validation and normalization.
     * Returns null if both first and last names are null or blank.
     *
     * @param firstName the first name
     * @param lastName  the last name
     * @return a new PersonName object or null
     * @throws InvalidDomainObjectException if validation fails
     */
    public static PersonName of(String firstName, String lastName) {
        if ((firstName == null || firstName.isBlank()) && (lastName == null || lastName.isBlank())) return null;
        String normalizedFirstName = validateAndNormalizeNamePart(firstName);
        String normalizedLastName = validateAndNormalizeNamePart(lastName);

        return new PersonName(normalizedFirstName, normalizedLastName);
    }

    /**
     * Reconstructs a PersonName value object from raw values without validation.
     * <p>
     * This method should only be used when mapping from a persistence entity.
     * It does not check invariants.
     * </p>
     *
     * @param firstName the first name
     * @param lastName  the last name
     * @return a new PersonName object
     */
    public static PersonName reconstruct(String firstName, String lastName) {
        return new PersonName(firstName, lastName);
    }

    /**
     * Validates and normalizes a name part (first or last name).
     *
     * @param namePart the name part to validate and normalize
     * @return the normalized name part
     * @throws InvalidDomainObjectException if validation fails
     */
    private static String validateAndNormalizeNamePart(String namePart) {
        String normalized = normalizeNamePart(namePart);
        if (normalized == null || longerThanMaxLength(normalized)) {
            throw new InvalidDomainObjectException(Errors.NAME_PART);
        }

        return normalized;
    }

    /**
     * Checks if the normalized name part exceeds the maximum allowed length.
     *
     * @param normalized the normalized name part
     * @return true if longer than max length, false otherwise
     */
    private static boolean longerThanMaxLength(String normalized) {
        return normalized.length() > NAME_PART_MAX_LENGTH;
    }

    /**
     * Normalizes a name part by trimming and collapsing whitespace.
     *
     * @param namePart the name part to normalize
     * @return the normalized name part, or null if input is null or blank
     */
    private static String normalizeNamePart(String namePart) {
        if (namePart == null || namePart.isBlank()) {
            return null;
        }
        return namePart.trim().replaceAll("\\s+", " ");
    }

    /**
     * Returns full name by concatenating {@link #firstName}
     * and {@link #lastName} with an empty character between them as separator.
     *
     * @return full name
     */
    @Override
    public String toString() {
        return firstName + " " + lastName;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        PersonName that = (PersonName) object;
        return Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }

    /**
     * Error messages for the PersonName value object.
     */
    public static final class Errors {
        public static final ErrorMessage NAME_PART = ErrorMessage.of("person-name.name-part", "Name part should be between 1 and 50 characters.");
    }
}
