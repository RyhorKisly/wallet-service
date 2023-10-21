package io.ylab.walletservice.core.dto;

import io.ylab.walletservice.core.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Transfer user data with registration for transfer to a storage location
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
    private UserRole role;

    /**
     * Used to protect data. Used for log in.
     */
    private String password;
}
