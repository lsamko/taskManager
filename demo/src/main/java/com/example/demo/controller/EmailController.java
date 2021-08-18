package com.example.demo.controller;

import com.example.demo.controller.handler.EmailPostRequestValidator;
import com.example.demo.dto.EmailPostRequestBody;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/email")
public class EmailController {
//
//    private final EmailPostRequestValidator emailPostRequestValidator;
//    private final EmailService emailService;
//
//    @InitBinder("emailPostRequestBody")
//    protected void initEmailPostRequestValidator(WebDataBinder binder) {
//        binder.addValidators(emailPostRequestValidator);
//    }
//
//    @PostMapping
//    public ResponseEntity<Void> postEmail(
//        @RequestBody @Valid EmailPostRequestBody emailPostRequestBody,
//        HttpServletRequest httpServletRequest) {
//
//        emailService.sendEmail(emailPostRequestBody);
//
//        return ResponseEntity.status(HttpStatus.CREATED).build();
//    }

}
