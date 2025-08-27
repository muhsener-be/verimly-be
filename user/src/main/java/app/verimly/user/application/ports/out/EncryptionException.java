package app.verimly.user.application.ports.out;

import app.verimly.commons.core.domain.exception.ErrorMessage;
import app.verimly.commons.core.security.SecurityException;

public class EncryptionException extends SecurityException {

    public static final ErrorMessage ERROR_MESSAGE =
            ErrorMessage.of("encryption.failed", "Password encryption failed.");

    public EncryptionException(String message, Throwable cause) {
        super(ERROR_MESSAGE, message, cause);
    }


}
