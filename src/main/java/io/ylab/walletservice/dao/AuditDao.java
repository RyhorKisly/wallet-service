package io.ylab.walletservice.dao;

import io.ylab.walletservice.aop.annotations.Loggable;
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
@Loggable
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
    private static final String TEXT = "text";

    /**
     * String for readability
     */
    private static final String DT_CREATE = "dt_create";

    /**
     * Query for finding all audit by user login
     */
    private static final String GET_AUDIT = "SELECT app.\"Audit\".id, dt_create, text " +
            "FROM app.\"Audit\" " +
            "ORDER BY dt_create;";

    /**
     * Query for saving audit
     */
    private static final String SAVE_AUDIT = "INSERT INTO app.\"Audit\"(dt_create, text) " +
            "VALUES (?, ?) " +
            "RETURNING id;";

    /**
     * define a field with a type {@link IConnectionWrapper} for further aggregation
     */
    private final IConnectionWrapper connection;

    /**
     * find set of entities by id of the user
     * @return set of entities from {@code audits}
     */
    @Override
    public Set<AuditEntity> findAllAscByDTCreate() {
        Set<AuditEntity> audit = new TreeSet<>(Comparator.comparing(AuditEntity::getDtCreate));
        try (Connection conn = this.connection.getConnection();
             PreparedStatement ps = conn.prepareStatement(GET_AUDIT)) {
            try(ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    AuditEntity auditEntity = new AuditEntity();
                    auditEntity.setId(rs.getLong(ID));
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
            ps.setObject(2, entity.getText());

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

}
