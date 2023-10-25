package io.ylab.walletsevice.dao;

import io.ylab.walletservice.core.enums.UserRole;
import io.ylab.walletservice.dao.AuditDao;
import io.ylab.walletservice.dao.UserDao;
import io.ylab.walletservice.dao.api.IAuditDao;
import io.ylab.walletservice.dao.api.IUserDao;
import io.ylab.walletservice.dao.entity.AuditEntity;
import io.ylab.walletservice.dao.entity.UserEntity;
import io.ylab.walletsevice.dao.ds.factory.ConnectionWrapperFactoryTest;
import io.ylab.walletsevice.dao.utils.api.ILiquibaseManagerTest;
import io.ylab.walletsevice.dao.utils.factory.LiquibaseManagerTestFactory;
import io.ylab.walletsevice.testcontainers.config.ContainersEnvironment;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Class for testing methods of the class AuditDao")
public class AuditDaoTest extends ContainersEnvironment {

    /**
     * Define a field with a type {@link IAuditDao} for further use in the test
     */
    private IAuditDao auditDao;

    /**
     * Define a field with a type {@link IUserDao} for further use in the test
     */
    private IUserDao userDao;

    /**
     * Define a field with a type {@link ILiquibaseManagerTest} for further use in the test
     */
    private ILiquibaseManagerTest liquibaseManagerTest;

    @BeforeAll
    @DisplayName("Initialize classes for tests and call method for creating schema and tables in test db")
    public void setUp() {
        auditDao = new AuditDao(ConnectionWrapperFactoryTest.getInstance());
        userDao = new UserDao(ConnectionWrapperFactoryTest.getInstance());
        liquibaseManagerTest = LiquibaseManagerTestFactory.getInstance();
        liquibaseManagerTest.migrateDbCreate();
    }

    @AfterEach
    @DisplayName("Migrates data to drop data in table")
    public void drop() {
        this.liquibaseManagerTest.migrateDbDrop();
    }

    @Test
    @DisplayName("Positive test for saving audit")
    void saveTest() {
        UserEntity userEntity = new UserEntity();
        userEntity.setLogin("Have never been created account test1");
        userEntity.setPassword("test");
        userEntity.setRole(UserRole.USER);
        UserEntity savedUserEntity = userDao.save(userEntity);

        AuditEntity auditEntity = new AuditEntity();
        auditEntity.setDtCreate(LocalDateTime.now());
        auditEntity.setText("test");
        AuditEntity savedAuditEntity = auditDao.save(auditEntity);

        assertEquals(auditEntity.getText(), savedAuditEntity.getText());
        assertEquals(auditEntity.getDtCreate(), savedAuditEntity.getDtCreate());
    }
}
