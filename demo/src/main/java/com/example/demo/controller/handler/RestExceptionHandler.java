package com.example.demo.controller.handler;

import com.example.demo.exception.TaskNotFoundException;
import com.example.demo.exception.TaskWithNameAlreadyExistsException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.exception.UserWithNameAlreadyExistsException;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(RestExceptionHandler.class);

    @ExceptionHandler(value = {
        TaskWithNameAlreadyExistsException.class, UserWithNameAlreadyExistsException.class
    })
    public void handleConflictException(HttpServletResponse response, Exception ex) throws IOException {
        log.trace("handleConflictException({}:{})", ex.getClass().getSimpleName(), ex.getMessage());
        response.sendError(HttpStatus.CONFLICT.value(), ex.getMessage());
    }

    @ExceptionHandler(value = {
        IllegalStateException.class
    })
    public void handleIllegalStateException(HttpServletResponse response, Exception ex) throws IOException {
        log.trace("handleIllegalStateException({}:{})", ex.getClass().getSimpleName(), ex.getMessage());
        response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
    }

    @ExceptionHandler(value = {
        ConstraintViolationException.class,
        IllegalArgumentException.class,
        ValidationException.class
    })
    public void handleBadRequest(HttpServletResponse response, RuntimeException ex) throws IOException {
        log.trace("handleBadRequest({}:{})", ex.getClass().getSimpleName(), ex.getMessage());
        response.sendError(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

    @ExceptionHandler(value = {
        TaskNotFoundException.class,
        UserNotFoundException.class
    })
    public void handleNotFoundException(HttpServletResponse response, Exception ex) throws IOException {
        log.trace("handleNotFoundException({}:{})", ex.getClass().getSimpleName(), ex.getMessage());
        response.sendError(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }


}
