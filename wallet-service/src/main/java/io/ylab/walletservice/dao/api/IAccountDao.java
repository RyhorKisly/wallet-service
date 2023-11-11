package io.ylab.walletservice.dao.api;


import io.ylab.walletservice.dao.entity.AccountEntity;

import java.util.Optional;

/**
 * Interface for generic operations on a repository for an Account.
 */
public interface IAccountDao {
    /**
     * find entity by number of an account
     * @param numberAccount find entity by number of an account
     * @return entity from storage
     */
    Optional<AccountEntity> find(Long numberAccount);

    /**
     * find entity by number of the account and login of the user
     * @param numberAccount find entity by number of Account
     * @param login find entity by user login
     * @return entity from storage
     */
    Optional<AccountEntity> find(Long numberAccount, String login);

    /**
     * find entity by number of the account and id of the user
     * @param userId find entity by user id
     * @return entity from storage
     */
    Optional<AccountEntity> findByUserId(Long userId);

    /**
     * Saves a given entity.
     * Use the returned instance for further operations as the save operation
     * might have changed the entity instance completely.
     * @param entity save given entity
     * @return the saved entity
     */
    AccountEntity save(AccountEntity entity);

    /**
     * Update a given entity.
     * Use the returned instance for further operations as the update operation
     * @param entity update given entity
     * @return the updated entity
     */
    AccountEntity updateBalance(AccountEntity entity);

}
