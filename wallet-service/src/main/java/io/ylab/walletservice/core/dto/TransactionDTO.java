package io.ylab.walletservice.core.dto;

import com.fasterxml.jackson.annotation.JsonSetter;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Information about transaction")
public class TransactionDTO {

    /**
     * Used like ID of transaction in order to save in storage place.
     * should be uniq and not be empty
     */
    @JsonSetter("id")
    @Schema(description = "Id if transaction")
    private String transactionId;

    /**
     * Enum type for description the transaction
     */
    @JsonSetter("operation")
    @Schema(description = "Operation")
    private Operation operation;

    /**
     * Sum of transaction.
     */
    @JsonSetter("sum_of_transaction")
    @Schema(description = "Sum of transaction")
    private BigDecimal sumOfTransaction;

    /**
     * number of account. Used to specify transaction for account.
     */
    @JsonSetter("account_id")
    @Schema(description = "Account Id of exact user for exact transaction")
    private Long accountId;
}
