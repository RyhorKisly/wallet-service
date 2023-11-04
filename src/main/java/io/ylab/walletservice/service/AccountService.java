package io.ylab.walletservice.service;

import io.ylab.walletservice.aop.annotations.Loggable;
import io.ylab.walletservice.core.enums.Operation;
import io.ylab.walletservice.core.dto.AccountDTO;
import io.ylab.walletservice.core.dto.TransactionDTO;
import io.ylab.walletservice.core.exceptions.NotExistUserException;
import io.ylab.walletservice.core.exceptions.NotUniqueException;
import io.ylab.walletservice.dao.api.IAccountDao;
import io.ylab.walletservice.dao.entity.AccountEntity;
import io.ylab.walletservice.dao.entity.UserEntity;
import io.ylab.walletservice.service.api.IAccountService;
import io.ylab.walletservice.service.api.IAuditService;
import io.ylab.walletservice.service.api.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Class for generic operations on a service for an Account.
 * Interact with {@link IAccountDao} and {@link IAuditService}
 * This an implementation of {@link IAccountService}
 */
@Service
@RequiredArgsConstructor
@Loggable
public class AccountService implements IAccountService {

    /**
     * Used when trying to save an entity, it is discovered that a user with the same login already has an account
     */
    private static final String USER_HAS_ACCOUNT = "You have an account. You can't create more than one account";

    /**
     * Message when try save account with existed user_id in other created account
     */
    private static final String USER_ID_UNIQUE = "account_user_id_unique";

    /**
     * Message when try to find account by user, but user does not exist
     */
    private static final String USER_NOT_EXIST_ID_RESPONSE = "Such user id does not exist!";

    /**
     * define a field with a type {@link IAccountDao} for further aggregation
     */
    private final IAccountDao accountDao;

    /**
     * define a field with a type {@link IUserService} for further aggregation
     */

    private final IUserService userService;

    /**
     * Create entity.
     * Use the returned instance for further operations as the save operation
     * @param accountDTO used to create entity
     * @return entity for farther interaction with app
     * @throws NotUniqueException other problem with db.
     */
    @Override
    public AccountEntity create(AccountDTO accountDTO) {
        UserEntity userEntity = userService.get(accountDTO.getId());
        AccountEntity entity = new AccountEntity();
        entity.setBalance(new BigDecimal("0.0"));
        entity.setUserId(userEntity.getId());

        return saveOrThrow(entity);
    }

    /**
     * get entity by number of an account
     * @param numberAccount get entity by number of an account
     * @return entity for farther interaction with app
     * @throws RuntimeException other problem with db.
     */
    @Override
    public AccountEntity get(Long numberAccount) {
        return accountDao.find(numberAccount)
                .orElseThrow(RuntimeException::new);
    }

    /**
     * get entity by number of the account and login of the user
     * @param numberAccount get entity by number of Account
     * @param login get entity by user login
     * @return entity for farther interaction with app
     * @throws RuntimeException other problem with db.
     */
    @Override
    public AccountEntity get(Long numberAccount, String login) {
        return accountDao.find(numberAccount, login)
                .orElseThrow(RuntimeException::new);
    }

    /**
     * get entity by number of the account and id of the user
     * @param userId get entity by user id
     * @return entity for farther interaction with app
     * @throws NotExistUserException when try to find account by user id, but user does not exist
     */
    @Override
    public AccountEntity getByUser(Long userId) {
        return accountDao.findByUserId(userId)
                .orElseThrow(() -> new NotExistUserException(USER_NOT_EXIST_ID_RESPONSE));
    }


    /**
     * Update entity with given number of account.
     * Use the returned instance for further operations as the update operation
     * Check if credit or debit transaction. If debit - check whether sumOfTransact more than balance
     * @param numberAccount used to update account with this number
     * @param transactionDTO used for checking can the operation be performed
     * @return updated entity
     * @throws RuntimeException other problem with db.
     */
    @Override
    public AccountEntity updateBalance(Long numberAccount, TransactionDTO transactionDTO) {
        AccountEntity entity = accountDao.find(numberAccount).orElseThrow(RuntimeException::new);
        if(transactionDTO.getOperation().equals(Operation.CREDIT)) {
            entity.setBalance(entity.getBalance().add(transactionDTO.getSumOfTransaction()));
        } else if(transactionDTO.getOperation().equals(Operation.DEBIT)){
            if(entity.getBalance().compareTo(transactionDTO.getSumOfTransaction()) >= 0) {
                entity.setBalance(entity.getBalance().subtract(transactionDTO.getSumOfTransaction()));
            } else {
                return null;
            }
        }
        return accountDao.updateBalance(entity);
    }

    /**
     * Calls save method of userDao.
     * @param entity for saving.
     * @return accountEntity if not exist return account in db.
     * @throws NotExistUserException if account exist in db.
     * @throws RuntimeException other problem with db.
     */
    private AccountEntity saveOrThrow(AccountEntity entity) {
        try{
            entity = accountDao.save(entity);
        } catch (RuntimeException ex) {
            if(ex.getMessage().contains(USER_ID_UNIQUE)) {
                throw new NotUniqueException(USER_HAS_ACCOUNT, ex);
            } else {
                throw new RuntimeException(ex);
            }
        }
        return entity;
    }
}
