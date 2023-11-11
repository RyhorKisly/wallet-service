package io.ylab.walletservice.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.ylab.walletservice.core.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Transfer user data for transfer to a storage location
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Information about user")
public class UserDTO {

    /**
     * Used like ID of transaction in order to save in storage place.
     * should be uniq and not be empty
     */
    @Schema(description = "Identifier")
    private Long id;

    /**
     * Identifies that the account belongs to a specific user. Used for log in.
     */
    @Schema(description = "Login")
    private String login;

    /**
     * Enum type for description the role of user
     */
    @Schema(description = "Role")
    private UserRole role;
}
