package com.example.demo.notification;

import org.springframework.stereotype.Service;

@Service
public class LogsSenderService implements NotificationSender{

    @Override
    public void send(String body) {
        System.out.println("Please check logs ->  " + body);
    }

    @Override
    public Notification getNotificationType() {
        return Notification.LOGS;
    }
}
