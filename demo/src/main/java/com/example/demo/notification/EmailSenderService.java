package com.example.demo.notification;

import org.springframework.stereotype.Service;

@Service
public class EmailSenderService implements NotificationSender{

    @Override
    public void send(String body) {
        System.out.println("Email was sent with message: " + body );
    }

    @Override
    public Notification getNotificationType() {
        return Notification.EMAIL;
    }
}
