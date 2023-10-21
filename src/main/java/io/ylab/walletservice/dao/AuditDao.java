package io.ylab.walletservice.dao;

import io.ylab.walletservice.dao.api.IAuditDao;
import io.ylab.walletservice.dao.ds.api.IConnectionWrapper;
import io.ylab.walletservice.dao.entity.AuditEntity;
import lombok.RequiredArgsConstructor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Class for generic operations on a repository for an Audit.
 * This an implementation of {@link IAuditDao}
 */
@RequiredArgsConstructor
public class AuditDao implements IAuditDao {

    /**
     * Message if throws SQLException
     */
    private static final String ERROR_CONNECTION = "Error connecting to database";

    /**
     * String for readability
     */
    private static final String ID = "id";

    /**
     * String for readability
     */
    private static final String USER_ID = "user_id";

    /**
     * String for readability
     */
    private static final String TEXT = "text";

    /**
     * String for readability
     */
    private static final String DT_CREATE = "dt_create";

    /**
     * Query for finding all audit by user login
     */
    private static final String GET_AUDIT = "SELECT app.\"Audit\".id, dt_create, user_id, text " +
            "FROM app.\"Audit\" " +
            "INNER JOIN app.\"User\" ON app.\"Audit\".user_id = app.\"User\".id " +
            "WHERE login = ? " +
            "ORDER BY dt_create;";

    /**
     * Query for saving audit
     */
    private static final String SAVE_AUDIT = "INSERT INTO app.\"Audit\"(dt_create, user_id, text) " +
            "VALUES (?, ?, ?) " +
            "RETURNING id;";

    /**
     * Query for deleting audit
     */
    private static final String DELETE_AUDIT = "DELETE FROM app.\"Audit\" WHERE id = ?;";

    /**
     * Query for deleting audit by user login
     */
    private static final String DELETE_AUDIT_BY_LOGIN = "DELETE FROM app.\"Audit\" " +
            "WHERE user_id = ?;";

    /**
     * define a field with a type {@link IConnectionWrapper} for further aggregation
     */
    private final IConnectionWrapper connection;

    /**
     * find set of entities by login of the user
     * @param login find entity by user login
     * @return set of entities from {@code audits}
     */
    @Override
    public Set<AuditEntity> findAllByLoginAscByDTCreate(String login) {
        Set<AuditEntity> audit = new TreeSet<>(Comparator.comparing(AuditEntity::getDtCreate));
        try (Connection conn = this.connection.getConnection();
             PreparedStatement ps = conn.prepareStatement(GET_AUDIT)) {
            ps.setObject(1, login);
            try(ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    AuditEntity auditEntity = new AuditEntity();
                    auditEntity.setId(rs.getLong(ID));
                    auditEntity.setUserId(rs.getLong(USER_ID));
                    auditEntity.setText(rs.getString(TEXT));
                    auditEntity.setDtCreate(rs.getTimestamp(DT_CREATE).toLocalDateTime());
                    audit.add(auditEntity);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(ERROR_CONNECTION, e);
        }
        return audit;
    }

    /**
     * Saves a given entity.
     * Use the returned instance for further operations as the save operation
     * might have changed the entity instance completely.
     * @param entity save given entity in {@code audits}
     * @return saved entity from {@code audits}
     */
    @Override
    public AuditEntity save(AuditEntity entity) {
        try (Connection conn =  this.connection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SAVE_AUDIT)) {

            ps.setObject(1, entity.getDtCreate());
            ps.setObject(2, entity.getUserId());
            ps.setObject(3, entity.getText());

            try(ResultSet rs = ps.executeQuery()) {
                if(rs.next()) {
                    entity.setId(rs.getLong(ID));
                }
            }
        } catch (SQLException e) {
                throw new RuntimeException(ERROR_CONNECTION, e);
        }
        return entity;
    }

    /**
     * Method just for testing method save()
     * @param id for finding and deleting audit
     */
    @Override
    public void delete(Long id) {
        try (Connection conn =  this.connection.getConnection()){
            PreparedStatement preparedStatement = conn.prepareStatement(DELETE_AUDIT);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(ERROR_CONNECTION, e);
        }
    }

    /**
     * Method just for testing
     * @param userId for finding and deleting audit
     */
    @Override
    public void deleteByUserId(Long userId) {
        try (Connection conn =  this.connection.getConnection()){
            PreparedStatement preparedStatement = conn.prepareStatement(DELETE_AUDIT_BY_LOGIN);
            preparedStatement.setLong(1, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(ERROR_CONNECTION, e);
        }
    }
}
