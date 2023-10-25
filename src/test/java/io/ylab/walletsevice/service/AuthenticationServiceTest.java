package io.ylab.walletsevice.service;

import io.ylab.walletservice.core.dto.UserCreateDTO;
import io.ylab.walletservice.core.dto.UserAuthenticationDTO;
import io.ylab.walletservice.core.enums.UserRole;
import io.ylab.walletservice.dao.AccountDao;
import io.ylab.walletservice.dao.AuditDao;
import io.ylab.walletservice.dao.UserDao;
import io.ylab.walletservice.dao.entity.UserEntity;
import io.ylab.walletservice.in.utils.JWTTokenHandler;
import io.ylab.walletservice.service.AccountService;
import io.ylab.walletservice.service.AuditService;
import io.ylab.walletservice.service.AuthenticationService;
import io.ylab.walletservice.service.UserService;
import io.ylab.walletservice.service.api.IUserAuthenticationService;
import io.ylab.walletservice.service.api.IUserService;
import io.ylab.walletsevice.dao.ds.factory.ConnectionWrapperFactoryTest;
import io.ylab.walletsevice.dao.utils.api.ILiquibaseManagerTest;
import io.ylab.walletsevice.dao.utils.factory.LiquibaseManagerTestFactory;
import io.ylab.walletsevice.testcontainers.config.ContainersEnvironment;
import org.junit.jupiter.api.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Class for testing methods of the class AuthenticationService")
public class AuthenticationServiceTest extends ContainersEnvironment {

    /**
     * Define a field with a type {@link IUserService} for further use in the test
     */
    private IUserService userService;

    /**
     * Define a field with a type {@link IUserAuthenticationService} for further use in the test
     */
    private IUserAuthenticationService authenticationService;

    /**
     * Define a field with a type {@link ILiquibaseManagerTest} for further use in the test
     */
    private ILiquibaseManagerTest liquibaseManagerTest;

    @BeforeAll
    @DisplayName("Initialize classes for tests and call method for creating schema and tables in test db")
    public void setUp() {
        UserDao userDao = new UserDao(ConnectionWrapperFactoryTest.getInstance());
        AccountDao accountDao = new AccountDao(ConnectionWrapperFactoryTest.getInstance());
        this.userService = new UserService(userDao);
        AccountService accountService = new AccountService(accountDao, this.userService);
        authenticationService = new AuthenticationService(this.userService, accountService);

        liquibaseManagerTest = LiquibaseManagerTestFactory.getInstance();
        liquibaseManagerTest.migrateDbCreate();
    }

    @AfterEach
    @DisplayName("Migrates data to drop data in table")
    public void drop() {
        this.liquibaseManagerTest.migrateDbDrop();
    }

    @Test
    @DisplayName("Positive test for checking register user")
    void registerTest() {
        UserAuthenticationDTO dto = new UserAuthenticationDTO("test4", "test");
        UserEntity registeredEntity = authenticationService.register(dto);

        Assertions.assertEquals(dto.getLogin(), registeredEntity.getLogin());
    }


    @Test
    @DisplayName("Positive test for checking authorisation user")
    void authorizeTest() {
        UserCreateDTO userCreateDTO = new UserCreateDTO("test4", UserRole.USER, "4tset");
        UserEntity createdEntity = userService.createByUser(userCreateDTO);

        UserAuthenticationDTO userAuthenticationDTO = new UserAuthenticationDTO("test4", "test4");

        //todo написать тест с токином
        String token = authenticationService.authorize(userAuthenticationDTO);

        Assertions.assertNotNull(token);
    }

}
