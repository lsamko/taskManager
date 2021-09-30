package com.example.demo.service.twilio;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "twilio")
public class TwilioProperties {

    String accountSid = "AC93ddb05f6ad2061133561e6dd6a99bec";
    String authToken = "1301d4ee6fd1662a250149cd44958901";

}
