package io.ylab.walletservice.core.dto;

import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Transfer user data with registration for transfer to a storage location
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthenticationDTO {

    /**
     * Identifies that the account belongs to a specific user. Used for log in.
     */
    @JsonSetter("login")
    private String login;

    /**
     * Used to protect data. Used for log in.
     */
    @JsonSetter("password")
    private String password;
}
