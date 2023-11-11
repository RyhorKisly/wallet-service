package io.ylab.walletservice.service;

import io.ylab.walletservice.core.dto.UserAuthenticationDTO;
import io.ylab.walletservice.core.enums.UserRole;
import io.ylab.walletservice.core.mappers.UserMapper;
import io.ylab.walletservice.dao.entity.UserEntity;
import io.ylab.walletservice.in.utils.JWTTokenHandler;
import io.ylab.walletservice.service.api.IAccountService;
import io.ylab.walletservice.service.api.IUserService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@DisplayName("Class for testing methods of the class AuthenticationService")
public class AuthenticationServiceTest {

    /**
     * define a field with a type {@link IUserService} for further usage in tests
     */
    @Mock
    private IUserService userService;

    /**
     * define a field with a type {@link IAccountService} for further usage in tests
     */
    @Mock
    private IAccountService accountService;

    /**
     * Initialize a field with a type {@link JWTTokenHandler} for using jwt token into class
     */
    @Autowired
    private JWTTokenHandler handler;

    /**
     * initialize a field with a type {@link UserMapper} for converting userDTO and entity
     */
    @Autowired
    private UserMapper userMapper;

    private AuthenticationService authenticationService;

    @BeforeEach
    @DisplayName("Initialize authenticationService for Tests with mocked accountService and userService")
    public void setUp() {
        authenticationService = new AuthenticationService(userService, accountService, handler, userMapper);
    }

    @Test
    @DisplayName("Positive test for checking register user")
    void registerTest() {
        UserAuthenticationDTO dto = new UserAuthenticationDTO("test4", "test");
        UserEntity userEntity = new UserEntity(1L, "test4", "tset", UserRole.USER);
        when(userService.createByRegistration(dto)).thenReturn(userEntity);

        UserEntity registeredEntity = authenticationService.register(dto);

        Assertions.assertEquals(userEntity, registeredEntity);
    }


    @Test
    @DisplayName("Positive test for checking authorisation user")
    void authorizeTest() {
        UserAuthenticationDTO dto = new UserAuthenticationDTO("test4", "test4");
        UserEntity userEntity = new UserEntity(1L, "test4", "4tset", UserRole.USER);

        when(userService.get(dto.getLogin())).thenReturn(userEntity);

        String token = authenticationService.authorize(dto);
        String username = handler.getUsername(token);
        Assertions.assertEquals(userEntity.getLogin(), username);
    }

}
