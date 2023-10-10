package io.ylab.walletsevice.service;


import io.ylab.walletservice.core.dto.UserCreateDTO;
import io.ylab.walletservice.core.enums.UserRole;
import io.ylab.walletservice.dao.entity.UserEntity;
import io.ylab.walletservice.dao.memory.UserDao;
import io.ylab.walletservice.dao.memory.factory.UserDaoFactory;
import io.ylab.walletservice.service.UserService;
import io.ylab.walletservice.service.factory.UserServiceFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserServiceTest {
    private UserDao userDao;
    private UserService userService;


    @BeforeEach
    public void setUp() {
        userDao = (UserDao) UserDaoFactory.getInstance();
        userService = (UserService) UserServiceFactory.getInstance();
    }

    @Test
    void createTest() {
        UserCreateDTO userCreateDTO = new UserCreateDTO("test5", UserRole.USER, "5tset");
        UserEntity createdEntity = userService.create(userCreateDTO);

        Assertions.assertEquals(userCreateDTO.getLogin(), createdEntity.getLogin());
        Assertions.assertEquals(userCreateDTO.getRole(), createdEntity.getRole());
        Assertions.assertEquals(userCreateDTO.getPassword(), createdEntity.getPassword());
    }

    @Test
    void getTest() {
        UserCreateDTO userCreateDTO = new UserCreateDTO("test6", UserRole.USER, "6tset");
        UserEntity createdEntity = userService.create(userCreateDTO);

        UserEntity getEntity = userService.get(userCreateDTO.getLogin());

        Assertions.assertEquals(createdEntity, getEntity);
    }

}
