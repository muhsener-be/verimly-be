package app.verimly.user.adapter.web;

import app.verimly.commons.core.exception_handler.GlobalExceptionHandler;
import app.verimly.commons.core.web.response.ErrorResponse;
import app.verimly.user.application.exception.DuplicateEmailException;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class UserExceptionHandler extends GlobalExceptionHandler {

    public UserExceptionHandler(MessageSource messageSource) {
        super(messageSource);
    }

    @ExceptionHandler(DuplicateEmailException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @Hidden
    public ErrorResponse handleDuplicateEmailException(DuplicateEmailException ex, WebRequest request) {
        String message = super.findMessageFromErrorMessage(ex.getErrorMessage());

        return ErrorResponse.conflict(message, request.getDescription(false));
    }
}
