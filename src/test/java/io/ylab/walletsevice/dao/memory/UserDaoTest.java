package io.ylab.walletsevice.dao.memory;

import io.ylab.walletservice.core.enums.UserRole;
import io.ylab.walletservice.dao.entity.UserEntity;
import io.ylab.walletservice.dao.memory.UserDao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserDaoTest {

    private UserDao userDao;
    @BeforeEach
    @DisplayName("Initialize class for tests")
    public void setUp() {
        userDao = new UserDao();
    }
    @Test
    @DisplayName("Test for finding user by login")
    void findByLoginTest() {
        UserEntity userEntity = new UserEntity(
                "test1", "test1", UserRole.USER
        );

        userDao.save(userEntity);

        Assertions.assertEquals(userEntity, userDao.find(userEntity.getLogin()));
    }

    @Test
    @DisplayName("Test for saving user")
    void saveTest() {
        UserEntity userEntity = new UserEntity(
                "test2", "test2", UserRole.USER
        );

        userDao.save(userEntity);
        Assertions.assertEquals(userEntity, userDao.save(userEntity));
    }
}
