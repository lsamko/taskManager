package com.example.demo.exception;

public class TaskWithNameAlreadyExistsException extends RuntimeException {

    public TaskWithNameAlreadyExistsException(String message) {
        super(message);
    }

}
