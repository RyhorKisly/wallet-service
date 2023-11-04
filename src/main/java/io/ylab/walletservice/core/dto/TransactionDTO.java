package io.ylab.walletservice.core.dto;

import com.fasterxml.jackson.annotation.JsonSetter;
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
    @JsonSetter("id")
    private String transactionId;

    /**
     * Enum type for description the transaction
     */
    @JsonSetter("operation")
    private Operation operation;

    /**
     * Sum of transaction.
     */
    @JsonSetter("sum_of_transaction")
    private BigDecimal sumOfTransaction;

    /**
     * number of account. Used to specify transaction for account.
     */
    @JsonSetter("account_id")
    private Long accountId;
}
