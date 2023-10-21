package io.ylab.walletservice.core.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoaderTest {
    public static Properties loadProperties() {
        try {

        Properties configuration = new Properties();
        InputStream inputStream = PropertiesLoaderTest.class
                .getClassLoader()
                .getResourceAsStream("test.properties");
            configuration.load(inputStream);
        inputStream.close();
        return configuration;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
