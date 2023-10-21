package io.ylab.walletsevice.testcontainers.config;

import io.ylab.walletsevice.testcontainers.containers.PostgresTestContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * Superclass used as testcontainers
 */
@Testcontainers
public class ContainersEnvironment {

    /**
     * Testcontainers implementation for PostgreSQL.
     */
    @Container
    public static PostgreSQLContainer postgreSQLContainer = PostgresTestContainer.getInstance();
}
