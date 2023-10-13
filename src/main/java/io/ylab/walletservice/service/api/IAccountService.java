package io.ylab.walletservice.service.api;


import io.ylab.walletservice.core.dto.AccountDTO;
import io.ylab.walletservice.core.dto.TransactionDTO;
import io.ylab.walletservice.dao.entity.AccountEntity;

import java.util.UUID;

/**
 * Interface for generic operations on a service for an Account.
 */
public interface IAccountService {

    /**
     * Create entity.
     * Use the returned instance for further operations as the save operation
     * @param accountDTO used to create entity
     * @return entity for farther interaction with app
     */
    AccountEntity create(AccountDTO accountDTO);

    /**
     * get entity by number of an account
     * @param numberAccount get entity by number of an account
     * @return entity for farther interaction with app
     */
    AccountEntity get(UUID numberAccount);

    /**
     * get entity by number of the account and login of the user
     * @param numberAccount get entity by number of Account
     * @param login get entity by user login
     * @return entity for farther interaction with app
     */
    AccountEntity get(String numberAccount, String login);

    /**
     * get entity by number of the account and login of the user
     * @param login get entity by user login
     * @return entity for farther interaction with app
     */
    AccountEntity get(String login);

    /**
     * Update entity with given number of account.
     * Use the returned instance for further operations as the update operation
     * @param numberAccount used to update account with this number
     * @param transactionDTO used for checking can the operation be performed
     * @return the updated entity
     */
    AccountEntity updateBalance(UUID numberAccount, TransactionDTO transactionDTO);

}
