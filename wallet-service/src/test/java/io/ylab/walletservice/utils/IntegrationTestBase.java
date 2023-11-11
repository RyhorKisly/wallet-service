package io.ylab.walletservice.utils;

import io.ylab.walletservice.WalletServiceApplication;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = WalletServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers =  {Postgres.Initializer.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@DisplayName("Base class for integration test in daoTests")
public abstract class IntegrationTestBase {

    /**
     * Define a field with a type {@link LiquibaseManagerTest} to manage migration in db for tests
     */
    @Autowired
    private LiquibaseManagerTest liquibaseManager;

    @BeforeAll
    @DisplayName("Init container")
    public static void init() {
        Postgres.container.start();
    }

    @AfterEach
    @DisplayName("Migrates data to drop data in table")
    public void drop() {
        this.liquibaseManager.migrateDbDrop();
    }
}
