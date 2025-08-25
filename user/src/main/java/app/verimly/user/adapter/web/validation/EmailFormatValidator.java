package app.verimly.user.adapter.web.validation;

import app.verimly.commons.core.domain.validation.InputValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class EmailFormatValidator implements ConstraintValidator<EmailFormat, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null)
            return true;

        return InputValidator.isEmailValid(value);
    }
}
