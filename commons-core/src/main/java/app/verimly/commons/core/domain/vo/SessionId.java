package app.verimly.commons.core.domain.vo;

import java.util.UUID;

public class SessionId extends BaseId<UUID> {

    protected SessionId(UUID value) {
        super(value);
    }

    public static SessionId of(UUID value) {
        return value == null ? null : new SessionId(value);
    }

    public static SessionId random() {
        return new SessionId(UUID.randomUUID());
    }
}
