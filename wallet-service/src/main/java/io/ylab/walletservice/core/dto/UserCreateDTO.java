package io.ylab.walletservice.core.dto;

import com.fasterxml.jackson.annotation.JsonSetter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.ylab.walletservice.core.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Transfer user data with creation by User for transfer to a storage location
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Information about user")
public class UserCreateDTO {

    /**
     * Identifies that the account belongs to a specific user. Used for log in.
     */
    @Schema(description = "Login")
    private String login;

    /**
     * Enum type for description the role of user
     */
    @JsonSetter("role")
    @Schema(description = "Role for authentication")
    private UserRole role;

    /**
     * Used to protect data. Used for log in.
     */
    @JsonSetter("password")
    @Schema(description = "Password")
    private String password;
}
