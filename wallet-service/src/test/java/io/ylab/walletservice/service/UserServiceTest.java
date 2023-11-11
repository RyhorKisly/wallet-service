package io.ylab.walletservice.service;

import io.ylab.walletservice.core.dto.UserCreateDTO;
import io.ylab.walletservice.core.enums.UserRole;
import io.ylab.walletservice.core.exceptions.NotExistUserException;
import io.ylab.walletservice.core.mappers.UserMapper;
import io.ylab.walletservice.dao.api.IUserDao;
import io.ylab.walletservice.dao.entity.UserEntity;
import io.ylab.walletservice.service.api.IUserService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@DisplayName("Class for testing methods of UserService")
public class UserServiceTest {

    @Mock
    private IUserDao userDao;

    /**
     * Define a field with a type {@link IUserService} for further use in the test
     */
    private IUserService userService;

    @Autowired
    private UserMapper userMapper;

    @BeforeEach
    @DisplayName("Initialize userService with mocked userDao")
    public void setUp() {
        userService = new UserService(userDao, userMapper);
    }

    @Test
    @DisplayName("Positive test for creating user")
    void createTest() {
        UserCreateDTO userCreateDTO = new UserCreateDTO("test6", UserRole.USER, "test6");
        UserEntity userEntity = new UserEntity();
        userEntity.setLogin("test6");
        userEntity.setRole(UserRole.USER);
        userEntity.setPassword("test6");


        when(userDao.save(userEntity)).thenReturn(userEntity);
        UserEntity createdEntity = userService.createByUser(userCreateDTO);


        Assertions.assertEquals(userCreateDTO.getLogin(), createdEntity.getLogin());
        Assertions.assertEquals(userCreateDTO.getRole(), createdEntity.getRole());
        Assertions.assertEquals(userCreateDTO.getPassword(), createdEntity.getPassword());
    }

    @Test
    @DisplayName("Positive test for getting user by login")
    void getByLoginTest() {
        UserCreateDTO userCreateDTO = new UserCreateDTO("test6", UserRole.USER, "test6");
        UserEntity userEntity = new UserEntity(1L, "test6", "test6", UserRole.USER);

        when(userDao.find(userCreateDTO.getLogin())).thenReturn(Optional.of(userEntity));
        UserEntity getEntity = userService.get(userCreateDTO.getLogin());

        Assertions.assertEquals(userEntity, getEntity);
    }

    @Test
    @DisplayName("Negative test for getting user by login when login not exist")
    void getByLoginNotExistTest() {
        when(userDao.find("test")).thenReturn(Optional.empty());
        Assertions.assertThrows(NotExistUserException.class, () -> userService.get("test"));
    }

    @Test
    @DisplayName("Positive test for getting user by id")
    void getByIdTest() {
        UserEntity userEntity = new UserEntity(1L, "test6", "test6", UserRole.USER);

        when(userDao.find(userEntity.getId())).thenReturn(Optional.of(userEntity));
        UserEntity getEntity = userService.get(userEntity.getId());

        Assertions.assertEquals(userEntity, getEntity);
    }

    @Test
    @DisplayName("Negative test for getting user by id when id not exist")
    void getByIdNotExistTest() {
        when(userDao.find(1L)).thenReturn(Optional.empty());
        Assertions.assertThrows(NotExistUserException.class, () -> userService.get(1L));
    }

}
