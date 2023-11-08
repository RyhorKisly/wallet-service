package io.ylab.walletservice.dao;

import com.zaxxer.hikari.HikariDataSource;
import io.ylab.starteraspectlogger.aop.annotations.Loggable;
import io.ylab.walletservice.dao.api.IAuditDao;
import io.ylab.walletservice.dao.entity.AuditEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Class for generic operations on a repository for an Audit.
 * This an implementation of {@link IAuditDao}
 */
@Repository
@RequiredArgsConstructor
@Loggable
public class AuditDao implements IAuditDao {

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
     * define a field with a type {@link HikariDataSource} for further aggregation
     */
    private final HikariDataSource connection;

    /**
     * find set of entities by id of the user
     * @return list of entities from {@code audits}
     */
    @Override
    public List<AuditEntity> findAllAscByDTCreate() {
        List<AuditEntity> audit = new LinkedList<>();
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
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
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
        } catch (SQLException ex) {
                throw new RuntimeException(ex);
        }
        return entity;
    }

}
