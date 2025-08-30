package app.verimly.task.adapter.web.exception;


import app.verimly.commons.core.domain.exception.ErrorMessage;
import app.verimly.task.adapter.web.dto.response.SessionSummaryWebResponse;
import app.verimly.task.adapter.web.mapper.SessionWebMapper;
import app.verimly.task.application.exception.ActiveSessionExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
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

@RestControllerAdvice()
@RequiredArgsConstructor
@Component
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class TaskExceptionHandler {

    private final SessionWebMapper mapper;
    private final MessageSource messageSource;

    @ExceptionHandler(ActiveSessionExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public SessionSummaryWebResponse handleActiveSessionExistsException(ActiveSessionExistsException e, WebRequest request) {
        return mapper.toWebResponse(e.getSession());

    }


    private String extractMessageFromErrorMessage(ErrorMessage errorMessage) {
        String code = errorMessage.code();
        assert code != null;
        return messageSource.getMessage(code, null, errorMessage.defaultMessage(), getLocale());

    }

    private static @NotNull Locale getLocale() {
        return LocaleContextHolder.getLocale();
    }
}
