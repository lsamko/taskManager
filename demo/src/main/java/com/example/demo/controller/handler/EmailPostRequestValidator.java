package com.example.demo.controller.handler;

import com.example.demo.dto.EmailPostRequestBody;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class EmailPostRequestValidator implements Validator {

    private final List<AbstractEmailRequestValidator> emailAddressValidators;

    public EmailPostRequestValidator(List<AbstractEmailRequestValidator> emailAddressValidators) {
        this.emailAddressValidators = emailAddressValidators;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        EmailPostRequestBody emailPostRequestBody = (EmailPostRequestBody) target;
        emailAddressValidators.forEach(validator -> validator.validate(emailPostRequestBody, errors));
    }
}
