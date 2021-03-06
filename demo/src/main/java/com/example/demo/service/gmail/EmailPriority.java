package com.example.demo.service.gmail;

import java.util.Arrays;

public enum EmailPriority {
    NORMAL(3, false),
    HIGH(1, true);

    final int javaMailPriority;
    final boolean important;

    EmailPriority(int javaMailPriority, boolean important) {
        this.javaMailPriority = javaMailPriority;
        this.important = important;
    }

    public static int getPriority(boolean importance) {
        return Arrays.stream(values()).filter(
                emailPriority -> emailPriority.important == importance)
            .findFirst()
            .orElse(EmailPriority.NORMAL)
            .javaMailPriority;
    }
}
