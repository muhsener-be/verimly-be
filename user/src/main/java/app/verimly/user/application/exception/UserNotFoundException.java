package app.verimly.user.application.exception;

import app.verimly.commons.core.domain.exception.NotFoundException;
import app.verimly.commons.core.domain.vo.UserId;

import static app.verimly.commons.core.domain.exception.Assert.notNull;

public class UserNotFoundException extends NotFoundException {

    public static final String RESOURCE_NAME = "USER";

    public UserNotFoundException(UserId userId) {
        super(RESOURCE_NAME, notNull(userId, "UserId cannot be null when constructing UserNotFoundException").toString());
    }
}
