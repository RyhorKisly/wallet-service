package io.ylab.walletsevice.dao;

import io.ylab.walletservice.core.enums.UserRole;
import io.ylab.walletservice.dao.UserDao;
import io.ylab.walletservice.dao.entity.UserEntity;
import io.ylab.walletsevice.dao.ds.factory.ConnectionWrapperFactoryTest;
import io.ylab.walletsevice.dao.utils.api.ILiquibaseManagerTest;
import io.ylab.walletsevice.dao.utils.factory.LiquibaseManagerTestFactory;
import io.ylab.walletsevice.testcontainers.config.ContainersEnvironment;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserDaoTest extends ContainersEnvironment {
    private UserDao userDao;
    private ILiquibaseManagerTest liquibaseManagerTest;

    @BeforeAll
    @DisplayName("Initialize class for tests")
    public void setUp() {
        userDao = new UserDao(ConnectionWrapperFactoryTest.getInstance());
        liquibaseManagerTest = LiquibaseManagerTestFactory.getInstance();
        this.liquibaseManagerTest.migrateDbCreate();
    }

    @AfterEach
    @DisplayName("Migrates dates to drop schema and tables")
    public void drop() {
        this.liquibaseManagerTest.migrateDbDrop();
    }


    @Test
    @DisplayName("Positive test for saving user")
    void saveTest() {

        UserEntity userEntity = new UserEntity();
        userEntity.setLogin("test1 it's better not create user with this name");
        userEntity.setPassword("1tset");
        userEntity.setRole(UserRole.USER);

        UserEntity savedEntity = userDao.save(userEntity);

        assertEquals(userEntity.getLogin(), savedEntity.getLogin());
        assertEquals(userEntity.getPassword(), savedEntity.getPassword());
        assertEquals(userEntity.getRole(), savedEntity.getRole());
    }

    @Test
    @DisplayName("Negative test for saving user with the same login as saved")
    void saveUniqueLoginTest() {
        UserEntity userEntity = new UserEntity();
        userEntity.setLogin("test1 it's better not create user with this name");
        userEntity.setPassword("1tset");
        userEntity.setRole(UserRole.USER);

        userDao.save(userEntity);
        UserEntity checkEntity = userDao.save(userEntity);

        Assertions.assertNull(checkEntity.getId());
    }

    @Test
    @DisplayName("Test for finding user by login")
    void findByLoginTest() {
        UserEntity userEntity = new UserEntity();
        userEntity.setLogin("test1 it's better not create user with this name");
        userEntity.setPassword("1tset");
        userEntity.setRole(UserRole.USER);

        UserEntity savedEntity = userDao.save(userEntity);
        UserEntity foundEntity = userDao.find(savedEntity.getLogin());

        assertEquals(savedEntity, foundEntity);
    }

    @Test
    @DisplayName("Negative test for checking null when login does not exist")
    void findByLoginNonExistentUserTest() {
        Assertions.assertNull(userDao.find("Have never been created user)"));
    }

}
