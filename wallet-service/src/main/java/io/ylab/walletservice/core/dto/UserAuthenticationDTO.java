package io.ylab.walletservice.core.dto;

import com.fasterxml.jackson.annotation.JsonSetter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Transfer user data with registration for transfer to a storage location
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Information about user for authentication")
public class UserAuthenticationDTO {

    /**
     * Identifies that the account belongs to a specific user. Used for log in.
     */
    @JsonSetter("login")
    @Schema(description = "login")
    private String login;

    /**
     * Used to protect data. Used for log in.
     */
    @JsonSetter("password")
    @Schema(description = "password")
    private String password;
}
