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

        Message message = Message.creator(new PhoneNumber("+380667681663"),
            new PhoneNumber("+12055761544"),
            body).create();

        System.out.println(message.getSid());
    }
}
