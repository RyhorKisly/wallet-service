package io.ylab.walletsevice.service;

import io.ylab.walletservice.core.dto.AuditDTO;
import io.ylab.walletservice.core.enums.UserRole;
import io.ylab.walletservice.dao.UserDao;
import io.ylab.walletservice.dao.api.IAuditDao;
import io.ylab.walletservice.dao.api.IUserDao;
import io.ylab.walletservice.dao.entity.AuditEntity;
import io.ylab.walletservice.dao.AuditDao;
import io.ylab.walletservice.dao.entity.UserEntity;
import io.ylab.walletservice.service.AuditService;
import io.ylab.walletservice.service.api.IAuditService;
import io.ylab.walletsevice.dao.ds.factory.ConnectionWrapperFactoryTest;
import io.ylab.walletsevice.dao.utils.api.ILiquibaseManagerTest;
import io.ylab.walletsevice.dao.utils.factory.LiquibaseManagerTestFactory;
import io.ylab.walletsevice.testcontainers.config.ContainersEnvironment;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Class for testing methods of the class AuditService")
public class AuditServiceTest extends ContainersEnvironment {

    /**
     * Define a field with a type {@link IUserDao} for further use in the test
     */
    private IUserDao userDao;

    /**
     * Define a field with a type {@link IAuditDao} for further use in the test
     */
    private IAuditDao auditDao;

    /**
     * Define a field with a type {@link IAuditService} for further use in the test
     */
    private IAuditService auditService;

    /**
     * Define a field with a type {@link ILiquibaseManagerTest} for further use in the test
     */
    private ILiquibaseManagerTest liquibaseManagerTest;
//todo resolve errors in audit test dao
    @BeforeAll
    @DisplayName("Initialize classes for tests and call method for creating schema and tables in test db")
    public void setUp() {
        userDao = new UserDao(ConnectionWrapperFactoryTest.getInstance());
        auditDao = new AuditDao(ConnectionWrapperFactoryTest.getInstance());
        auditService = new AuditService(auditDao);

        liquibaseManagerTest = LiquibaseManagerTestFactory.getInstance();
        liquibaseManagerTest.migrateDbCreate();
    }

    @AfterEach
    @DisplayName("Migrates data to drop data in table")
    public void drop() {
        this.liquibaseManagerTest.migrateDbDrop();
    }

    @Test
    @DisplayName("Test for getting audit by login")
    void getAllByLogin() {
        UserEntity userEntity = new UserEntity();
        userEntity.setLogin("Have never been created account test1");
        userEntity.setPassword("1tset");
        userEntity.setRole(UserRole.USER);
        UserEntity savedUserEntity = userDao.save(userEntity);

        AuditEntity auditEntity = new AuditEntity();
        auditEntity.setText("firstTestByUser");
        auditEntity.setDtCreate(LocalDateTime.now());

        AuditEntity auditEntity2 = new AuditEntity();
        auditEntity2.setText("secondTestByUser");
        auditEntity2.setDtCreate(LocalDateTime.now());

        auditDao.save(auditEntity);
        auditDao.save(auditEntity2);

        Set<AuditEntity> audits = new TreeSet<>(Comparator.comparing(AuditEntity::getDtCreate));
        audits.add(auditEntity);
        audits.add(auditEntity2);


        Assertions.assertEquals(audits, auditService.getAll());
    }

    @Test
    @DisplayName("Positive test for creating audit")
    void createTest() {
        AuditDTO auditDTO = new AuditDTO("firstTestByAdmin");

        AuditEntity entity = auditService.create(auditDTO);

        Assertions.assertEquals(auditDTO.getText(), entity.getText());

    }
}
