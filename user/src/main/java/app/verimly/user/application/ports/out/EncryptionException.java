package app.verimly.user.application.ports.out;

import app.verimly.commons.core.domain.exception.SecurityException;

public class EncryptionException extends SecurityException {

    public EncryptionException(String message) {
        super(message);
    }
}
