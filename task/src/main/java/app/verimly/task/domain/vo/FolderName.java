package app.verimly.task.domain.vo;

import app.verimly.commons.core.domain.exception.ErrorMessage;
import app.verimly.commons.core.domain.exception.InvalidDomainObjectException;
import app.verimly.commons.core.domain.vo.ValueObject;

public class FolderName extends ValueObject<String> {

    public static final int MAX_LENGTH = 50;

    protected FolderName(String value) {
        super(value);
    }

    public static FolderName of(String value) {
        if (value == null || value.isBlank())
            return null;

        String normalizedName = normalize(value);
        validateRules(normalizedName);

        return new FolderName(normalizedName);
    }


    public static FolderName reconstruct(String value) {
        return value == null ? null : new FolderName(value);
    }

    private static void validateRules(String value) {
        if (value.length() > MAX_LENGTH)
            throw new InvalidDomainObjectException(Errors.LENGTH);
    }

    private static String normalize(String value) {
        assert value != null : "value cannot be null to normalize";

        return value.trim().replaceAll("\\s+", " ");
    }


    public static final class Errors {
        public static final ErrorMessage LENGTH = new ErrorMessage("folder-name.length", "Folder name must be between 1 and %s characters".formatted(MAX_LENGTH));
    }
}
