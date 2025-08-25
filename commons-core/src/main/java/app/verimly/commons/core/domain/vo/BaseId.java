package app.verimly.commons.core.domain.vo;

/**
 * Abstract base class for identifier value objects.
 *
 * @param <T> the type of the identifier value
 */
public abstract class BaseId<T> extends ValueObject<T> {

    /**
     * Constructs a new {@code BaseId} with the given value.
     *
     * @param value the identifier value
     */
    protected BaseId(T value) {
        super(value);
    }

}
