package app.verimly.commons.core.domain.vo;

import app.verimly.commons.core.domain.exception.Assert;
import lombok.Getter;

import java.util.Objects;

@Getter
public abstract class ValueObject<T> {

    private final T value;

    public ValueObject(T value) {
        this.value = Assert.notNull(value, "%s value cannot be null!".formatted(getClass().getSimpleName()));
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ValueObject<?> that = (ValueObject<?>) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

}
