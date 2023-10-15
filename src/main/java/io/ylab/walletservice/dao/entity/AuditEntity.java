package io.ylab.walletservice.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Stored business logic object. Used to save, retrieve and update state to a storage location
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuditEntity {

    /**
     * Used like ID of an audit. Should be unique
     */
    private UUID uuid;

    /**
     * Class instance creation time
     */
    private LocalDateTime dtCreate;

    /**
     * Identifies that the account belongs to a specific user.
     */
    private String userLogin;

    /**
     * Message where we write specific action performed by the user.
     */
    private String text;

}