package io.ylab.walletsevice.service;

import io.ylab.walletservice.core.dto.AccountDTO;
import io.ylab.walletservice.core.enums.UserRole;
import io.ylab.walletservice.dao.AccountDao;
import io.ylab.walletservice.dao.AuditDao;
import io.ylab.walletservice.dao.UserDao;
import io.ylab.walletservice.dao.api.IAccountDao;
import io.ylab.walletservice.dao.api.IUserDao;
import io.ylab.walletservice.dao.entity.AccountEntity;
import io.ylab.walletservice.dao.entity.UserEntity;
import io.ylab.walletservice.service.AccountService;
import io.ylab.walletservice.service.AuditService;
import io.ylab.walletservice.service.UserService;
import io.ylab.walletservice.service.api.IAccountService;
import io.ylab.walletsevice.dao.ds.factory.ConnectionWrapperFactoryTest;
import io.ylab.walletsevice.dao.utils.api.ILiquibaseManagerTest;
import io.ylab.walletsevice.dao.utils.factory.LiquibaseManagerTestFactory;
import io.ylab.walletsevice.testcontainers.config.ContainersEnvironment;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Class for testing methods of the class AccountService")
class AccountServiceTest extends ContainersEnvironment {

    /**
     * Define a field with a type {@link IUserDao} for further use in the test
     */
    private IAccountService accountService;

    /**
     * Define a field with a type {@link IUserDao} for further use in the test
     */
    private IAccountDao accountDao;

    /**
     * Define a field with a type {@link IUserDao} for further use in the test
     */
    private IUserDao userDao;

    /**
     * Define a field with a type {@link IUserDao} for further use in the test
     */
    private ILiquibaseManagerTest liquibaseManagerTest;

    @BeforeAll
    @DisplayName("Initialize classes for tests and call method for creating schema and tables in test db")
    public void setUp() {
        this.userDao = new UserDao(ConnectionWrapperFactoryTest.getInstance());
        this.accountDao = new AccountDao(ConnectionWrapperFactoryTest.getInstance());
        AuditDao auditDao = new AuditDao(ConnectionWrapperFactoryTest.getInstance());
        UserService userService = new UserService(userDao);

        this.accountService = new AccountService(accountDao, userService);
        liquibaseManagerTest = LiquibaseManagerTestFactory.getInstance();
        liquibaseManagerTest.migrateDbCreate();
    }

    @AfterEach
    @DisplayName("Migrates data to drop data in table")
    public void drop() {
        this.liquibaseManagerTest.migrateDbDrop();
    }

    @Test
    @DisplayName("Positive test for creating account")
    void createTest() {
        UserEntity userEntity = new UserEntity();
        userEntity.setLogin("Have never been created account test1");
        userEntity.setPassword("test");
        userEntity.setRole(UserRole.USER);
        UserEntity savedUserEntity = userDao.save(userEntity);

        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(userEntity.getId());
        accountDTO.setBalance(new BigDecimal("0.0"));
        AccountEntity savedAccountEntity = accountService.create(accountDTO);

        assertEquals(accountDTO.getBalance(), savedAccountEntity.getBalance());
        assertEquals(savedUserEntity.getId(), savedAccountEntity.getUserId());
    }

    @Test
    @DisplayName("Positive test for getting account by number of account")
    void getByNumberAccountTest() {
        UserEntity userEntity = new UserEntity();
        userEntity.setLogin("Have never been created account test1");
        userEntity.setPassword("test");
        userEntity.setRole(UserRole.USER);
        UserEntity savedEntity = userDao.save(userEntity);

        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setUserId(savedEntity.getId());
        accountEntity.setBalance(new BigDecimal("0.0"));
        AccountEntity savedAccountEntity = accountDao.save(accountEntity);
        AccountEntity foundAccountEntity = accountService.get(savedAccountEntity.getId());

        Assertions.assertEquals(savedAccountEntity, foundAccountEntity);
    }

    @Test
    @DisplayName("Positive test for getting account by number of account and login")
    void getByNumberAccountAndLoginTest() {
        UserEntity userEntity = new UserEntity();
        userEntity.setLogin("Have never been created account test1");
        userEntity.setPassword("test");
        userEntity.setRole(UserRole.USER);
        UserEntity savedEntity = userDao.save(userEntity);

        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setUserId(savedEntity.getId());
        accountEntity.setBalance(new BigDecimal("0.0"));
        AccountEntity savedAccountEntity = accountDao.save(accountEntity);
        AccountEntity foundAccountEntity = accountService.get(savedAccountEntity.getId(), userEntity.getLogin());

        Assertions.assertEquals(savedAccountEntity, foundAccountEntity);
    }

    @Test
    @DisplayName("Positive test for getting account by login")
    void getByLoginTest() {
        UserEntity userEntity = new UserEntity();
        userEntity.setLogin("Have never been created account test1");
        userEntity.setPassword("test");
        userEntity.setRole(UserRole.USER);
        UserEntity savedEntity = userDao.save(userEntity);

        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setUserId(savedEntity.getId());
        accountEntity.setBalance(new BigDecimal("0.0"));
        AccountEntity savedAccountEntity = accountDao.save(accountEntity);
        AccountEntity foundAccountEntity = accountService.get(userEntity.getId());

        Assertions.assertEquals(savedAccountEntity, foundAccountEntity);
    }

}
