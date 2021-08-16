package com.example.demo.gmail;

import com.example.demo.dto.EmailPostRequestBody;
import com.example.demo.notification.Notification;
import com.example.demo.notification.NotificationSender;

public interface EmailService extends NotificationSender {
    void send(EmailPostRequestBody emailPostRequestBody);

     default Notification getNotificationType() {
        return Notification.EMAIL;
    }
}
