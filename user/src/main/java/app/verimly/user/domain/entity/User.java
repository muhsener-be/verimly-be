package app.verimly.user.domain.entity;

import app.verimly.commons.core.domain.entity.BaseEntity;
import app.verimly.commons.core.domain.exception.ErrorMessage;
import app.verimly.commons.core.domain.vo.Email;
import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.user.domain.event.UserCreated;
import app.verimly.user.domain.exception.UserDomainException;
import app.verimly.user.domain.vo.Password;
import app.verimly.user.domain.vo.PersonName;
import lombok.Getter;

import static app.verimly.user.domain.entity.User.Errors.PASSWORD_NOT_EXIST;

/**
 * Domain entity representing a user.
 * <p>
 * Encapsulates user identity, name, email, and password.
 * Provides factory methods for creation and reconstruction, as well as domain invariant checks.
 * </p>
 */
@Getter
public class User extends BaseEntity<UserId> {

    private PersonName name;
    private Email email;
    private Password password;


    protected User(UserId id, PersonName name, Email email, Password password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    /**
     * Creates a new User entity, checking all invariants and publishing a UserCreated event.
     *
     * @param name     the user's name
     * @param email    the user's email
     * @param password the user's password
     * @return a new User instance
     * @throws UserDomainException if any invariant is violated
     */
    @org.jetbrains.annotations.Contract("_, _, _ -> new")
    public static User create(PersonName name, Email email, Password password) {
        checkInvariants(name, email, password);

        UserId randomUserId = UserId.random();
        User user = new User(randomUserId, name, email, password);

        UserCreated userCreatedEvent = UserCreated.of(user);
        user.addDomainEvent(userCreatedEvent);
        return user;
    }


    private static void checkInvariants(PersonName name, Email email, Password password) {
        ensureNameExists(name);
        ensureEmailExists(email);
        ensurePasswordExists(password);
        ensurePasswordEncrypted(password);
    }


    private static void ensureNameExists(PersonName name) {
        if (name == null)
            throw new UserDomainException(Errors.NAME_NOT_EXIST);
    }


    private static void ensureEmailExists(Email email) {
        if (email == null)
            throw new UserDomainException(Errors.EMAIL_NOT_EXIST);
    }


    private static void ensurePasswordExists(Password password) {
        if (password == null)
            throw new UserDomainException(PASSWORD_NOT_EXIST);
    }


    private static void ensurePasswordEncrypted(Password password) {
        if (!password.isEncrypted())
            throw new UserDomainException(Errors.NOT_ENCRYPTED_PASSWORD);
    }

    /**
     * Reconstructs a User domain object from persisted data.
     * <p>
     * <b>Note:</b> This method should only be used when mapping from a persistence entity (e.g., ORM or database row).
     * It does <b>not</b> check any invariants or perform validation.
     * </p>
     *
     * @param id       the UserId
     * @param name     the PersonName
     * @param email    the Email
     * @param password the Password
     * @return a User instance
     */
    public static User reconstruct(UserId id, PersonName name, Email email, Password password) {
        return new User(id, name, email, password);
    }

    /**
     * Error messages for the User entity.
     */
    public static final class Errors {
        public static final ErrorMessage NAME_NOT_EXIST = ErrorMessage.of("user.name-not-exist", "User must have a name.");
        public static final ErrorMessage EMAIL_NOT_EXIST = ErrorMessage.of("user.email-not-exist", "User must have an email.");
        public static final ErrorMessage PASSWORD_NOT_EXIST = ErrorMessage.of("user.password-not-exist", "User must have a password.");
        public static final ErrorMessage NOT_ENCRYPTED_PASSWORD = ErrorMessage.of("user.password-not-encrypted", "User password must be encrypted.");
    }
}
