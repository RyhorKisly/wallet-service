package io.ylab.walletservice.core.dto;

import com.fasterxml.jackson.annotation.JsonSetter;
import io.ylab.walletservice.core.enums.Operation;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "Id is mandatory")
    @JsonSetter("id")
    private String transactionId;

    /**
     * Enum type for description the transaction
     */
    @NotNull(message = "Operation is mandatory")
    @JsonSetter("operation")
    private Operation operation;

    /**
     * Sum of transaction.
     */
    @NotNull(message = "Sum of Transaction is mandatory")
    @JsonSetter("sum_of_transaction")
    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer=6, fraction=2, message = "Слева от запятой должно быть не больше 6 знаков, справа - не больше 2")
    private BigDecimal sumOfTransaction;

    /**
     * number of account. Used to specify transaction for account.
     */
    @NotNull(message = "Number of account is mandatory")
    @JsonSetter("account_id")
    private Long accountId;
}
