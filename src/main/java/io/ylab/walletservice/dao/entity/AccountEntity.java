package io.ylab.walletservice.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Stored business logic object. Used to save, retrieve and update state to a storage location
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountEntity {

    /**
     * Used like ID of an account. Should be unique
     */
    private UUID numberAccount;

    /**
     * Current account balance.
     */
    private BigDecimal balance;

    /**
     * Identifies that the account belongs to a specific user.
     */
    private String login;

}