package app.verimly.commons.core.security;

import app.verimly.commons.core.domain.exception.ErrorMessage;
import app.verimly.commons.core.domain.exception.InvalidDomainObjectException;
import app.verimly.commons.core.domain.vo.Email;
import app.verimly.commons.core.domain.vo.UserId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AuthenticatedPrincipalTest {

    UserId userId = UserId.random();
    Email email = Email.of("test@gmail.com");

    @Test
    void of_whenValidArguments_thenReturnsAuthenticatedPrincipalInstance() {
        AuthenticatedPrincipal actual = AuthenticatedPrincipal.of(userId, email);

        assertEquals(userId, actual.getId());
        assertEquals(email, actual.getEmail());

    }

    @Test
    void of_whenNullUserId_thenThrowsInvalidDomainObjectException() {
        userId = null;

        Executable action = () -> AuthenticatedPrincipal.of(userId, email);


        InvalidDomainObjectException exception = assertThrows(InvalidDomainObjectException.class, action);
        ErrorMessage actualErrorMessage = exception.getErrorMessage();
        ErrorMessage expectedErrorMessage = AuthenticatedPrincipal.Errors.ID_NOT_EXIST;

        assertEquals(expectedErrorMessage,actualErrorMessage);


    }


    @Test
    void of_whenNullEmail_thenThrowsInvalidDomainObjectException() {
        email = null;

        Executable action = () -> AuthenticatedPrincipal.of(userId, email);


        InvalidDomainObjectException exception = assertThrows(InvalidDomainObjectException.class, action);
        ErrorMessage actualErrorMessage = exception.getErrorMessage();
        ErrorMessage expectedErrorMessage = AuthenticatedPrincipal.Errors.EMAIL_NOT_EXIST;

        assertEquals(expectedErrorMessage,actualErrorMessage);


    }
}