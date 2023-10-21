package io.ylab.walletsevice.service;

import io.ylab.walletservice.core.dto.UserCreateDTO;
import io.ylab.walletservice.core.enums.UserRole;
import io.ylab.walletservice.dao.UserDao;
import io.ylab.walletservice.dao.api.IUserDao;
import io.ylab.walletservice.dao.entity.UserEntity;
import io.ylab.walletservice.service.UserService;
import io.ylab.walletservice.service.api.IUserService;
import io.ylab.walletsevice.dao.ds.factory.ConnectionWrapperFactoryTest;
import io.ylab.walletsevice.dao.utils.api.ILiquibaseManagerTest;
import io.ylab.walletsevice.dao.utils.factory.LiquibaseManagerTestFactory;
import io.ylab.walletsevice.testcontainers.config.ContainersEnvironment;
import org.junit.jupiter.api.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Class for testing methods of UserService")
public class UserServiceTest extends ContainersEnvironment {

    /**
     * Define a field with a type {@link IUserService} for further use in the test
     */
    private IUserService userService;
    /**
     * Define a field with a type {@link ILiquibaseManagerTest} for further use in the test
     */
    private ILiquibaseManagerTest liquibaseManagerTest;

    @BeforeAll
    @DisplayName("Initialize classes for tests and call method for creating schema and tables in test db")
    public void setUp() {
        IUserDao userDao = new UserDao(ConnectionWrapperFactoryTest.getInstance());
        userService = new UserService(userDao);

        liquibaseManagerTest = LiquibaseManagerTestFactory.getInstance();
        liquibaseManagerTest.migrateDbCreate();
    }

    @AfterEach
    @DisplayName("Migrates data to drop data in table")
    public void drop() {
        this.liquibaseManagerTest.migrateDbDrop();
    }

    @Test
    @DisplayName("Test for creating user")
    void createTest() {
        UserCreateDTO userCreateDTO = new UserCreateDTO("test5", UserRole.USER, "test5");
        UserEntity createdEntity = userService.create(userCreateDTO);

        Assertions.assertEquals(userCreateDTO.getLogin(), createdEntity.getLogin());
        Assertions.assertEquals(userCreateDTO.getRole(), createdEntity.getRole());
        Assertions.assertEquals(userCreateDTO.getPassword(), createdEntity.getPassword());
    }

    @Test
    @DisplayName("Test for getting user by login")
    void getTest() {
        UserCreateDTO userCreateDTO = new UserCreateDTO("test6", UserRole.USER, "test6");
        UserEntity createdEntity = userService.create(userCreateDTO);

        UserEntity getEntity = userService.get(userCreateDTO.getLogin());

        Assertions.assertEquals(createdEntity, getEntity);
    }

}
