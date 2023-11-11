package io.ylab.walletservice.service.api;

import io.ylab.walletservice.core.dto.TransactionDTO;
import io.ylab.walletservice.dao.entity.TransactionEntity;

import java.util.Set;

/**
 * Interface for generic operations on a service for Transactions.
 */
public interface ITransactionService {

    /**
     * Create entity.
     * Use the returned instance for further operations as the save operation
     * @param transactionDTO used for creating entity
     * @param userId used for creating entity
     * @return created entity
     */
    TransactionEntity create(TransactionDTO transactionDTO, Long userId);

    /**
     * Get entity by ID
     * @param transactionID get entity by ID
     * @return entity for farther interaction with app
     */
    TransactionEntity get(String transactionID);

    /**
     * Get set of entities by number of an account
     * @param accountId get entity by id of an account
     * @param UserId will be used for audit
     * @return set of entities for farther interaction
     */
    Set<TransactionEntity> get(Long accountId, Long UserId);

    /**
     * Return true if transaction with ID exists
     * @param transactionalID find entity by ID
     * @return true if transaction with ID exists
     */
    boolean isExist(String transactionalID);
}
