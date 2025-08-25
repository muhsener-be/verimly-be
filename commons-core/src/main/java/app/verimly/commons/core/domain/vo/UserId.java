package app.verimly.commons.core.domain.vo;

import java.util.UUID;

/**
 * Value object representing a User ID based on UUID.
 * <p>
 * Provides creation, validation, and reconstruction methods for user identifiers.
 * </p>
 */
public class UserId extends BaseId<UUID> {

    /**
     * Protected constructor for UserId value object.
     *
     * @param value the UUID value
     */
    protected UserId(UUID value) {
        super(value);
    }

    /**
     * Creates a new {@code UserId} value object after validation.
     * Returns {@code null} if the input value is {@code null}.
     *
     * @param value the UUID value
     * @return a validated {@code UserId} object or {@code null}
     */
    public static UserId of(UUID value) {
        if (value == null) {
            return null;
        }
        return new UserId(validateAndFormat(value));
    }

    /**
     * Validates and formats the given UUID value.
     * <p>
     * (No validation is currently implemented.)
     * </p>
     *
     * @param value the UUID value
     * @return the UUID value
     */
    public static UUID validateAndFormat(UUID value) {
        // TODO: implement validation/formatting if needed
        return value;
    }

    /**
     * Generates a new {@code UserId} with a random UUID.
     *
     * @return a new {@code UserId} object
     */
    public static UserId random() {
        return of(UUID.randomUUID());
    }

    /**
     * Reconstructs a {@code UserId} value object from a raw value without validation.
     * <p>
     * This method should only be used when mapping from a persistence entity.
     * It does not check invariants.
     * </p>
     *
     * @param value the UUID value
     * @return a new {@code UserId} object
     */
    public static UserId reconstruct(UUID value) {
        return new UserId(value);
    }
}
