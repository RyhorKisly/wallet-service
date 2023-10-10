package io.ylab.walletsevice.dao.memory;

import io.ylab.walletservice.core.enums.UserRole;
import io.ylab.walletservice.dao.entity.UserEntity;
import io.ylab.walletservice.dao.memory.UserDao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserDaoTest {

    @Test
    void findTest() {
        UserEntity userEntity = new UserEntity(
                "test1", "test1", UserRole.USER
        );

        UserDao userDao = new UserDao();
        UserEntity savedEntity = userDao.save(userEntity);

        Assertions.assertEquals(userEntity, savedEntity);
    }

    @Test
    void saveTest() {
        UserEntity userEntity = new UserEntity(
                "test2", "test2", UserRole.USER
        );

        UserDao userDao = new UserDao();
        userDao.save(userEntity);
        Assertions.assertEquals(userEntity, userDao.save(userEntity));
    }
}
