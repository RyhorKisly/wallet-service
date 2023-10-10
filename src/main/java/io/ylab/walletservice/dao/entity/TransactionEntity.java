package io.ylab.walletservice.dao.entity;

import io.ylab.walletservice.core.enums.Operation;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Stored business logic object. Used to save, retrieve and update state to a storage location
 */
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

    /**
     * Give you opportunity construct entity without initial params.
     * This is a required condition for using entity with ORM
     */
    public TransactionEntity() {
    }

    /**
     *  Constructs entity with the specified params.
     * @param transactionId the value used to set the {@code transactionId} entity field
     * @param operation the value used to set the {@code operation} entity field
     * @param sumOfTransaction the value used to set the {@code sumOfTransaction} entity field
     * @param numberAccount the value used to set the {@code numberAccount} entity field
     * @param dtCreate the value used to set the {@code dtCreate} entity field
     */
    public TransactionEntity(
            String transactionId,
            Operation operation,
            BigDecimal sumOfTransaction,
            UUID numberAccount,
            LocalDateTime dtCreate
    ) {
        this.transactionId = transactionId;
        this.operation = operation;
        this.sumOfTransaction = sumOfTransaction;
        this.numberAccount = numberAccount;
        this.dtCreate = dtCreate;
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
     * @param transactionId the value used to set the {@code transactionID} entity field.
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
     * @param operation the value used to set the {@code operation} entity field.
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
     * @param sumOfTransaction the value used to set the {@code sumOfTransaction} entity field.
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
     * @param numberAccount the value used to set the {@code numberAccount} entity field.
     */
    public void setNumberAccount(UUID numberAccount) {
        this.numberAccount = numberAccount;
    }

    /**
     * Returns date of creation.
     * @return date of creation
     */
    public LocalDateTime getDtCreate() {
        return dtCreate;
    }

    /**
     * Sets the values for the field {@code dtCreate}
     * @param dtCreate the value used to set the {@code dtCreate} entity field.
     */
    public void setDtCreate(LocalDateTime dtCreate) {
        this.dtCreate = dtCreate;
    }

    /**
     * Override method which specifies comparison of instances
     * of a class according to the fields of the class
     * @param o comparison object
     * @return true if objects equals
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionEntity that = (TransactionEntity) o;
        return Objects.equals(transactionId, that.transactionId) && operation == that.operation && Objects.equals(sumOfTransaction, that.sumOfTransaction) && Objects.equals(numberAccount, that.numberAccount) && Objects.equals(dtCreate, that.dtCreate);
    }

    /**
     * Override for specifying hashCode according to the fields of the class
     * @return hashCode according to the fields of the class
     */
    @Override
    public int hashCode() {
        return Objects.hash(transactionId, operation, sumOfTransaction, numberAccount, dtCreate);
    }
}