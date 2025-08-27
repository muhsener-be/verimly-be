package app.verimly.commons.core.domain.vo;

import app.verimly.commons.core.domain.exception.ErrorMessage;
import app.verimly.commons.core.domain.exception.InvalidDomainObjectException;
import app.verimly.commons.core.domain.validation.InputValidator;

public class Color extends ValueObject<String> {

    private Color(String value) {
        super(value);
    }


    public static Color of(String hex) {
        if (hex == null || hex.isBlank())
            return null;

        Color color = new Color(hex);
        color.checkInvariants();
        return color;
    }

    private void checkInvariants() {
        boolean colorValid = InputValidator.isColorValid(value);
        if (!colorValid)
            throw new InvalidDomainObjectException(Errors.HEX_FORMAT);
    }


    public static final class Errors {
        public static final ErrorMessage HEX_FORMAT = ErrorMessage.of("color.hex-format", "Color must be in a valid hex format");
    }
}
