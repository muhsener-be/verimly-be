package app.verimly.commons.core.domain.exception;

import lombok.Getter;

@Getter
public class NotFoundException extends ApplicationException {

    private String resourceType;
    private String resourceId;

    public NotFoundException() {
    }

    public NotFoundException(String resourceType, String resourceId) {
        this.resourceType = resourceType;
        this.resourceId = resourceId;
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundException(ErrorMessage errorMessage) {
        super(errorMessage);
    }

    public NotFoundException(ErrorMessage errorMessage, String message) {
        super(errorMessage, message);
    }

    public NotFoundException(ErrorMessage errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }

    public NotFoundException(ErrorMessage errorMessage, String message, Throwable cause) {
        super(errorMessage, message, cause);
    }
}
