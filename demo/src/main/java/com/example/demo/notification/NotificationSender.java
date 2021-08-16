package com.example.demo.notification;

public interface NotificationSender {

    void send(String body);

    Notification getNotificationType();
}
