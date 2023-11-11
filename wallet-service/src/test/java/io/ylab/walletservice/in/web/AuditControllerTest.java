package io.ylab.walletservice.in.web;

import io.ylab.walletservice.core.dto.UserDTO;
import io.ylab.walletservice.core.enums.UserRole;
import io.ylab.walletservice.in.utils.JWTTokenHandler;
import io.ylab.walletservice.utils.IntegrationTestBase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@DisplayName("Class for testing methods of the class AuditController ")
class AuditControllerTest extends IntegrationTestBase {

    @Autowired
    private MockMvc mockMvc;

    /**
     * define a field with a type {@link JWTTokenHandler} for further aggregation
     */
    @Autowired
    JWTTokenHandler tokenHandler;

    @Test
    @DisplayName("Positive test method find history of audit. Access only for authorized user")
    void findAllAudits() throws Exception {
        UserDTO dto = new UserDTO(2L, "test7@test.test", UserRole.USER);
        String token = tokenHandler.generateUserAccessToken(dto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users/audit")
                .header("authorization", "Bearer " + token);

        this.mockMvc.perform(requestBuilder)
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Negative test method find history of audit. User without token")
    void findByLoginIfTokenNotExist() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users/audit");

        this.mockMvc.perform(requestBuilder)
                .andExpect(status().isUnauthorized());
    }

}