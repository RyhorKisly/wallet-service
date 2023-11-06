package io.ylab.walletservice.dao;

import io.ylab.starteraspectaudit.aop.annotations.Auditable;
import io.ylab.starteraspectlogger.aop.annotations.Loggable;
import io.ylab.walletservice.dao.api.IAccountDao;
import io.ylab.walletservice.dao.entity.AccountEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 * Class for generic operations on a repository for an Account.
 * This an implementation of {@link IAccountDao}
 */
@Repository
@RequiredArgsConstructor
@Loggable
@Auditable
public class AccountDao implements IAccountDao {

    /**
     * String for readability
     */
    private static final String ID = "id";

    /**
     * String for readability
     */
    private static final String BALANCE = "balance";

    /**
     * String for readability
     */
    private static final String USER_ID = "user_id";

    /**
     * Query for finding account by id
     */
    private static final String FIND_ACCOUNT_BY_ACCOUNT_ID = "SELECT id, balance, user_id FROM app.\"Account\" WHERE id = ?;";

    /**
     * Query for finding account by id and login
     */
    private static final String FIND_ACCOUNT_BY_ID_AND_LOGIN = "SELECT app.\"Account\".id, balance, user_id " +
            "FROM app.\"Account\" " +
            "INNER JOIN app.\"User\" ON app.\"Account\".user_id = app.\"User\".id " +
            "WHERE app.\"Account\".id = ? AND login = ?;";

    /**
     * Query for finding account by login
     */
    private static final String FIND_ACCOUNT_BY_USER_ID = "SELECT app.\"Account\".id, balance, user_id " +
            "FROM app.\"Account\" " +
            "WHERE user_id = ?;";

    /**
     * Query for saving account
     */
    private static final String SAVE_ACCOUNT = "INSERT INTO app.\"Account\"(balance, user_id) VALUES (?, ?) RETURNING id;";

    /**
     * Query for updating account with balance
     */
    private static final String UPDATE_ACCOUNT = "UPDATE app.\"Account\" SET balance = ? WHERE id = ?;";

    /**
     * define a field with a type {@link DataSource} for further aggregation
     */
    private final DataSource connection;

    /**
     * find entity by number of an account
     * @param numberAccount find entity by number of an account
     * @return entity from {@code accounts}
     */
    @Override
    public Optional<AccountEntity> find(Long numberAccount) {
        AccountEntity accountEntity = null;
        try (Connection conn =  this.connection.getConnection();
             PreparedStatement ps = conn.prepareStatement(FIND_ACCOUNT_BY_ACCOUNT_ID)) {
            ps.setObject(1, numberAccount);
            try(ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    accountEntity = new AccountEntity();
                    accountEntity.setId(rs.getLong(ID));
                    accountEntity.setBalance(rs.getBigDecimal(BALANCE));
                    accountEntity.setUserId(rs.getLong(USER_ID));
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return Optional.ofNullable(accountEntity);
    }

    /**
     * find entity by number of the account and login of the user
     * @param numberAccount find entity by number of Account
     * @param login find entity by user login
     * @return entity from {@code accounts}
     */
    @Override
    public Optional<AccountEntity> find(Long numberAccount, String login) {
        AccountEntity accountEntity = null;
        try (Connection conn =  this.connection.getConnection();
             PreparedStatement ps = conn.prepareStatement(FIND_ACCOUNT_BY_ID_AND_LOGIN)) {
            ps.setObject(1, numberAccount);
            ps.setObject(2, login);
            try(ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    accountEntity = new AccountEntity();
                    accountEntity.setId(rs.getLong(ID));
                    accountEntity.setBalance(rs.getBigDecimal(BALANCE));
                    accountEntity.setUserId(rs.getLong(USER_ID));
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return Optional.ofNullable(accountEntity);
    }

    @Override
    public Optional<AccountEntity> findByUserId(Long userId) {
        AccountEntity accountEntity = null;
        try (Connection conn =  this.connection.getConnection();
             PreparedStatement ps = conn.prepareStatement(FIND_ACCOUNT_BY_USER_ID)) {
            ps.setObject(1, userId);
            try(ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    accountEntity = new AccountEntity();
                    accountEntity.setId(rs.getLong(ID));
                    accountEntity.setBalance(rs.getBigDecimal(BALANCE));
                    accountEntity.setUserId(rs.getLong(USER_ID));
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return Optional.ofNullable(accountEntity);
    }

    /**
     * Saves a given entity.
     * Use the returned instance for further operations as the save operation
     * might have changed the entity instance completely.
     * Check if account already has user with specific login
     * @param entity save given entity
     * @return the saved entity in {@code accounts}
     */
    @Override
    public AccountEntity save(AccountEntity entity) {
        try (Connection conn =  this.connection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SAVE_ACCOUNT)) {

            ps.setObject(1, entity.getBalance());
            ps.setObject(2, entity.getUserId());

            try(ResultSet rs = ps.executeQuery()) {
                if(rs.next()) {
                    entity.setId(rs.getLong(ID));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return entity;
    }

    /**
     * Update a given entity.
     * Use the returned instance for further operations as the update operation
     * @param entity update given entity in {@code accounts}
     * @return updated entity form {@code accounts}
     */
    @Override
    public AccountEntity updateBalance(AccountEntity entity) {
        try (Connection conn =  this.connection.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_ACCOUNT)) {

            ps.setObject(1, entity.getBalance());
            ps.setObject(2, entity.getId());
            ps.execute();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return entity;
    }

}
