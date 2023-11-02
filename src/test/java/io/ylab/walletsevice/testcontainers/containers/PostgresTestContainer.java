package io.ylab.walletsevice.testcontainers.containers;

import io.ylab.walletservice.core.conf.PropertiesLoaderTest;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.Properties;

public class PostgresTestContainer extends PostgreSQLContainer<PostgresTestContainer> {

    /**
     * Image_version for testcontainers
     */
    private static final String IMAGE_VERSION = "postgres:15.3-alpine3.18";

    /**
     * Field to do container singleton
     */
    private static PostgreSQLContainer container;

    /**
     * Initialize class {@link Properties} for using properties from test.properties
     */
    private static final Properties CONF = PropertiesLoaderTest.loadProperties();

    /**
     * Constructor where the name of Image is transmitted
     */
    public PostgresTestContainer() {
        super(IMAGE_VERSION);
    }

    /**
     * Basic method for initializing class with singleton pattern
     * @return single object of this class
     */
    public static PostgreSQLContainer getInstance() {
        if(container == null) {
            container = new PostgreSQLContainer().withDatabaseName(CONF.getProperty("database.db"))
                    .withUsername(CONF.getProperty("database.user"))
                    .withPassword(CONF.getProperty("database.password"));
        }
        return container;
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void stop() {
        super.stop();
    }
}
