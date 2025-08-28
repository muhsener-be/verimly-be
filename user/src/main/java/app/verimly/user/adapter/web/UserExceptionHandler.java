package app.verimly.user.adapter.web;

import app.verimly.commons.core.exception_handler.GlobalExceptionHandler;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserExceptionHandler extends GlobalExceptionHandler {

    public UserExceptionHandler(MessageSource messageSource) {
        super(messageSource);
    }


}
