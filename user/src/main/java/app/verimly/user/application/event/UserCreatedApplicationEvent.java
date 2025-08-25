package app.verimly.user.application.event;

import app.verimly.commons.core.application.event.ApplicationEvent;
import app.verimly.user.domain.entity.User;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
public final class UserCreatedApplicationEvent extends ApplicationEvent {

    private final User user;

    private UserCreatedApplicationEvent(Instant occurredAt, User user) {
        super(occurredAt);
        this.user = user;
    }

    @Builder
    public static UserCreatedApplicationEvent of(User user) {
        return new UserCreatedApplicationEvent(Instant.now(), user);
    }
}
