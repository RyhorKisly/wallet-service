package io.ylab.walletservice.core.conf;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Class for getting configuration from token.properties
 */
public class PropertiesLoaderToken {

    /**
     * Method for getting configuration from token.properties
     * @return {@link Properties} with my recourse where I store properties
     */
    public static Properties loadProperties() {
        try {

            Properties configuration = new Properties();
            InputStream inputStream = PropertiesLoaderToken.class
                    .getClassLoader()
                    .getResourceAsStream("token.properties");
            configuration.load(inputStream);
            inputStream.close();
            return configuration;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
