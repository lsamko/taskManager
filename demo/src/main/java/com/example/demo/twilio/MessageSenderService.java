package com.example.demo.twilio;


import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Service;

@Service
public class MessageSenderService {

    public MessageSenderService(TwilioProperties twilioProperties) {
        Twilio.init(twilioProperties.getAccountSid(), twilioProperties.getAuthToken());
    }

    public void sendTasks(String body) {
        int length = body.length();
        int maxLength = 110;
        if (length > maxLength) {
            Message message = Message.creator(new PhoneNumber("+380667681663"),
                new PhoneNumber("+12055761544"),
                body.substring(0, maxLength)).create();
            System.out.println(message.getSid());
        }
    }
}
