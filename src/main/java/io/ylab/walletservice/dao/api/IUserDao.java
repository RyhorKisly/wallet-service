package io.ylab.walletservice.dao.api;

import io.ylab.walletservice.dao.entity.UserEntity;

/**
 * Interface for generic operations on a repository for Users.
 */
public interface IUserDao {

    /**
     * find entity by login
     * @param login find entity by login
     * @return entity from storage
     */
    UserEntity find(String login);

    /**
     * Saves a given entity.
     * Use the returned instance for further operations as the save operation
     * might have changed the entity instance completely.
     * @param entity save given entity
     * @return the saved entity
     */
    UserEntity save(UserEntity entity);

    /**
     * Method just for testing method save()
     * @param id for finding and deleting user
     */
    void delete(Long id);
}
