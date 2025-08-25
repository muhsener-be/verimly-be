package app.verimly.commons.core.domain.vo;

import java.util.UUID;

public class UserId extends BaseId<UUID> {

    protected UserId(UUID value) {
        super(value);
    }

    public static UserId of(UUID value) {
        if (value == null) {
            return null;
        }
        return new UserId(validateAndFormat(value));
    }

    public static UUID validateAndFormat(UUID value) {
        // TODO: implement validation/formatting if needed
        return value;
    }

    public static UserId random() {
        return of(UUID.randomUUID());
    }

    public static UserId reconstruct(UUID value) {
        return new UserId(value);
    }
}
