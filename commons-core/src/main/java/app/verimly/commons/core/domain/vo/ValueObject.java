package app.verimly.commons.core.domain.vo;

import app.verimly.commons.core.domain.exception.Assert;
import lombok.Getter;

import java.util.Objects;

/**
 * Abstract base class for value objects.
 * <p>
 * Implements equality and hash code based on the value.
 * </p>
 *
 * @param <T> the type of the value
 */
@Getter
public abstract class ValueObject<T> {

    /**
     * The underlying value of the value object.
     */
    protected final T value;

    /**
     * Constructs a new {@code ValueObject} with the given value.
     * Throws an exception if the value is {@code null}.
     *
     * @param value the value of the value object
     */
    public ValueObject(T value) {
        this.value = Assert.notNull(value, "%s value cannot be null!".formatted(getClass().getSimpleName()));
    }

    /**
     * Returns the string representation of the value.
     *
     * @return the string representation of the value
     */
    @Override
    public String toString() {
        return value.toString();
    }

    /**
     * Checks equality based on the value and class type.
     *
     * @param o the object to compare
     * @return {@code true} if the objects are equal, {@code false} otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ValueObject<?> that = (ValueObject<?>) o;
        return Objects.equals(value, that.value);
    }

    /**
     * Returns the hash code based on the value.
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }


}
