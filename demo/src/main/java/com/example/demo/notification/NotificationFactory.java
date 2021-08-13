package com.example.demo.notification;

import com.example.demo.twilio.MessageSenderService;
import com.example.demo.twilio.TwilioProperties;
import org.springframework.stereotype.Component;

@Component
public class NotificationFactory {

    private final TwilioProperties twilioProperties;

    public NotificationFactory(TwilioProperties twilioProperties) {
        this.twilioProperties = twilioProperties;
    }

    public NotificationSender getNotification(Notification notification) {
        if (notification == null) {
            return null;
        }
        if (notification.equals(Notification.LOGS)) {
            return new LogsSenderService();
        } else if (notification.equals(Notification.EMAIL)) {
            return new EmailSenderService();
        } else if (notification.equals(Notification.MESSAGE)) {
            return new MessageSenderService(twilioProperties);
        }
        return null;
    }
}
