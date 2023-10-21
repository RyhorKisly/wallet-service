package io.ylab.walletsevice.service;

import io.ylab.walletservice.core.dto.UserCreateDTO;
import io.ylab.walletservice.core.dto.UserLoginDTO;
import io.ylab.walletservice.core.enums.UserRole;
import io.ylab.walletservice.dao.AccountDao;
import io.ylab.walletservice.dao.AuditDao;
import io.ylab.walletservice.dao.UserDao;
import io.ylab.walletservice.dao.entity.UserEntity;
import io.ylab.walletservice.service.AccountService;
import io.ylab.walletservice.service.AuditService;
import io.ylab.walletservice.service.AuthenticationService;
import io.ylab.walletservice.service.UserService;
import io.ylab.walletsevice.dao.ds.factory.ConnectionWrapperFactoryTest;
import io.ylab.walletsevice.dao.utils.api.ILiquibaseManagerTest;
import io.ylab.walletsevice.dao.utils.factory.LiquibaseManagerTestFactory;
import io.ylab.walletsevice.testcontainers.config.ContainersEnvironment;
import org.junit.jupiter.api.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AuthenticationServiceTest extends ContainersEnvironment {
    private UserDao userDao;
    private AuditDao auditDao;
    private UserService userService;
    private AuthenticationService authenticationService;
    private ILiquibaseManagerTest liquibaseManagerTest;

    @BeforeAll
    @DisplayName("Initialize classes for tests")
    public void setUp() {
        this.userDao = new UserDao(ConnectionWrapperFactoryTest.getInstance());
        this.auditDao = new AuditDao(ConnectionWrapperFactoryTest.getInstance());
        AccountDao accountDao = new AccountDao(ConnectionWrapperFactoryTest.getInstance());
        this.userService = new UserService(userDao);
        AuditService auditService = new AuditService(this.auditDao);
        AccountService accountService = new AccountService(accountDao, auditService, this.userService);
        authenticationService = new AuthenticationService(this.userService, accountService, auditService);

        liquibaseManagerTest = LiquibaseManagerTestFactory.getInstance();
        liquibaseManagerTest.migrateDbCreate();
    }

    @AfterEach
    @DisplayName("Migrates dates to drop schema and tables")
    public void drop() {
        this.liquibaseManagerTest.migrateDbDrop();
    }

    @Test
    @DisplayName("Test for checking register user")
    void registerTest() {
        UserCreateDTO userCreateDTO = new UserCreateDTO("test4", UserRole.USER, "4tset");
        UserEntity registeredEntity = authenticationService.register(userCreateDTO);

        Assertions.assertEquals(userCreateDTO.getLogin(), registeredEntity.getLogin());
    }


    @Test
    @DisplayName("Test for checking authorisation user")
    void authorizeTest() {
        UserLoginDTO userLoginDTO = new UserLoginDTO("test4", "test4");
        UserCreateDTO userCreateDTO = new UserCreateDTO("test4", UserRole.USER, "4tset");
        UserEntity createdEntity = userService.create(userCreateDTO);
        UserEntity authorizedEntity = authenticationService.authorize(userLoginDTO);

        auditDao.deleteByUserId(createdEntity.getId());
        userDao.delete(createdEntity.getId());

        Assertions.assertEquals(createdEntity, authorizedEntity);
    }

}
