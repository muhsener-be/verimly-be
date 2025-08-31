package app.verimly.user.application.exception;

import app.verimly.commons.core.domain.exception.ErrorMessage;
import app.verimly.commons.core.domain.exception.NotFoundException;
import app.verimly.commons.core.domain.vo.UserId;

public class UserNotFoundException extends NotFoundException {


    public static ErrorMessage ERROR_MESSAGE = ErrorMessage.of("user.not-found", "User not found.");

    public UserNotFoundException(UserId userId) {
        super("USER", userId == null ? null : userId.toString());
    }
}
