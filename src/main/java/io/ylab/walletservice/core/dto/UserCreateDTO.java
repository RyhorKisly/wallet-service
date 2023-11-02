package io.ylab.walletservice.core.dto;

import com.fasterxml.jackson.annotation.JsonSetter;
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
public class UserCreateDTO {

    /**
     * Identifies that the account belongs to a specific user. Used for log in.
     */
    private String login;

    /**
     * Enum type for description the role of user
     */
    @JsonSetter("role")
    private UserRole role;

    /**
     * Used to protect data. Used for log in.
     */
    @JsonSetter("password")
    private String password;
}
