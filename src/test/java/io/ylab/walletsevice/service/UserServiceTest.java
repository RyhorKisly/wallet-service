package io.ylab.walletsevice.service;


import io.ylab.walletservice.core.dto.UserCreateDTO;
import io.ylab.walletservice.core.enums.UserRole;
import io.ylab.walletservice.dao.UserDao;
import io.ylab.walletservice.dao.entity.UserEntity;
import io.ylab.walletservice.service.UserService;
import io.ylab.walletsevice.dao.ds.factory.ConnectionWrapperFactoryTest;
import io.ylab.walletsevice.dao.utils.api.ILiquibaseManagerTest;
import io.ylab.walletsevice.dao.utils.factory.LiquibaseManagerTestFactory;
import io.ylab.walletsevice.testcontainers.config.ContainersEnvironment;
import org.junit.jupiter.api.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceTest extends ContainersEnvironment {
    private UserDao userDao;
    private UserService userService;
    private ILiquibaseManagerTest liquibaseManagerTest;

    @BeforeAll
    @DisplayName("Initialize classes for tests")
    public void setUp() {
        userDao = new UserDao(ConnectionWrapperFactoryTest.getInstance());
        userService = new UserService(userDao);

        liquibaseManagerTest = LiquibaseManagerTestFactory.getInstance();
        liquibaseManagerTest.migrateDbCreate();
    }

    @Test
    @DisplayName("Test for creating user")
    void createTest() {
        UserCreateDTO userCreateDTO = new UserCreateDTO("test5", UserRole.USER, "5tset");
        UserEntity createdEntity = userService.create(userCreateDTO);

        Assertions.assertEquals(userCreateDTO.getLogin(), createdEntity.getLogin());
        Assertions.assertEquals(userCreateDTO.getRole(), createdEntity.getRole());
        Assertions.assertEquals(userCreateDTO.getPassword(), createdEntity.getPassword());
    }

    @Test
    @DisplayName("Test for getting user by login")
    void getTest() {
        UserCreateDTO userCreateDTO = new UserCreateDTO("test6", UserRole.USER, "6tset");
        UserEntity createdEntity = userService.create(userCreateDTO);

        UserEntity getEntity = userService.get(userCreateDTO.getLogin());

        Assertions.assertEquals(createdEntity, getEntity);
    }

}
