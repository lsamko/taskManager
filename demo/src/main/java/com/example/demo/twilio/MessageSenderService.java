package com.example.demo.twilio;


import com.example.demo.notification.NotificationSender;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MessageSenderService  implements NotificationSender {

    private static final int MAX_LENGTH = 110;

    public MessageSenderService(TwilioProperties twilioProperties) {
        Twilio.init(twilioProperties.getAccountSid(), twilioProperties.getAuthToken());
    }
    @Override
    public void send(String body) {
        String messageBody = prepareMessageBody(body);

        Message message = Message.creator(new PhoneNumber("+380667681663"),
            new PhoneNumber("+12055761544"),
            messageBody).create();
        log.info(message.getSid());
    }

    private String prepareMessageBody(String body) {
        if (body.length() > MAX_LENGTH) {
            return body.substring(0, MAX_LENGTH).concat("...");
        }
        return body;
    }
}
