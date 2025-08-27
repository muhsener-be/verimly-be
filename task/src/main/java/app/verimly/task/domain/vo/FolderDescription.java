package app.verimly.task.domain.vo;

import app.verimly.commons.core.domain.exception.ErrorMessage;
import app.verimly.commons.core.domain.exception.InvalidDomainObjectException;
import app.verimly.commons.core.domain.vo.ValueObject;

public class FolderDescription extends ValueObject<String> {

    public static final int MAX_LENGTH = 240;

    protected FolderDescription(String value) {
        super(value);
    }

    public static FolderDescription of(String value) {
        if (value == null || value.isBlank()) return null;

        FolderDescription description = new FolderDescription(value);

        description.checkInvariants();

        return description;
    }

    private void checkInvariants() {
        checkLength();
    }

    private void checkLength() {
        if (value.length() > MAX_LENGTH)
            throw new InvalidDomainObjectException(Errors.LENGTH);
    }


    public static final class Errors {
        public static final ErrorMessage LENGTH = ErrorMessage.of("folder-description.length", "Folder description must be shorter than 240 characters.");
    }
}
