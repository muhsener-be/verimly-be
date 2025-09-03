package app.verimly.commons.core.exception_handler;

import app.verimly.commons.core.domain.exception.*;
import app.verimly.commons.core.security.AuthenticationRequiredException;
import app.verimly.commons.core.security.NoPermissionException;
import app.verimly.commons.core.web.response.*;
import io.swagger.v3.oas.annotations.Hidden;
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
public class GlobalExceptionHandler {


    private final MessageSource messageSource;
    private final ErrorResponseFactory errorResponseFactory;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @Hidden
    public BadRequestErrorResponse handleMethodArgumentNotValid(MethodArgumentNotValidException e, WebRequest request) {
        Map<String, String> codeToMessageMap = new HashMap<>();


        e.getFieldErrors().forEach(fieldError -> {
            String errorCode = fieldError.getDefaultMessage();
            String message = findMessageForCode(errorCode);
            codeToMessageMap.put(errorCode, message);
        });

        return errorResponseFactory.badRequest().path(request.getDescription(false))
                .errors(codeToMessageMap)
                .message("invalid inputs")
                .build();

//        Map<String, Map<String, Object>> additional = new HashMap<>();
//
//        for (FieldError err : e.getFieldErrors()) {
//            String objectName = err.getObjectName();
//            String fieldName = err.getField();
//
//            String message = findMessageFromFieldError(err);
//
//            additional.computeIfAbsent(objectName, k -> new HashMap<>())
//                    .put(fieldName, message);
//        }
//
//        return ErrorResponse.badRequest("invalid-request", "Invalid request.", request.getDescription(false), additional);
    }


    @Hidden
    @ExceptionHandler({InvalidDomainObjectException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BadRequestErrorResponse handleInvalidDomainObjectException(InvalidDomainObjectException ex, WebRequest request) {
        ErrorMessage actualErrorMessage = ex.getErrorMessage();
        String errorCode = actualErrorMessage.code();
        String i18Message = findMessageFromErrorMessage(actualErrorMessage);


        return errorResponseFactory.badRequest()
                .errors(Map.of(errorCode, i18Message))
                .message(i18Message)
                .path(request.getDescription(false)).build();


    }

    @Hidden
    @ExceptionHandler({DomainException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInvalidAndUserDomainExceptions(DomainException ex, WebRequest request) {


        ErrorMessage actualErrorMessage = ex.getErrorMessage();
        String message = findMessageFromErrorMessage(actualErrorMessage);
        return ErrorResponse.badRequest(actualErrorMessage.code(), message, request.getDescription(false));

    }

    @ExceptionHandler({NoPermissionException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @Hidden
    public ErrorResponse handleNoPermissionException(NoPermissionException ex, WebRequest request) {

        ErrorMessage actualErrorMessage = ex.getErrorMessage();
        String message = findMessageFromErrorMessage(actualErrorMessage);
        return ErrorResponse.forbidden(actualErrorMessage.code(), message, request.getDescription(false));

    }

    @ExceptionHandler({AuthenticationRequiredException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @Hidden
    public UnauthenticatedErrorResponse handleAuthenticationRequiredException(AuthenticationRequiredException ex, WebRequest request) {
        return errorResponseFactory.unauthenticated()
                .path(request.getDescription(false))
                .message(ex.getMessage())
                .build();

    }

    @Hidden
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public NotFoundErrorResponse handleFolderNotFoundException(NotFoundException e, WebRequest request) {
        ErrorMessage actualErrorMessage = e.getErrorMessage();
        String extracted = findMessageFromErrorMessage(actualErrorMessage);

        return errorResponseFactory.notFound()
                .message(e.getMessage())
                .path(request.getDescription(false))
                .resourceType(e.getResourceType())
                .resourceId(e.getResourceId())
                .build();

//        return ErrorResponse.notFound(actualErrorMessage.code(), extracted, request.getDescription(false));
    }


    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @Hidden
    public ErrorResponse handleConflictException(ConflictException ex, WebRequest request) {
        ErrorMessage actualErrorMessage = ex.getErrorMessage();
        String message = findMessageFromErrorMessage(actualErrorMessage);
        return ErrorResponse.conflict(actualErrorMessage.code(), message, request.getDescription(false));
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @Hidden
    public ErrorResponse handleException(Exception e, WebRequest request) {
        log.error(e.getMessage(), e);

        String message = messageSource.getMessage("internal.error", null, "Unexpected error happened.", null);

        return ErrorResponse.internalServerError(message, request.getDescription(false));
    }


    protected String findMessageFromErrorMessage(ErrorMessage errorMessage) {
        String errorCode = errorMessage.code();
        String defaultMessage = errorMessage.defaultMessage();
        return messageSource.getMessage(errorCode, null, defaultMessage, getLocale());
    }

    private Locale getLocale() {
        return LocaleContextHolder.getLocale();
    }


    private String findMessageFromFieldError(FieldError error) {
        String code = error.getDefaultMessage();

        return findMessageForCode(code);

    }

    private String findMessageForCode(String errorCode) {
        if (errorCode == null || errorCode.isBlank())
            return "";

        return messageSource.getMessage(errorCode, null, errorCode, getLocale());

    }


}

