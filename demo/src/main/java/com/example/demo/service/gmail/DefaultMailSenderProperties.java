package com.example.demo.service.gmail;

import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@ConfigurationProperties(prefix = "gmail.com")
@Getter
@Setter
@ToString
@Validated
public class DefaultMailSenderProperties {
    private String fromName;
    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$",
        message = "'fromAddress' should have email format!")
    private String fromAddress;
}
