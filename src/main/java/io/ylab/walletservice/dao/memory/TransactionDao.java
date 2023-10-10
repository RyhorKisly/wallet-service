package io.ylab.walletservice.dao.memory;

import io.ylab.walletservice.dao.api.ITransactionDao;
import io.ylab.walletservice.dao.entity.TransactionEntity;

import java.util.*;

/**
 * Class for generic operations on a repository for Transaction.
 * This an implementation of {@link ITransactionDao}
 */
public class TransactionDao implements ITransactionDao {

    /**
     * Used when ID is empty or exists in {@code transactions}
     */
    private static final String TRANSACTION_ID_EXIST_OR_EMPTY = "Empty or exist transactionID!";

    /**
     * Stores a map of entities. Key - String ID, value - entity.
     */
    private final Map<String, TransactionEntity> transactions = new HashMap<>();

    /**
     * find entity by ID
     * @param transactionalId find entity by ID
     * @return entity from {@code transactions}
     */
    @Override
    public TransactionEntity find(String transactionalId) {
        return transactions.get(transactionalId);
    }

    /**
     * Return true if transaction with ID exists from {@code transactions}
     * @param transactionalId find entity by ID in {@code transactions}
     * @return true if transaction with ID exists in {@code transactions}
     */
    @Override
    public boolean isExist(String transactionalId) {
        if(transactions.containsKey(transactionalId)) {
            return true;
        }
        return false;
    }

    /**
     * Saves a given entity.
     * Use the returned instance for further operations as the save operation
     * might have changed the entity instance completely.
     * Check if transaction id empty or exist in {@code transactions}
     * @param entity save given entity in {@code transactions}
     * @return the saved entity from {@code transactions}
     */
    @Override
    public TransactionEntity save(TransactionEntity entity) {
        if(entity.getTransactionId().isEmpty() || transactions.containsKey(entity.getTransactionId())) {
            System.err.println(TRANSACTION_ID_EXIST_OR_EMPTY);
            System.exit(1);
            return entity;
        }
        transactions.put(entity.getTransactionId(), entity);
        return entity;
    }

    /**
     * find set of entities by number of an account
     * @param numberAccount find entity by number of an account in {@code transactions}
     * @return set of entities from {@code transactions}
     */
    @Override
    public Set<TransactionEntity> findAllByNumberAccountAscByDTCreate(UUID numberAccount) {
        Set<TransactionEntity> historyOfTransactions = new TreeSet<>(Comparator.comparing(TransactionEntity::getDtCreate));
        for (TransactionEntity value : transactions.values()) {
            if(value.getNumberAccount().equals(numberAccount)) {
                historyOfTransactions.add(value);
            }
        }
        return historyOfTransactions;
    }
}
