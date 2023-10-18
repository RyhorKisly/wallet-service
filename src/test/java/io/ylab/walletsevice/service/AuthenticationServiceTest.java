package io.ylab.walletsevice.service;

import io.ylab.walletservice.core.dto.UserCreateDTO;
import io.ylab.walletservice.core.dto.UserLoginDTO;
import io.ylab.walletservice.core.enums.UserRole;
import io.ylab.walletservice.dao.AuditDao;
import io.ylab.walletservice.dao.UserDao;
import io.ylab.walletservice.dao.entity.UserEntity;
import io.ylab.walletservice.service.AuthenticationService;
import io.ylab.walletservice.service.UserService;
import io.ylab.walletservice.service.factory.UserAuthenticationServiceFactory;
import io.ylab.walletservice.service.factory.UserServiceFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AuthenticationServiceTest {
    private UserService userService;
    private AuthenticationService authenticationService;
    private UserDao userDao;
    private AuditDao auditDao;

    @BeforeEach
    @DisplayName("Initialize classes for tests")
    public void setUp() {
        userService = (UserService) UserServiceFactory.getInstance();
        authenticationService = (AuthenticationService) UserAuthenticationServiceFactory.getInstance();
        userDao = new UserDao();
        auditDao = new AuditDao();
    }

    @Test
    @DisplayName("Test for checking authorisation user")
    void authorizeTest() {
        UserLoginDTO userLoginDTO = new UserLoginDTO("test4", "test4");
        UserCreateDTO userCreateDTO = new UserCreateDTO("test4", UserRole.USER, "4tset");
        UserEntity createdEntity = userService.create(userCreateDTO);
        UserEntity authorizedEntity = authenticationService.authorize(userLoginDTO);

        auditDao.deleteByUserId(createdEntity.getId());
        userDao.delete(createdEntity.getId());

        Assertions.assertEquals(createdEntity, authorizedEntity);
    }

}
