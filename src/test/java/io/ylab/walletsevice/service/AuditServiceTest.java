package io.ylab.walletsevice.service;

import io.ylab.walletservice.core.dto.AuditDTO;
import io.ylab.walletservice.core.enums.UserRole;
import io.ylab.walletservice.dao.UserDao;
import io.ylab.walletservice.dao.entity.AuditEntity;
import io.ylab.walletservice.dao.AuditDao;
import io.ylab.walletservice.dao.entity.UserEntity;
import io.ylab.walletservice.service.AuditService;
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
public class AuditServiceTest extends ContainersEnvironment {
    private UserDao userDao;
    private AuditDao auditDao;
    private AuditService auditService;
    private ILiquibaseManagerTest liquibaseManagerTest;

    @BeforeAll
    @DisplayName("Initialize classes for tests")
    public void setUp() {
        userDao = new UserDao(ConnectionWrapperFactoryTest.getInstance());
        auditDao = new AuditDao(ConnectionWrapperFactoryTest.getInstance());
        auditService = new AuditService(auditDao);

        liquibaseManagerTest = LiquibaseManagerTestFactory.getInstance();
        liquibaseManagerTest.migrateDbCreate();
    }

    @AfterEach
    @DisplayName("Migrates dates to drop schema and tables")
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
        auditEntity.setUserId(savedUserEntity.getId());

        AuditEntity auditEntity2 = new AuditEntity();
        auditEntity2.setText("secondTestByUser");
        auditEntity2.setDtCreate(LocalDateTime.now());
        auditEntity2.setUserId(savedUserEntity.getId());

        auditDao.save(auditEntity);
        auditDao.save(auditEntity2);

        Set<AuditEntity> audits = new TreeSet<>(Comparator.comparing(AuditEntity::getDtCreate));
        audits.add(auditEntity);
        audits.add(auditEntity2);


        Assertions.assertEquals(audits, auditService.getAllByLogin(savedUserEntity.getLogin()));
    }

    @Test
    @DisplayName("Test for creating audit")
    void createTest() {
        UserEntity userEntity = new UserEntity();
        userEntity.setLogin("Have never been created account test1");
        userEntity.setPassword("1tset");
        userEntity.setRole(UserRole.USER);
        UserEntity savedUserEntity = userDao.save(userEntity);

        AuditDTO auditDTO = new AuditDTO(savedUserEntity.getId(),"firstTestByAdmin");

        AuditEntity entity = auditService.create(auditDTO);

        Assertions.assertEquals(auditDTO.getText(), entity.getText());
        Assertions.assertEquals(auditDTO.getUserId(), entity.getUserId());

    }
}
