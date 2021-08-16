package com.example.demo.notification;

import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class NotificationFactory {

    private final List<NotificationSender> notificationSenders;

    public NotificationFactory( List<NotificationSender> notificationSenders) {
        this.notificationSenders = notificationSenders;
    }

    public NotificationSender getNotification(Notification notification) {

        return notificationSenders.stream().filter(notificationSender -> notification.equals(notificationSender.getNotificationType()))
            .findFirst().orElseThrow(()-> new RuntimeException("Notification sender not found"));
    }
}
