package app.verimly.user.adapter.web.exception;

import app.verimly.commons.core.web.response.ErrorResponse;
import lombok.RequiredArgsConstructor;
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

    private String findMessageFromFieldError(FieldError error) {
        String code = error.getDefaultMessage();
        Locale locale = LocaleContextHolder.getLocale();
        if (code == null)
            return null;

        return messageSource.getMessage(code, null, code, locale);

    }


}

