package app.verimly.user.adapter.web.exception;

import app.verimly.commons.core.domain.exception.DomainException;
import app.verimly.commons.core.domain.exception.ErrorMessage;
import app.verimly.commons.core.domain.exception.InvalidDomainObjectException;
import app.verimly.commons.core.web.response.ErrorResponse;
import app.verimly.user.application.exception.DuplicateEmailException;
import app.verimly.user.domain.exception.UserDomainException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Slf4j
@RestControllerAdvice
@Component
@RequiredArgsConstructor
public class UserExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValid(MethodArgumentNotValidException e, WebRequest request) {
        Map<String, Map<String, Object>> additional = new HashMap<>();

        for (FieldError err : e.getFieldErrors()) {
            String objectName = err.getObjectName();
            String fieldName = err.getField();

            String message = findMessageFromFieldError(err);

            additional.computeIfAbsent(objectName, k -> new HashMap<>())
                    .put(fieldName, message);
        }

        return ErrorResponse.badRequest("Invalid request.", request.getDescription(false), additional);
    }


    @ExceptionHandler({InvalidDomainObjectException.class, UserDomainException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInvalidAndUserDomainExceptions(DomainException ex, WebRequest request) {
        assert ex instanceof InvalidDomainObjectException || ex instanceof UserDomainException;

        String message = findMessageFromErrorMessage(ex.getErrorMessage());
        return ErrorResponse.badRequest(message, request.getDescription(false), null);

    }

    @ExceptionHandler(DuplicateEmailException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleDuplicateEmailException(DuplicateEmailException ex, WebRequest request) {
        String message = findMessageFromErrorMessage(ex.getErrorMessage());

        return ErrorResponse.conflict(message, request.getDescription(false));
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(Exception e, WebRequest request) {
        log.error(e.getMessage(), e);

        String message = messageSource.getMessage("internal.error", null, "Unexpected error happened.", null);

        return ErrorResponse.internalServerError(message, request.getDescription(false));
    }


    private String findMessageFromErrorMessage(ErrorMessage errorMessage) {
        String errorCode = errorMessage.code();
        String defaultMessage = errorMessage.defaultMessage();
        return messageSource.getMessage(errorCode, null, defaultMessage, getLocale());
    }

    private Locale getLocale() {
        return LocaleContextHolder.getLocale();
    }


    private String findMessageFromFieldError(FieldError error) {
        String code = error.getDefaultMessage();
        Locale locale = getLocale();
        if (code == null)
            return null;

        return messageSource.getMessage(code, null, code, locale);

    }


}

