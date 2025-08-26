package app.verimly.user.domain.repository;

import app.verimly.commons.core.domain.exception.DataAccessException;

public class UserDataAccessException extends DataAccessException {


    public UserDataAccessException(String message) {
        super(message);
    }

    public UserDataAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
