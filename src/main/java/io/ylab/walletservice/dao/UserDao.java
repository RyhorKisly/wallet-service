package io.ylab.walletservice.dao;

import io.ylab.walletservice.aop.annotations.Auditable;
import io.ylab.walletservice.aop.annotations.Loggable;
import io.ylab.walletservice.core.enums.UserRole;
import io.ylab.walletservice.dao.api.IUserDao;
import io.ylab.walletservice.dao.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 * Class for generic operations on a repository for User.
 * This an implementation of {@link IUserDao}
 */
@Repository
@RequiredArgsConstructor
@Loggable
@Auditable
public class UserDao implements IUserDao {

    /**
     * String for readability
     */
    private static final String ID = "id";

    /**
     * String for readability
     */
    private static final String LOGIN = "login";
    /**
     * String for readability
     */
    private static final String PASSWORD = "password";

    /**
     * String for readability
     */
    private static final String ROLE = "role";

    /**
     * Query for finding user in bd
     */
    private static final String FIND_USER_BY_LOGIN = "SELECT id, login, password, role FROM app.\"User\" WHERE login = ?;";
    private static final String FIND_USER_BY_ID = "SELECT id, login, password, role FROM app.\"User\" WHERE id = ?;";

    /**
     * Query for saving user in bd
     */
    private static final String SAVE_USER = "INSERT INTO app.\"User\"(login, password, role) VALUES (?, ?, ?) RETURNING id;";

    /**
     * define a field with a type {@link DataSource} for further aggregation
     */
    private final DataSource connection;

    /**
     * find entity by login
     * @param login find entity by login in {@code users}
     * @return entity from {@code users}
     */
    @Override
    public Optional<UserEntity> find(String login) {
        UserEntity userEntity = null;
        try (Connection conn = this.connection.getConnection();
             PreparedStatement ps = conn.prepareStatement(FIND_USER_BY_LOGIN)) {
            ps.setObject(1, login);
            try(ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    userEntity = new UserEntity();
                    userEntity.setId(rs.getLong(ID));
                    userEntity.setLogin(rs.getString(LOGIN));
                    userEntity.setPassword(rs.getString(PASSWORD));
                    userEntity.setRole(UserRole.valueOf(rs.getString(ROLE)));
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return Optional.ofNullable(userEntity);
    }

    @Override
    public Optional<UserEntity> find(Long id) {
        UserEntity userEntity = null;
        try (Connection conn = this.connection.getConnection();
             PreparedStatement ps = conn.prepareStatement(FIND_USER_BY_ID)) {
            ps.setObject(1, id);
            try(ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    userEntity = new UserEntity();
                    userEntity.setId(rs.getLong(ID));
                    userEntity.setLogin(rs.getString(LOGIN));
                    userEntity.setPassword(rs.getString(PASSWORD));
                    userEntity.setRole(UserRole.valueOf(rs.getString(ROLE)));
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return Optional.ofNullable(userEntity);
    }

    /**
     * Saves a given entity.
     * Use the returned instance for further operations as the save operation
     * might have changed the entity instance completely.
     * Check login exists or not
     * @param entity save given entity in {@code users}
     * @return the saved entity from {@code users}
     */
    @Override
    public UserEntity save(UserEntity entity) {
        try (Connection conn = this.connection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SAVE_USER)) {

            ps.setObject(1, entity.getLogin());
            ps.setObject(2, entity.getPassword());
            ps.setObject(3, entity.getRole().toString());

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
