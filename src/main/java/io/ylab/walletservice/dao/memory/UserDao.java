package io.ylab.walletservice.dao.memory;

import io.ylab.walletservice.core.enums.UserRole;
import io.ylab.walletservice.dao.entity.UserEntity;
import io.ylab.walletservice.dao.api.IUserDao;

import java.util.HashMap;
import java.util.Map;

/**
 * Class for generic operations on a repository for User.
 * This an implementation of {@link IUserDao}
 */
public class UserDao implements IUserDao {

    /**
     * Used when trying to save an entity, it is discovered that a user with the same login already exists
     */
    private static final String LOGIN_EXIST = "Such login exist, try again!";

    /**
     * Stores a map of entities. Key - String login, value - entity.
     */
    private final Map<String, UserEntity> users = new HashMap<>();

    /**
     * Constructor specified initial user account and add in {@code users}
     */
    public UserDao() {
        {
            UserEntity entity = new UserEntity(
                    "admin",
                    "nimda",
                    UserRole.ADMIN
            );
            users.put(entity.getLogin(), entity);
        }
    }

    /**
     * find entity by login
     * @param login find entity by login in {@code users}
     * @return entity from {@code users}
     */
    @Override
    public UserEntity find(String login) {
        return users.get(login);
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
        if(users.containsKey(entity.getLogin())) {
            System.out.println(LOGIN_EXIST);
        } else {
            users.put(entity.getLogin(), entity);
        }
        return entity;
    }
}
