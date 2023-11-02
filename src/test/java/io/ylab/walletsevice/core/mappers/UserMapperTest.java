package io.ylab.walletsevice.core.mappers;

import io.ylab.walletservice.core.dto.UserDTO;
import io.ylab.walletservice.core.enums.UserRole;
import io.ylab.walletservice.core.mappers.UserMapper;
import io.ylab.walletservice.core.mappers.UserMapperImpl;
import io.ylab.walletservice.dao.entity.UserEntity;
import io.ylab.walletsevice.testcontainers.config.ContainersEnvironment;
import org.junit.jupiter.api.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Class for testing mappers for Users DTOs and entities")
public class UserMapperTest {

    /**
     * Define a field with a type {@link UserMapper} for further use in the test
     */
    private UserMapper userMapper;

    @BeforeAll
    @DisplayName("Initialize classes for tests")
    public void setUp() {
        this.userMapper = new UserMapperImpl();
    }

    @Test
    @DisplayName("Positive test for mapping userEntity to UserDTO")
    void toDTOTest() {
        UserEntity userEntity = new UserEntity();
        userEntity.setLogin("test1 it's better not create user with this name");
        userEntity.setPassword("test");
        userEntity.setRole(UserRole.USER);

        UserDTO userDTO = userMapper.toDTO(userEntity);

        Assertions.assertNotNull(userDTO);

        Assertions.assertEquals(userEntity.getId(), userDTO.getId());
        Assertions.assertEquals(userEntity.getLogin(), userDTO.getLogin());
        Assertions.assertEquals(userEntity.getRole(), userDTO.getRole());
    }
}
