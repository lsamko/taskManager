package com.example.demo.controller.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class EmailSenderController {

    @Autowired
    public JavaMailSender emailSender;
    @Autowired
    public MyConstants myConstants;

    @ResponseBody
    @RequestMapping("/sendSimpleEmail")
    public String sendSimpleEmail() {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(myConstants.getFriendEmail());
        message.setSubject("Test Simple Email");
        message.setText("Hello, Im testing Simple Email");

        this.emailSender.send(message);

        return "Email Sent!";
    }

}
