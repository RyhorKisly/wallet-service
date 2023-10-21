package io.ylab.walletservice.core.dto;

import io.ylab.walletservice.core.enums.Operation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Transfer transaction data for transfer to a storage location
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {

    /**
     * Used like ID of transaction in order to save in storage place.
     * should be uniq and not be empty
     */
    private String transactionId;

    /**
     * Enum type for description the transaction
     */
    private Operation operation;

    /**
     * Sum of transaction.
     */
    private BigDecimal sumOfTransaction;

    /**
     * number of account. Used to specify transaction for account.
     */
    private Long numberAccount;
}
