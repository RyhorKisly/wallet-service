package io.ylab.walletservice.core.conf;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Class for getting configuration from test.properties
 */
public class PropertiesLoaderTest {

    /**
     * Method for getting configuration from application.properties
     * @return {@link Properties} with my recourse where I store properties
     */
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
