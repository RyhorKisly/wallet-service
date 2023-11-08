package io.ylab.walletservice.utils;

import org.jetbrains.annotations.NotNull;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;

/**
 * Superclass used as testcontainers
 */
public class Postgres {

    /**
     * Image_version for testcontainers
     */
    private static final String IMAGE_VERSION = "postgres:15.3-alpine3.18";

    /**
     * Name for test database
     */
    private static final String DATABASE_NAME = "wallet-service-test";

    /**
     * Container for tests
     */
    public static final PostgreSQLContainer<?> container = new PostgreSQLContainer<>(IMAGE_VERSION).withDatabaseName(DATABASE_NAME);

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(@NotNull ConfigurableApplicationContext applicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.hikari.jdbc-url=" + container.getJdbcUrl(),
                    "spring.datasource.hikari.username=" + container.getUsername(),
                    "spring.datasource.hikari.password=" + container.getPassword()
            ).applyTo(applicationContext);
        }
    }
}
