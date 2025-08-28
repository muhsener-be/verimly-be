//package app.verimly.task.adapter.web.exception;
//
//
//import app.verimly.commons.core.domain.exception.ErrorMessage;
//import app.verimly.commons.core.web.response.ErrorResponse;
//import app.verimly.task.application.exception.FolderNotFoundException;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.jetbrains.annotations.NotNull;
//import org.springframework.context.MessageSource;
//import org.springframework.context.i18n.LocaleContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//import org.springframework.web.context.request.WebRequest;
//
//import java.util.Locale;
//
//@ControllerAdvice
//@RequiredArgsConstructor
//@Component
//@Slf4j
//public class TaskExceptionHandler {
//
//    private final MessageSource messageSource;
//
//    @ExceptionHandler(FolderNotFoundException.class)
//    public ErrorResponse handleFolderNotFoundException(FolderNotFoundException e, WebRequest request) {
//        ErrorMessage actualErrorMessage = e.getErrorMessage();
//        String extracted = extractMessageFromErrorMessage(actualErrorMessage);
//
//        return ErrorResponse.notFound(extracted , request.getDescription(false));
//    }
//
//
//    private String extractMessageFromErrorMessage(ErrorMessage errorMessage) {
//        String code = errorMessage.code();
//        assert code != null;
//        return messageSource.getMessage(code, null, errorMessage.defaultMessage(), getLocale());
//
//    }
//
//    private static @NotNull Locale getLocale() {
//        return LocaleContextHolder.getLocale();
//    }
//}
