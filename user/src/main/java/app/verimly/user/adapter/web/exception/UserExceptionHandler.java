package app.verimly.user.adapter.web.exception;


import app.verimly.commons.core.domain.exception.ErrorMessage;
import app.verimly.commons.core.domain.vo.Email;
import app.verimly.commons.core.web.response.ConflictErrorResponse;
import app.verimly.commons.core.web.response.ErrorResponseFactory;
import app.verimly.user.application.exception.DuplicateEmailException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Locale;
import java.util.Map;

@RestControllerAdvice
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
public class UserExceptionHandler {

    private final ErrorResponseFactory factory;
    private final MessageSource messageSource;


    @ExceptionHandler(DuplicateEmailException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ConflictErrorResponse handleDuplicateEmail(DuplicateEmailException duplicateEmailException, WebRequest request) {
        Email email = duplicateEmailException.getEmail();
        Map<String, String> conflictingResource = Map.of("email", email.toString());

        ErrorMessage actualErrorMessage = duplicateEmailException.getErrorMessage();
        String code = actualErrorMessage.code();
        String i18 = extractMessageFromErrorMessage(actualErrorMessage);

        return factory.conflict(request.getDescription(false), "User")
                .errorCode(code)
                .message(i18)
                .conflictingResource(conflictingResource)
                .build();

    }

    private String extractMessageFromErrorMessage(ErrorMessage errorMessage) {
        String code = errorMessage.code();
        assert code != null;
        return messageSource.getMessage(code, null, errorMessage.defaultMessage(), getLocale());

    }

    private Locale getLocale() {
        return LocaleContextHolder.getLocale();
    }

}