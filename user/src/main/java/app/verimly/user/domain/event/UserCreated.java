package app.verimly.user.domain.event;

import app.verimly.commons.core.domain.event.DomainEvent;
import app.verimly.commons.core.domain.exception.Assert;
import app.verimly.user.domain.entity.User;
import lombok.Getter;

/**
 * Domain event indicating that a user has been created.
 * <p>
 * Contains a reference to the created {@link User} entity.
 * </p>
 */
@Getter
public class UserCreated implements DomainEvent {

    private final User user;


    private UserCreated(User user) {
        this.user = user;
    }

    /**
     * Factory method to create a new {@code UserCreated} event.
     *
     * @param user the created user
     * @return a new {@code UserCreated} event
     * @throws IllegalArgumentException if user is null
     */
    public static UserCreated of(User user) {
        Assert.notNull(user, "User cannot be null!");
        return new UserCreated(user);
    }
}
