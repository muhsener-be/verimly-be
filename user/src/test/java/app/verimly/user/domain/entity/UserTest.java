package app.verimly.user.domain.entity;

import app.verimly.commons.core.domain.event.DomainEvent;
import app.verimly.commons.core.domain.exception.ErrorMessage;
import app.verimly.commons.core.domain.vo.Email;
import app.verimly.user.data.UserTestData;
import app.verimly.user.domain.event.UserCreated;
import app.verimly.user.domain.vo.Password;
import app.verimly.user.domain.vo.PersonName;
import app.verimly.user.exception.UserDomainException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {


    UserTestData userTestData = UserTestData.getInstance();

    Email email = userTestData.email();
    PersonName personName = userTestData.personName();
    Password notEncryptedPassword = userTestData.password().raw();
    Password encryptedPassword = userTestData.password().encrypted();


    ErrorMessage NAME_NOT_EXIST_ERROR_MESSAGE = User.Errors.NAME_NOT_EXIST;
    ErrorMessage EMAIL_NOT_EXIST_ERROR_MESSAGE = User.Errors.EMAIL_NOT_EXIST;
    ErrorMessage PASSWORD_NOT_EXIST_ERROR_MESSAGE = User.Errors.PASSWORD_NOT_EXIST;
    ErrorMessage PASSWORD_NOT_ENCRYPTED_ERROR_MESSAGE = User.Errors.NOT_ENCRYPTED_PASSWORD;


    @DisplayName("Create - Happy Path")
    @Test
    public void create_happy_path() {
        User actual = User.create(personName, email, encryptedPassword);

        assertNotNull(actual.getId());
        assertEquals(personName, actual.getName());
        assertEquals(email, actual.getEmail());
        assertEquals(encryptedPassword, actual.getPassword());
    }


    @DisplayName("User must have name.")
    @Test
    public void create_whenPersonNameIsNull_thenThrows() {
        UserDomainException exception = assertThrows(UserDomainException.class,
                () -> User.create(null, email, encryptedPassword));

        ErrorMessage actualErrorMessage = exception.getErrorMessage();
        assertEquals(NAME_NOT_EXIST_ERROR_MESSAGE, actualErrorMessage);

    }

    @DisplayName("User must have email.")
    @Test
    public void create_whenEmailIsNull_thenThrows() {
        UserDomainException exception = assertThrows(UserDomainException.class,
                () -> User.create(personName, null, encryptedPassword));

        ErrorMessage actualErrorMessage = exception.getErrorMessage();
        assertEquals(EMAIL_NOT_EXIST_ERROR_MESSAGE, actualErrorMessage);


    }

    @DisplayName("User must have password.")
    @Test
    public void create_whenPasswordIsNull_thenThrows() {
        UserDomainException exception = assertThrows(UserDomainException.class,
                () -> User.create(personName, email, null));

        ErrorMessage actualErrorMessage = exception.getErrorMessage();
        assertEquals(PASSWORD_NOT_EXIST_ERROR_MESSAGE, actualErrorMessage);
    }

    @Test
    public void create_whenPasswordIsNotEncrypted_thenThrows() {
        UserDomainException exception = assertThrows(UserDomainException.class,
                () -> User.create(personName, email, notEncryptedPassword));

        ErrorMessage acualErrorMessage = exception.getErrorMessage();
        assertEquals(PASSWORD_NOT_ENCRYPTED_ERROR_MESSAGE, acualErrorMessage);
    }


    @Test
    @DisplayName("Reconstruct - Does not check invariants")
    public void reconstruct_doesNotCheckInvariants() {
        User user = User.reconstruct(null, null, null, null);
        assertNotNull(user);
    }


    @Test
    public void create_putsUserCreatedDomainEvent() {
        User user = User.create(personName, email, encryptedPassword);


        List<DomainEvent> domainEvents = user.pullDomainEvents();
        assertEquals(1, domainEvents.size());
        DomainEvent first = domainEvents.getFirst();
        assertInstanceOf(UserCreated.class, first);
        UserCreated userCreated = (UserCreated) first;
        assertEquals(user, userCreated.getUser());

        assertEquals(0, user.pullDomainEvents().size());
    }

}
