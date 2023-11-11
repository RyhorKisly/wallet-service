package io.ylab.walletservice.dao;

import io.ylab.walletservice.core.enums.UserRole;
import io.ylab.walletservice.dao.api.IUserDao;
import io.ylab.walletservice.utils.IntegrationTestBase;
import io.ylab.walletservice.dao.entity.UserEntity;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


class UserDaoTest extends IntegrationTestBase {

    @Autowired
    private IUserDao userDao;

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

        Assertions.assertThrows(RuntimeException.class, () -> userDao.save(userEntity));
    }

    @Test
    @DisplayName("Positive test for finding user by login")
    void findByLoginTest() {
        UserEntity userEntity = new UserEntity();
        userEntity.setLogin("test1 it's better not create user with this name");
        userEntity.setPassword("test");
        userEntity.setRole(UserRole.USER);

        UserEntity savedEntity = userDao.save(userEntity);
        Optional<UserEntity> foundEntity = userDao.find(savedEntity.getLogin());

        assertEquals(savedEntity, foundEntity.orElseThrow(RuntimeException::new));
    }

    @Test
    @DisplayName("Negative test for finding user by login")
    void findByLoginNotExistUserTest() {
        Optional<UserEntity> foundEntity = userDao.find("test");
        assertThrowsExactly(RuntimeException.class, () -> foundEntity.orElseThrow(RuntimeException::new));
    }

    @Test
    @DisplayName("Positive test for finding user by id")
    void findByIdTest() {
        UserEntity userEntity = new UserEntity();
        userEntity.setLogin("test1 it's better not create user with this name");
        userEntity.setPassword("test");
        userEntity.setRole(UserRole.USER);

        UserEntity savedEntity = userDao.save(userEntity);
        Optional<UserEntity> foundEntity = userDao.find(savedEntity.getId());

        assertEquals(savedEntity, foundEntity.orElseThrow(RuntimeException::new));
    }

    @Test
    @DisplayName("Negative test for finding user by id")
    void findByIdNotExistUserTest() {
        Optional<UserEntity> foundEntity = userDao.find(10L);
        assertThrowsExactly(RuntimeException.class, () -> foundEntity.orElseThrow(RuntimeException::new));
    }
}