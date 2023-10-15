package io.ylab.walletservice.dao.entity;

import io.ylab.walletservice.core.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Stored business logic object. Used to save, retrieve and update state to a storage location
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    /**
     * Identifies that the account belongs to a specific user.
     */
    private String login;

    /**
     * Identifies that account belongs to a specific user.
     */
    private String password;

    /**
     * Enum type for description the role of user
     */
    private UserRole role;

}