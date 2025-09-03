package app.verimly.task.logging;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

@EqualsAndHashCode
@Getter
public final class Actor {
    private final String type;
    private final UUID id;

    private Actor(String type, UUID id) {
        this.type = type;
        this.id = id;
    }

    public static Actor user(UUID userId) {
        return new Actor("user", userId);
    }

    public static Actor system() {
        return new Actor("system", null);
    }


    @Override
    public String toString() {
        return type + (id == null ? "" : ":" + id);
    }
}
