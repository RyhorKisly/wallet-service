package io.ylab.walletservice.in.web;

import io.ylab.walletservice.core.dto.UserCreateDTO;
import io.ylab.walletservice.core.dto.UserDTO;
import io.ylab.walletservice.core.enums.UserRole;
import io.ylab.walletservice.utils.IntegrationTestBase;
import io.ylab.walletservice.in.utils.JWTTokenHandler;
import io.ylab.walletservice.service.api.IUserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@DisplayName("Class for testing methods of the class UserController ")
class UserControllerTest extends IntegrationTestBase {

    @Autowired
    private MockMvc mockMvc;

    /**
     * Define a field with a type {@link IUserService} for further aggregation
     */
    @Autowired
    private IUserService userService;

    /**
     * define a field with a type {@link JWTTokenHandler} for further aggregation
     */
    @Autowired
    JWTTokenHandler tokenHandler;

    @Test
    @DisplayName("Positive test method find which get login and return ResponseEntity<UserDTO>")
    void findByLoginReturnResponseEntityWithUserDTO() throws Exception {
        UserCreateDTO userCreateDTO = new UserCreateDTO("test7@test.test", UserRole.USER, "test");
        userService.createByUser(userCreateDTO);

        UserDTO dto = new UserDTO(2L, "test7@test.test", UserRole.USER);
        String token = tokenHandler.generateUserAccessToken(dto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users/2")
                .header("authorization", "Bearer " + token);

        this.mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.login").value("test7@test.test"))
                .andExpect(jsonPath("$.role").value("USER"));
    }

    @Test
    @DisplayName("Negative test method find by login but token not exist")
    void findByLoginIfTokenNotExist() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users/2");

        this.mockMvc.perform(requestBuilder)
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Negative test method findMe but token not exist")
    void findMeIfTokenNotExist() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users/me");

        this.mockMvc.perform(requestBuilder)
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Positive test method find user who has token return ResponseEntity<UserDTO> with data about him in dto")
    void findMeReturnResponseEntityWithUserDTO() throws Exception {
        UserCreateDTO userCreateDTO = new UserCreateDTO("test8@test.test", UserRole.USER, "test");
        userService.createByUser(userCreateDTO);

        UserDTO userDTO = new UserDTO(3L, "test8@test.test", UserRole.USER);
        String token = tokenHandler.generateUserAccessToken(userDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users/me")
                .header("authorization", "Bearer " + token)
                .sessionAttr("user", userDTO);

        this.mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.login").value("test8@test.test"))
                .andExpect(jsonPath("$.role").value("USER"));
    }


}