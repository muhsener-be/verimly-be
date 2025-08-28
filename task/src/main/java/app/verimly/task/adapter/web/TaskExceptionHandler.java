package app.verimly.task.adapter.web;

import app.verimly.commons.core.exception_handler.GlobalExceptionHandler;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TaskExceptionHandler extends GlobalExceptionHandler {


    public TaskExceptionHandler(MessageSource messageSource) {
        super(messageSource);
    }


}
