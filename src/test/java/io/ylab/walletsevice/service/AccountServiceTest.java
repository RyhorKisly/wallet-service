package io.ylab.walletsevice.service;

import io.ylab.walletservice.core.dto.AccountDTO;
import io.ylab.walletservice.core.enums.UserRole;
import io.ylab.walletservice.dao.AccountDao;
import io.ylab.walletservice.dao.AuditDao;
import io.ylab.walletservice.dao.UserDao;
import io.ylab.walletservice.dao.entity.AccountEntity;
import io.ylab.walletservice.dao.entity.UserEntity;
import io.ylab.walletservice.service.AccountService;
import io.ylab.walletservice.service.AuditService;
import io.ylab.walletservice.service.UserService;
import io.ylab.walletsevice.dao.ds.factory.ConnectionWrapperFactoryTest;
import io.ylab.walletsevice.dao.utils.api.ILiquibaseManagerTest;
import io.ylab.walletsevice.dao.utils.factory.LiquibaseManagerTestFactory;
import io.ylab.walletsevice.testcontainers.config.ContainersEnvironment;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AccountServiceTest extends ContainersEnvironment {

    private AccountService accountService;
    private AccountDao accountDao;
    private UserDao userDao;
    private ILiquibaseManagerTest liquibaseManagerTest;

    @BeforeAll
    @DisplayName("Initialize classes for tests")
    public void setUp() {
        this.userDao = new UserDao(ConnectionWrapperFactoryTest.getInstance());
        this.accountDao = new AccountDao(ConnectionWrapperFactoryTest.getInstance());
        AuditDao auditDao = new AuditDao(ConnectionWrapperFactoryTest.getInstance());
        UserService userService = new UserService(userDao);
        AuditService auditService = new AuditService(auditDao);

        this.accountService = new AccountService(accountDao, auditService, userService);
        liquibaseManagerTest = LiquibaseManagerTestFactory.getInstance();
        liquibaseManagerTest.migrateDbCreate();
    }

    @AfterEach
    @DisplayName("Migrates dates to drop schema and tables")
    public void drop() {
        this.liquibaseManagerTest.migrateDbDrop();
    }

    @Test
    @DisplayName("Test for creating account")
    void createTest() {
        UserEntity userEntity = new UserEntity();
        userEntity.setLogin("Have never been created account test1");
        userEntity.setPassword("1tset");
        userEntity.setRole(UserRole.USER);
        UserEntity savedUserEntity = userDao.save(userEntity);

        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setLogin(userEntity.getLogin());
        accountDTO.setBalance(new BigDecimal("0.0"));
        AccountEntity savedAccountEntity = accountService.create(accountDTO);

        assertEquals(accountDTO.getBalance(), savedAccountEntity.getBalance());
        assertEquals(savedUserEntity.getId(), savedAccountEntity.getUserId());
    }

    @Test
    @DisplayName("Test for getting account by number of account")
    void getByNumberAccountTest() {
        UserEntity userEntity = new UserEntity();
        userEntity.setLogin("Have never been created account test1");
        userEntity.setPassword("1tset");
        userEntity.setRole(UserRole.USER);
        UserEntity savedEntity = userDao.save(userEntity);

        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setUserId(userEntity.getId());
        accountEntity.setBalance(new BigDecimal("0.0"));
        AccountEntity savedAccountEntity = accountDao.save(accountEntity);
        AccountEntity foundAccountEntity = accountService.get(savedAccountEntity.getId());

        Assertions.assertEquals(savedAccountEntity, foundAccountEntity);
    }

    @Test
    @DisplayName("Test for getting account by number of account and login")
    void getByNumberAccountAndLoginTest() {
        UserEntity userEntity = new UserEntity();
        userEntity.setLogin("Have never been created account test1");
        userEntity.setPassword("1tset");
        userEntity.setRole(UserRole.USER);
        UserEntity savedEntity = userDao.save(userEntity);

        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setUserId(userEntity.getId());
        accountEntity.setBalance(new BigDecimal("0.0"));
        AccountEntity savedAccountEntity = accountDao.save(accountEntity);
        AccountEntity foundAccountEntity = accountService.get(savedAccountEntity.getId(), userEntity.getLogin());

        Assertions.assertEquals(savedAccountEntity, foundAccountEntity);
    }

    @Test
    @DisplayName("Test for getting account by login")
    void getByLoginTest() {
        UserEntity userEntity = new UserEntity();
        userEntity.setLogin("Have never been created account test1");
        userEntity.setPassword("1tset");
        userEntity.setRole(UserRole.USER);
        UserEntity savedEntity = userDao.save(userEntity);

        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setUserId(userEntity.getId());
        accountEntity.setBalance(new BigDecimal("0.0"));
        AccountEntity savedAccountEntity = accountDao.save(accountEntity);
        AccountEntity foundAccountEntity = accountService.get(userEntity.getLogin());

        Assertions.assertEquals(savedAccountEntity, foundAccountEntity);
    }

}
