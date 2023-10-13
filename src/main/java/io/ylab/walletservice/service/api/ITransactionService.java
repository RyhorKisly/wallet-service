package io.ylab.walletservice.service.api;

import io.ylab.walletservice.core.dto.TransactionDTO;
import io.ylab.walletservice.dao.entity.AccountEntity;
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
     * @param login used for creating entity
     * @return created entity
     */
    TransactionEntity create(TransactionDTO transactionDTO, String login);

    /**
     * get entity by ID
     * @param transactionID get entity by ID
     * @return entity for farther interaction with app
     */
    TransactionEntity get(String transactionID);

    /**
     * get set of entities by number of an account
     * @param accountEntity get entity by number of an account
     * @return set of entities for farther interaction
     */
    Set<TransactionEntity> get(AccountEntity accountEntity);

    /**
     * Return true if transaction with ID exists
     * @param transactionalID find entity by ID
     * @return true if transaction with ID exists
     */
    boolean isExist(String transactionalID);
}
