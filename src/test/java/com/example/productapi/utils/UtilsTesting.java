package com.example.productapi.utils;

import com.google.common.io.Resources;
import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@UtilityClass
public class UtilsTesting {
    public static String readRS(String path) throws IOException {
        URL url = Resources.getResource(path);
        return Resources.toString(url, StandardCharsets.UTF_8);
    }
}
