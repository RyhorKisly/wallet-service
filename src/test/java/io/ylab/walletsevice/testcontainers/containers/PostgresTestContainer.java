package io.ylab.walletsevice.testcontainers.containers;

import io.ylab.walletservice.core.utils.PropertiesLoaderTest;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.Properties;

public class PostgresTestContainer extends PostgreSQLContainer<PostgresTestContainer> {
    private static final String IMAGE_VERSION = "postgres:15.3-alpine3.18";
    private static PostgreSQLContainer container;
    private static final Properties CONF = PropertiesLoaderTest.loadProperties();
    public PostgresTestContainer() {
        super(IMAGE_VERSION);
    }

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
