package io.ylab.walletsevice.service;


import io.ylab.walletservice.core.dto.UserCreateDTO;
import io.ylab.walletservice.core.enums.UserRole;
import io.ylab.walletservice.dao.UserDao;
import io.ylab.walletservice.dao.entity.UserEntity;
import io.ylab.walletservice.service.UserService;
import io.ylab.walletservice.service.factory.UserServiceFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserServiceTest {
    private UserService userService;
    private UserDao userDao;

    @BeforeEach
    @DisplayName("Initialize classes for tests")
    public void setUp() {
        userService = (UserService) UserServiceFactory.getInstance();
        userDao = new UserDao();
    }

    @Test
    @DisplayName("Test for creating user")
    void createTest() {
        UserCreateDTO userCreateDTO = new UserCreateDTO("test5", UserRole.USER, "5tset");
        UserEntity createdEntity = userService.create(userCreateDTO);

        userDao.delete(createdEntity.getId());

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

        userDao.delete(createdEntity.getId());


        Assertions.assertEquals(createdEntity, getEntity);
    }

}
