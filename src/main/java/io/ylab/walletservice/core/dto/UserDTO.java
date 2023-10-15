package io.ylab.walletservice.core.dto;

import io.ylab.walletservice.core.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Transfer user data for transfer to a storage location
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    /**
     * Used like ID of transaction in order to save in storage place.
     * should be uniq and not be empty
     */
    private UUID uuid;

    /**
     * Identifies that the account belongs to a specific user. Used for log in.
     */
    private String login;

    /**
     * Enum type for description the role of user
     */
    private UserRole userRole;
}
