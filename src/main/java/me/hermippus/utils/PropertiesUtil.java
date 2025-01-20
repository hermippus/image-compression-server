package me.hermippus.utils;

import me.hermippus.Main;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {

    private static final Properties properties = new Properties();

    public static void load(String fileName) throws IOException {
        try (InputStream inputStream = Main.class.getClassLoader().getResourceAsStream(fileName)) {
            properties.load(inputStream);
        }
    }

    public static int getInt(String propertyName, int defaultValue) {
        int propertyValue = Integer.parseInt(properties.getProperty(propertyName));
        return propertyValue == 0 ? defaultValue : propertyValue;
    }
}
