package io.ylab.walletservice.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
    private Long id;

    /**
     * Class instance creation time
     */
    private LocalDateTime dtCreate;

    /**
     * Identifies that the account belongs to a specific user.
     */
    private Long userId;

    /**
     * Message where we write specific action performed by the user.
     */
    private String text;

}