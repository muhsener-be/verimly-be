package app.verimly.user.domain.vo;

import app.verimly.commons.core.domain.exception.Assert;
import app.verimly.commons.core.domain.exception.ErrorMessage;
import app.verimly.commons.core.domain.exception.InvalidDomainObjectException;
import lombok.Getter;

/**
 * Value object representing a user's password.
 * <p>
 * Supports raw and encrypted password states, validation, and reconstruction from persistence.
 * </p>
 */
@Getter
public class Password {
    public static final int MIN_LENGTH = 8;
    public static final int MAX_LENGTH = 30;
    private String raw;
    private String encrypted;


    private Password(String raw, String encrypted) {
        this.raw = raw;
        this.encrypted = encrypted;
    }

    /**
     * Creates a Password value object from a raw password string.
     * Performs validation on whitespace and length.
     *
     * @param raw the raw password string
     * @return a new Password object or null if raw is null
     * @throws InvalidDomainObjectException if validation fails
     */
    public static Password withRaw(String raw) {
        if (raw == null)
            return null;

        ensureDoesNotContainWhiteSpace(raw);
        ensureLengthIsValid(raw);
        return new Password(raw, null);
    }

    /**
     * Encrypts the password, setting the encrypted value and clearing the raw value.
     *
     * @param encrypted the encrypted password string
     * @throws IllegalArgumentException if encrypted is null or blank
     */
    public void encrypt(String encrypted) {
        this.encrypted = Assert.notBlank(encrypted, "Encrypted password cannot be null or blank");
        this.raw = "";
    }

    /**
     * Reconstructs a Password value object from an encrypted password string.
     * <p>
     * This method should only be used when mapping from a persistence entity.
     * It does not check invariants.
     * </p>
     *
     * @param encrypted the encrypted password string
     * @return a new Password object
     */
    public static Password reconstruct(String encrypted) {
        return new Password("", encrypted);
    }


    private static void ensureDoesNotContainWhiteSpace(String raw) {
        assert raw != null : "raw password argument cannot be null!";
        for (int i = 0; i < raw.length(); i++) {
            char ch = raw.charAt(i);
            if (Character.isWhitespace(ch))
                throw new InvalidDomainObjectException(Errors.WHITE_SPACE);
        }
    }


    private static void ensureLengthIsValid(String raw) {
        assert raw != null : "raw password cannot be null!";
        int length = raw.length();
        if (length < MIN_LENGTH || length > MAX_LENGTH)
            throw new InvalidDomainObjectException(Errors.LENGTH);
    }

    /**
     * Checks if the password is encrypted.
     *
     * @return true if encrypted, false otherwise
     */
    public boolean isEncrypted() {
        return encrypted != null;
    }

    /**
     * Error messages for the Password value object.
     */
    public static class Errors {
        public static final ErrorMessage WHITE_SPACE = ErrorMessage.of("password.white-space", "Password cannot contain white space.");
        public static final ErrorMessage LENGTH = ErrorMessage.of("password.length", "Password length must be between 8 and 30 characters.");
    }

    /**
     * Returns a masked string representation of the password.
     *
     * @return a masked password string
     */
    @Override
    public String toString() {
        return "************";
    }
}
