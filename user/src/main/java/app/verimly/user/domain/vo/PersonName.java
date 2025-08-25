package app.verimly.user.domain.vo;

import app.verimly.commons.core.domain.exception.ErrorMessage;
import app.verimly.commons.core.domain.exception.InvalidDomainObjectException;
import lombok.Getter;

@Getter
public class PersonName {

    public static final int NAME_PART_MAX_LENGTH = 50;
    private final String firstName;
    private final String lastName;


    protected PersonName(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public static PersonName of(String firstName, String lastName) {
        if ((firstName == null || firstName.isBlank()) && (lastName == null || lastName.isBlank())) return null;
        String normalizedFirstName = validateAndNormalizeNamePart(firstName);
        String normalizedLastName = validateAndNormalizeNamePart(lastName);

        return new PersonName(normalizedFirstName, normalizedLastName);
    }

    public static PersonName reconstruct(String firstName, String lastName) {
        return new PersonName(firstName, lastName);
    }

    private static String validateAndNormalizeNamePart(String namePart) {
        String normalized = normalizeNamePart(namePart);
        if (normalized == null || longerThanMaxLength(normalized)) {
            throw new InvalidDomainObjectException(Errors.NAME_PART);
        }

        return normalized;
    }

    private static boolean longerThanMaxLength(String normalized) {
        return normalized.length() > NAME_PART_MAX_LENGTH;
    }

    private static String normalizeNamePart(String namePart) {
        if (namePart == null || namePart.isBlank()) {
            return null;
        }
        return namePart.trim().replaceAll("\\s+", " ");
    }

    public static final class Errors {
        public static final ErrorMessage NAME_PART = ErrorMessage.of("person-name.name-part", "Name part should be between 1 and 50 characters.");
    }
}
