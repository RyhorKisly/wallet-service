package io.ylab.walletservice.dao.entity;

import io.ylab.walletservice.core.enums.Operation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Stored business logic object. Used to save, retrieve and update state to a storage location
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionEntity {

    /**
     * Used like ID of transaction. Should be unique
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
    private UUID numberAccount;

    /**
     * Class instance creation time
     */
    private LocalDateTime dtCreate;

}