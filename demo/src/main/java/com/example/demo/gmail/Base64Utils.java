package com.example.demo.gmail;

import lombok.NoArgsConstructor;
import org.apache.commons.codec.binary.Base64;

@NoArgsConstructor
public final class Base64Utils {
    public static boolean isNotBase64(String string) {
        return !Base64.isBase64(string);
    }
    public static String decodeBase64(String base64String) {
        return new String(Base64.decodeBase64(base64String));
    }
}
