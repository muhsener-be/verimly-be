package app.verimly.commons.core.security;

import app.verimly.commons.core.domain.exception.ErrorMessage;

public class AuthenticationRequiredException extends SecurityException {


    public static final ErrorMessage ERROR_MESSAGE = ErrorMessage.of("unauthorized", "Authentication is required.");

    public AuthenticationRequiredException(String message) {
        super(ERROR_MESSAGE, message);
    }


    public AuthenticationRequiredException(Action action) {
        super(ERROR_MESSAGE, "Authentication required to " + action);
    }

}
