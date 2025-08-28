package app.verimly.task.adapter.web;

import app.verimly.commons.core.domain.exception.ErrorMessage;
import app.verimly.commons.core.exception_handler.GlobalExceptionHandler;
import app.verimly.commons.core.web.response.ErrorResponse;
import app.verimly.task.application.exception.FolderNotFoundException;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class TaskExceptionHandler extends GlobalExceptionHandler {


    public TaskExceptionHandler(MessageSource messageSource) {
        super(messageSource);
    }


}
