package io.ylab.walletsevice.dao;

import io.ylab.walletservice.core.enums.UserRole;
import io.ylab.walletservice.dao.AuditDao;
import io.ylab.walletservice.dao.UserDao;
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
public class AuditDaoTest extends ContainersEnvironment {
    private AuditDao auditDao;
    private UserDao userDao;
    private ILiquibaseManagerTest liquibaseManagerTest;

    @BeforeAll
    @DisplayName("Initialize class for tests")
    public void setUp() {
        auditDao = new AuditDao(ConnectionWrapperFactoryTest.getInstance());
        userDao = new UserDao(ConnectionWrapperFactoryTest.getInstance());
        liquibaseManagerTest = LiquibaseManagerTestFactory.getInstance();
        liquibaseManagerTest.migrateDbCreate();
    }

    @AfterEach
    @DisplayName("Migrates dates to drop schema and tables")
    public void drop() {
        this.liquibaseManagerTest.migrateDbDrop();
    }

    @Test
    void saveTest() {
        UserEntity userEntity = new UserEntity();
        userEntity.setLogin("Have never been created account test1");
        userEntity.setPassword("1tset");
        userEntity.setRole(UserRole.USER);
        UserEntity savedUserEntity = userDao.save(userEntity);

        AuditEntity auditEntity = new AuditEntity();
        auditEntity.setUserId(savedUserEntity.getId());
        auditEntity.setDtCreate(LocalDateTime.now());
        auditEntity.setText("test");
        AuditEntity savedAuditEntity = auditDao.save(auditEntity);

        assertEquals(auditEntity.getText(), savedAuditEntity.getText());
        assertEquals(auditEntity.getUserId(), savedAuditEntity.getUserId());
        assertEquals(auditEntity.getDtCreate(), savedAuditEntity.getDtCreate());
    }
}
