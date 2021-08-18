package com.example.demo.controller.handler;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class AbstractEmailRequestValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {

    }
}
