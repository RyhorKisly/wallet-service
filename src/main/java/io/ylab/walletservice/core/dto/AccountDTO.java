package io.ylab.walletservice.core.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Transfer account data for transfer to a storage location
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {

    /**
     * Used like ID of account in order to save in storage place and show in menu.
     */
    private Long numberAccount;

    /**
     * Current account balance. Used for transfer the original balance value to the storage place.
     */
    private BigDecimal balance;

    /**
     * Identifies that the account belongs to a specific user.
     */
    private String login;

}
