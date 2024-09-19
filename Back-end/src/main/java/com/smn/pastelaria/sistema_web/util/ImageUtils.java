package com.smn.pastelaria.sistema_web.util;

import java.util.Base64;

public class ImageUtils {

    public static String convertToBase64String(byte[] imageBytes) {
        return Base64.getEncoder().encodeToString(imageBytes);
    }
}
