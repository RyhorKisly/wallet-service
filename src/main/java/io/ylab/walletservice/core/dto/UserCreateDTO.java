package io.ylab.walletservice.core.dto;

import com.fasterxml.jackson.annotation.JsonSetter;
import io.ylab.walletservice.core.enums.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Transfer user data with creation by User for transfer to a storage location
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateDTO {

    /**
     * Identifies that the account belongs to a specific user. Used for log in.
     */
    @NotBlank(message = "Login is mandatory")
    @Size(min = 4, message = "Login too short. Must be more 4 symbols")
    @Size(max = 20, message = "Login too long. Must be less 20 symbols")
    private String login;

    /**
     * Enum type for description the role of user
     */
    @NotNull(message = "Role is mandatory")
    @JsonSetter("role")
    private UserRole role;

    /**
     * Used to protect data. Used for log in.
     */
    @NotBlank(message = "Password is mandatory")
    @Size(min = 4, message = "Password too short. Must be more 4 symbols")
    @Size(max = 20, message = "Password too long. Must be less 20 symbols")
    @JsonSetter("password")
    private String password;
}
