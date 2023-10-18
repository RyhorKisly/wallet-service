package io.ylab.walletsevice.dao;

import io.ylab.walletservice.core.enums.UserRole;
import io.ylab.walletservice.dao.UserDao;
import io.ylab.walletservice.dao.entity.UserEntity;
import io.ylab.walletsevice.testcontainers.config.ContainersEnvironment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserDaoTest extends ContainersEnvironment {
    private UserDao userDao;
    @BeforeEach
    @DisplayName("Initialize class for tests")
    public void setUp() {
        userDao = new UserDao();
    }

    @Test
    @DisplayName("Positive test for saving user")
    void saveTest() {

        UserEntity userEntity = new UserEntity();
        userEntity.setLogin("test1 it's better not create user with this name");
        userEntity.setPassword("1tset");
        userEntity.setRole(UserRole.USER);

        UserEntity savedEntity = userDao.save(userEntity);
        userDao.delete(savedEntity.getId());

        assertEquals(userEntity.getLogin(), savedEntity.getLogin());
        assertEquals(userEntity.getPassword(), savedEntity.getPassword());
        assertEquals(userEntity.getRole(), savedEntity.getRole());

    }

    @Test
    @DisplayName("Negative test for saving user with the same login as saved")
    void saveUniqueLoginTest() {
        UserEntity testEntity = new UserEntity();
        testEntity.setLogin("admin");
        testEntity.setPassword("nimda");
        testEntity.setRole(UserRole.ADMIN);

        UserEntity savedEntity = userDao.save(testEntity);

        Assertions.assertNull(savedEntity.getId());
    }

    @Test
    @DisplayName("Test for finding user by login")
    void findByLoginTest() {
        UserEntity userEntity = new UserEntity();
        userEntity.setLogin("test1 it's better not create user with this name");
        userEntity.setPassword("1tset");
        userEntity.setRole(UserRole.USER);

        UserEntity savedEntity = userDao.save(userEntity);
        UserEntity foundEntity = userDao.find(userEntity.getLogin());
        userDao.delete(savedEntity.getId());

        assertEquals(userEntity.getLogin(), foundEntity.getLogin());
        assertEquals(userEntity.getPassword(), foundEntity.getPassword());
        assertEquals(userEntity.getRole(), foundEntity.getRole());
    }

    @Test
    @DisplayName("Negative test for checking null when login does not exist")
    void findByLoginNonExistentUserTest() {
        Assertions.assertNull(userDao.find("Have never been created user)"));
    }

}
