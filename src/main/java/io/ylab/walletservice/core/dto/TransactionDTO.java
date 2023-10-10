package io.ylab.walletservice.core.dto;

import io.ylab.walletservice.core.enums.Operation;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Transfer transaction data for transfer to a storage location
 */
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
    private UUID numberAccount;

    /**
     * Give you opportunity construct dto without initial params.
     * It can be useful in case where you want check some params for null before adding in to the object
     */
    public TransactionDTO() {
    }

    /**
     *  Constructs dto with the specified params.
     * @param transactionId the value used to set the {@code transactionId} dto field
     * @param operation the value used to set the {@code operation} dto field
     * @param sumOfTransaction the value used to set the {@code sumOfTransaction} dto field
     * @param numberAccount the value used to set the {@code numberAccount} dto field
     */
    public TransactionDTO(
            String transactionId,
            Operation operation,
            BigDecimal sumOfTransaction,
            UUID numberAccount
    ) {
        this.transactionId = transactionId;
        this.operation = operation;
        this.sumOfTransaction = sumOfTransaction;
        this.numberAccount = numberAccount;
    }

    /**
     * Returns transaction ID.
     * @return transaction ID
     */
    public String getTransactionId() {
        return transactionId;
    }

    /**
     * Sets the values for the field {@code transactionID}
     * @param transactionId the value used to set the {@code transactionID} dto field.
     */
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    /**
     * Returns operation.
     * @return operation
     */
    public Operation getOperation() {
        return operation;
    }

    /**
     * Sets the values for the field {@code operation}
     * @param operation the value used to set the {@code operation} dto field.
     */
    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    /**
     * Returns sum of transaction.
     * @return sum of transaction
     */
    public BigDecimal getSumOfTransaction() {
        return sumOfTransaction;
    }

    /**
     * Sets the values for the field {@code sumOfTransaction}
     * @param sumOfTransaction the value used to set the {@code sumOfTransaction} dto field.
     */
    public void setSumOfTransaction(BigDecimal sumOfTransaction) {
        this.sumOfTransaction = sumOfTransaction;
    }

    /**
     * Returns number account.
     * @return number account
     */
    public UUID getNumberAccount() {
        return numberAccount;
    }

    /**
     * Sets the values for the field {@code numberAccount}
     * @param numberAccount the value used to set the {@code numberAccount} dto field.
     */
    public void setNumberAccount(UUID numberAccount) {
        this.numberAccount = numberAccount;
    }
}
