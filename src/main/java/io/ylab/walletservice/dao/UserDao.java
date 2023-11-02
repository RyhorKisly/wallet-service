package io.ylab.walletservice.dao;

import io.ylab.walletservice.aop.annotations.Auditable;
import io.ylab.walletservice.aop.annotations.Loggable;
import io.ylab.walletservice.core.enums.UserRole;
import io.ylab.walletservice.core.exceptions.NotUniqueException;
import io.ylab.walletservice.dao.ds.api.IConnectionWrapper;
import io.ylab.walletservice.dao.api.IUserDao;
import io.ylab.walletservice.dao.entity.UserEntity;
import lombok.RequiredArgsConstructor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class for generic operations on a repository for User.
 * This an implementation of {@link IUserDao}
 */
@RequiredArgsConstructor
@Auditable
public class UserDao implements IUserDao {

    /**
     * Message if throws SQLException
     */
    private static final String ERROR_CONNECTION = "Error connecting to database";

    /**
     * Message when try save user with existed login
     */
    private static final String USER_LOGIN_UNIQUE = "user_login_unique";
    private static final String USER_LOGIN_EXIST = "User with such login exist";

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
     * Used when trying to save an entity, it is discovered that a user with the same login already exists
     */
    private static final String LOGIN_EXIST = "Such login exist, try again!";

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
     * define a field with a type {@link IConnectionWrapper} for further aggregation
     */
    private final IConnectionWrapper connection;

    /**
     * find entity by login
     * @param login find entity by login in {@code users}
     * @return entity from {@code users}
     */
    public UserEntity find(String login) {
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
        } catch (SQLException e) {
            throw new RuntimeException(ERROR_CONNECTION, e);
        }
        return userEntity;
    }

    @Override
    public UserEntity find(Long id) {
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
        } catch (SQLException e) {
            throw new RuntimeException(ERROR_CONNECTION, e);
        }
        return userEntity;
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
            if(ex.getMessage().contains(USER_LOGIN_UNIQUE)) {
                throw new NotUniqueException(USER_LOGIN_EXIST, ex);
            }
        }
        return entity;
    }

}
