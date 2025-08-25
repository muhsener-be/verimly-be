package app.verimly.user.domain.event;

import app.verimly.commons.core.domain.event.DomainEvent;
import app.verimly.commons.core.domain.exception.Assert;
import app.verimly.user.domain.entity.User;
import lombok.Getter;

@Getter
public class UserCreated implements DomainEvent {

    private final User user;

    private UserCreated(User user) {
        this.user = user;
    }

    public static UserCreated of(User user) {
        Assert.notNull(user, "User cannot be null!");
        return new UserCreated(user);
    }
}
