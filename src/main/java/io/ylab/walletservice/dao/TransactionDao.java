package io.ylab.walletservice.dao;

import io.ylab.walletservice.core.enums.Operation;
import io.ylab.walletservice.dao.ds.api.IConnectionWrapper;
import io.ylab.walletservice.dao.api.ITransactionDao;
import io.ylab.walletservice.dao.entity.TransactionEntity;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Class for generic operations on a repository for Transaction.
 * This an implementation of {@link ITransactionDao}
 */
@RequiredArgsConstructor
public class TransactionDao implements ITransactionDao {

    /**
     * String for readability
     */
    private static final String ID = "id";

    /**
     * String for readability
     */
    private static final String OPERATION = "operation";

    /**
     * String for readability
     */
    private static final String SUM_OF_TRANSACTION = "sum_of_transaction";

    /**
     * String for readability
     */
    private static final String ACCOUNT_ID = "account_id";

    /**
     * String for readability
     */
    private static final String DT_CREATE = "dt_create";

    /**
     * Message if throws SQLException
     */
    private static final String ERROR_CONNECTION = "Error connecting to database";

    /**
     * Query for finding transaction by id
     */
    private static final String FIND_TRANSACTION = "SELECT id, operation, sum_of_transaction, account_id, dt_create " +
            "FROM app.\"Transaction\" WHERE id = ?;";

    /**
     * Query for saving transaction
     */
    private static final String SAVE_TRANSACTION = "INSERT INTO app.\"Transaction\"" +
            "(id, operation, sum_of_transaction, account_id, dt_create) " +
            "VALUES (?, ?, ?, ?, ?);";

    /**
     * Query for finding all transaction by account id
     */
    private static final String FIND_ALL_TRANSACTION_BY_ACCOUNT_ASC_DATE = "SELECT id, operation, sum_of_transaction, account_id, dt_create " +
            "FROM app.\"Transaction\" " +
            "WHERE account_id = ? " +
            "ORDER BY dt_create;";

    /**
     * Query for deleting transaction
     */
    private static final String DELETE_TRANSACTION = "DELETE FROM app.\"Transaction\" WHERE id = ?;";

    /**
     * define a field with a type {@link IConnectionWrapper} for further aggregation
     */
    private final IConnectionWrapper connection;

    /**
     * find entity by ID
     * @param transactionalId find entity by ID
     * @return entity from {@code transactions}
     */
    @Override
    public TransactionEntity find(String transactionalId) {
        TransactionEntity entity = null;
        try (Connection conn = this.connection.getConnection();
             PreparedStatement ps = conn.prepareStatement(FIND_TRANSACTION)) {
            ps.setObject(1, transactionalId);
            try(ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    entity = new TransactionEntity();
                    entity.setTransactionId(transactionalId);
                    entity.setOperation(Operation.valueOf(rs.getString(OPERATION)));
                    entity.setSumOfTransaction(new BigDecimal(rs.getString(SUM_OF_TRANSACTION)));
                    entity.setAccountId(rs.getLong(ACCOUNT_ID));
                    entity.setDtCreate(rs.getTimestamp(DT_CREATE).toLocalDateTime());
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(ERROR_CONNECTION, e);
        }
        return entity;
    }

    /**
     * Return true if transaction with ID exists from {@code transactions}
     * @param transactionalId find entity by ID in {@code transactions}
     * @return true if transaction with ID exists in {@code transactions}
     */
    @Override
    public boolean isExist(String transactionalId) {
        boolean check = true;
        TransactionEntity entity = null;
        try (Connection conn = this.connection.getConnection();
             PreparedStatement ps = conn.prepareStatement(FIND_TRANSACTION)) {
            ps.setObject(1, transactionalId);
            try(ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    entity = new TransactionEntity();
                    entity.setTransactionId(transactionalId);
                    entity.setOperation(Operation.valueOf(rs.getString(OPERATION)));
                    entity.setSumOfTransaction(new BigDecimal(rs.getString(SUM_OF_TRANSACTION)));
                    entity.setAccountId(rs.getLong(ACCOUNT_ID));
                    entity.setDtCreate(rs.getTimestamp(DT_CREATE).toLocalDateTime());
                }
            }
            if(entity == null) {
                check = false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(ERROR_CONNECTION, e);
        }
        return check;
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
        try (Connection conn = this.connection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SAVE_TRANSACTION)) {

            ps.setObject(1, entity.getTransactionId());
            ps.setObject(2, entity.getOperation().toString());
            ps.setObject(3, entity.getSumOfTransaction());
            ps.setObject(4, entity.getAccountId());
            ps.setObject(5, entity.getDtCreate());
            ps.execute();

        } catch (SQLException e) {
            throw new RuntimeException(ERROR_CONNECTION, e);
        }
        return entity;
    }

    /**
     * find set of entities by number of an account
     * @param numberAccount find entity by number of an account in {@code transactions}
     * @return set of entities from {@code transactions}
     */
    @Override
    public Set<TransactionEntity> findAllByNumberAccountAscByDTCreate(Long numberAccount) {
        Set<TransactionEntity> historyOfTransactions = new LinkedHashSet<>();
        try (Connection conn = this.connection.getConnection();
             PreparedStatement ps = conn.prepareStatement(FIND_ALL_TRANSACTION_BY_ACCOUNT_ASC_DATE)) {
            ps.setObject(1, numberAccount);
            try(ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    TransactionEntity transaction = new TransactionEntity();
                    transaction.setTransactionId(rs.getString(ID));
                    transaction.setOperation(Operation.valueOf(rs.getString(OPERATION)));
                    transaction.setSumOfTransaction(new BigDecimal(rs.getString(SUM_OF_TRANSACTION)));
                    transaction.setAccountId(rs.getLong(ACCOUNT_ID));
                    transaction.setDtCreate(rs.getTimestamp(DT_CREATE).toLocalDateTime());
                    historyOfTransactions.add(transaction);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(ERROR_CONNECTION, e);
        }
        return historyOfTransactions;
    }

    /**
     * Method just for testing method save()
     * @param TransactionId for finding and deleting transaction
     */
    @Override
    public void delete(String TransactionId) {
        try (Connection connection = this.connection.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_TRANSACTION);
            preparedStatement.setString(1, TransactionId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(ERROR_CONNECTION, e);
        }
    }
}
