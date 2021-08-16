package com.example.demo.gmail;

import javax.mail.MessagingException;

@FunctionalInterface
public interface EmailsConsumer {
    void acceptEmails(String[] strings) throws MessagingException;
}
