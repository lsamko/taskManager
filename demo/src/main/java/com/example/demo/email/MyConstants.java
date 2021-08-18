package com.example.demo.email;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "mail")
public class MyConstants {

    private String myEmail;

    private String myPassword;

    private String friendEmail;

}
