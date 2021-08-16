package com.example.demo.exception;

public class EmailBadRequestException extends RuntimeException {

    public EmailBadRequestException(Exception message) {
        super(message);
    }
}
