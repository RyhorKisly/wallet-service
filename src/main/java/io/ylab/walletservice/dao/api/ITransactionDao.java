package io.ylab.walletservice.dao.api;

import io.ylab.walletservice.dao.entity.TransactionEntity;

import java.util.Optional;
import java.util.Set;

/**
 * Interface for generic operations on a repository for Transactions.
 */
public interface ITransactionDao {

    /**
     * find entity by ID
     * @param transactionalId find entity by ID
     * @return entity from storage
     */
    Optional<TransactionEntity> find(String transactionalId);

    /**
     * Return true if transaction with ID exists
     * @param transactionalId find entity by ID
     * @return true if transaction with ID exists
     */
    boolean isExist(String transactionalId);

    /**
     * Saves a given entity.
     * Use the returned instance for further operations as the save operation
     * might have changed the entity instance completely.
     * @param entity save given entity
     * @return the saved entity
     */
    TransactionEntity save(TransactionEntity entity);

    /**
     * find set of entities by number of an account
     * @param numberAccount find entity by number of an account
     * @return set of entities from storage
     */
    Set<TransactionEntity> findAllByNumberAccountAscByDTCreate(Long numberAccount);

    /**
     * Method for deleting transaction
     * @param TransactionId for finding and deleting transaction
     */
    void delete(String TransactionId);

}
