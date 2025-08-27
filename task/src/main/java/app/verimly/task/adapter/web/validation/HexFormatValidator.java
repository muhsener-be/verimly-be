package app.verimly.task.adapter.web.validation;

import app.verimly.commons.core.domain.validation.InputValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class HexFormatValidator implements ConstraintValidator<HexFormat, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null)
            return true;


        return InputValidator.isColorValid(value);

    }
}
