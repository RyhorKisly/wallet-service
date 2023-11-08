package io.ylab.walletservice.in.web;

import io.ylab.walletservice.core.dto.UserDTO;
import io.ylab.walletservice.core.enums.UserRole;
import io.ylab.walletservice.in.utils.JWTTokenHandler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@SpringBootTest
@DisplayName("Class for testing methods of the class AccountController ")
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * define a field with a type {@link JWTTokenHandler} for further aggregation
     */
    @Autowired
    JWTTokenHandler tokenHandler;

    @Test
    @DisplayName("Negative test method find history of transaction. User does not have token")
    void findAccountByUserIdTokenNotExist() throws Exception {
        UserDTO userDTO = new UserDTO(2L, "test43@admin.admin", UserRole.ADMIN);
        String token = tokenHandler.generateUserAccessToken(userDTO);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users/account/333")
                .header("authorization", "Bearer " + token)
                .sessionAttr("user", userDTO);

        this.mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Negative test method find history of transaction. Bed request because of wrong userId")
    void findAccountByUserWrongAccountId() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users/account/333");

        this.mockMvc.perform(requestBuilder)
                .andExpect(status().isUnauthorized());
    }

}