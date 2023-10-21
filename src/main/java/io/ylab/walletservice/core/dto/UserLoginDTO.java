package io.ylab.walletservice.core.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Transfer user data with authentication for transfer to a storage location
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDTO {

    /**
     * Identifies that the account belongs to a specific user. Used for log in.
     */
    private String login;

    /**
     * Used to protect data. Used for log in.
     */
    private String password;
}
