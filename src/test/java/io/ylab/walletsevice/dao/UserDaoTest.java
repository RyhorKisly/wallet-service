package io.ylab.walletsevice.dao;

import io.ylab.walletservice.core.enums.UserRole;
import io.ylab.walletservice.core.exceptions.NotUniqueException;
import io.ylab.walletservice.dao.UserDao;
import io.ylab.walletservice.dao.api.IUserDao;
import io.ylab.walletservice.dao.entity.UserEntity;
import io.ylab.walletsevice.dao.ds.factory.ConnectionWrapperFactoryTest;
import io.ylab.walletsevice.dao.utils.api.ILiquibaseManagerTest;
import io.ylab.walletsevice.dao.utils.factory.LiquibaseManagerTestFactory;
import io.ylab.walletsevice.testcontainers.config.ContainersEnvironment;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Class for testing methods of the class UserDao")
public class UserDaoTest extends ContainersEnvironment {

    /**
     * Define a field with a type {@link IUserDao} for further use in the test
     */
    private IUserDao userDao;

    /**
     * Define a field with a type {@link ILiquibaseManagerTest} for further use in the test
     */
    private ILiquibaseManagerTest liquibaseManagerTest;

    @BeforeAll
    @DisplayName("Initialize classes for tests and call method for creating schema and tables in test db")
    public void setUp() {
        userDao = new UserDao(ConnectionWrapperFactoryTest.getInstance());
        liquibaseManagerTest = LiquibaseManagerTestFactory.getInstance();
        this.liquibaseManagerTest.migrateDbCreate();
    }

    @AfterEach
    @DisplayName("Migrates data to drop data in table")
    public void drop() {
        this.liquibaseManagerTest.migrateDbDrop();
    }


    @Test
    @DisplayName("Positive test for saving user")
    void saveTest() {

        UserEntity userEntity = new UserEntity();
        userEntity.setLogin("test1 it's better not create user with this name");
        userEntity.setPassword("test");
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
        userEntity.setPassword("test");
        userEntity.setRole(UserRole.USER);

        userDao.save(userEntity);

        Assertions.assertThrows(NotUniqueException.class, () -> userDao.save(userEntity));
    }

    @Test
    @DisplayName("Positive test for finding user by login")
    void findByLoginTest() {
        UserEntity userEntity = new UserEntity();
        userEntity.setLogin("test1 it's better not create user with this name");
        userEntity.setPassword("test");
        userEntity.setRole(UserRole.USER);

        UserEntity savedEntity = userDao.save(userEntity);
        UserEntity foundEntity = userDao.find(savedEntity.getLogin());

        assertEquals(savedEntity, foundEntity);
    }

    @Test
    @DisplayName("Negative test for finding user by login")
    void findByLoginNotExistUserTest() {
        Assertions.assertNull(userDao.find("test"));
    }

    @Test
    @DisplayName("Positive test for finding user by id")
    void findByIdTest() {
        UserEntity userEntity = new UserEntity();
        userEntity.setLogin("test1 it's better not create user with this name");
        userEntity.setPassword("test");
        userEntity.setRole(UserRole.USER);

        UserEntity savedEntity = userDao.save(userEntity);
        UserEntity foundEntity = userDao.find(savedEntity.getId());

        assertEquals(savedEntity, foundEntity);
    }

    @Test
    @DisplayName("Negative test for finding user by id")
    void findByIdNotExistUserTest() {
        Assertions.assertNull(userDao.find(10L));
    }

    @Test
    @DisplayName("Negative test for checking null when login does not exist")
    void findByLoginNonExistentUserTest() {
        Assertions.assertNull(userDao.find("Have never been created user)"));
    }

}
