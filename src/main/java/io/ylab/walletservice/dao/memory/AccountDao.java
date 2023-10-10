package io.ylab.walletservice.dao.memory;


import io.ylab.walletservice.dao.api.IAccountDao;
import io.ylab.walletservice.dao.entity.AccountEntity;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Class for generic operations on a repository for an Account.
 * This an implementation of {@link IAccountDao}
 */
public class AccountDao implements IAccountDao {

    /**
     * Used when trying to save an entity, it is discovered that a user with the same login already has an account
     */
    private static final String USER_HAS_ACCOUNT = "You have an account. You can't create more than one account";

    /**
     * Stores a map of entities. Key - UUID, value - entity.
     */
    private final Map<UUID, AccountEntity> accounts = new HashMap<>();

    /**
     * Constructor specified initial user account and add in {@code accounts}
     */
    public AccountDao() {
        {
            AccountEntity entity = new AccountEntity(
                    UUID.fromString("44e83a63-f1aa-43cf-b958-78d7e877c9ee"),
                    new BigDecimal("1000.0"),
                    "admin"
            );
            accounts.put(entity.getNumberAccount(), entity);
        }
    }

    /**
     * find entity by number of an account
     * @param numberAccount find entity by number of an account
     * @return entity from {@code accounts}
     */
    @Override
    public AccountEntity find(UUID numberAccount) {
        return accounts.get(numberAccount);
    }

    /**
     * find entity by number of the account and login of the user
     * @param numberAccount find entity by number of Account
     * @param login find entity by user login
     * @return entity from {@code accounts}
     */
    @Override
    public AccountEntity find(UUID numberAccount, String login) {
        AccountEntity entity = accounts.get(numberAccount);
        if(entity.getLogin().equals(login)) {
            return entity;
        } else {
            return null;
        }
    }

    /**
     * find entity by number of the account and login of the user
     * @param login find entity by user login
     * @return entity from {@code accounts}
     */
    @Override
    public AccountEntity find(String login) {
        for (AccountEntity value : accounts.values()) {
            if(value.getLogin().equals(login)) {
                return value;
            }
        }
        return null;
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
        for (AccountEntity value : accounts.values()) {
            if(value.getLogin().equals(entity.getLogin())) {
                System.out.println(USER_HAS_ACCOUNT);
                return entity;
            }
        }
        accounts.put(entity.getNumberAccount(), entity);
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
        return accounts.put(entity.getNumberAccount(), entity);
    }

}
