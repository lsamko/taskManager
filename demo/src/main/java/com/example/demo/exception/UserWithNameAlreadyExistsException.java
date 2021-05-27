package com.example.demo.exception;

public class UserWithNameAlreadyExistsException extends RuntimeException {

    public UserWithNameAlreadyExistsException(String message) {
        super(message);
    }

}
