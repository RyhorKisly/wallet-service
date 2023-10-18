package io.ylab.walletservice.service;

import io.ylab.walletservice.core.enums.Operation;
import io.ylab.walletservice.core.dto.AccountDTO;
import io.ylab.walletservice.core.dto.AuditDTO;
import io.ylab.walletservice.core.dto.TransactionDTO;
import io.ylab.walletservice.dao.api.IAccountDao;
import io.ylab.walletservice.dao.entity.AccountEntity;
import io.ylab.walletservice.dao.entity.UserEntity;
import io.ylab.walletservice.service.api.IAccountService;
import io.ylab.walletservice.service.api.IAuditService;
import io.ylab.walletservice.service.api.IUserService;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

/**
 * Class for generic operations on a service for an Account.
 * Interact with {@link IAccountDao} and {@link IAuditService}
 * This an implementation of {@link IAccountService}
 */
@RequiredArgsConstructor
public class AccountService implements IAccountService {

    /**
     * Print in console that account was created
     */
    private static final String ACCOUNT_CREATED = "Account was created";

    /**
     * define a field with a type {@link IAccountDao} for further aggregation
     */
    private final IAccountDao accountDao;

    /**
     * define a field with a type {@link IAuditService} for further aggregation
     */
    private final IAuditService auditService;
    private final IUserService userService;

    /**
     * Create entity.
     * Use the returned instance for further operations as the save operation
     * @param accountDTO used to create entity
     * @return entity for farther interaction with app
     */
    @Override
    public AccountEntity create(AccountDTO accountDTO) {
        UserEntity userEntity = userService.get(accountDTO.getLogin());
        AccountEntity entity = new AccountEntity();
        entity.setBalance(new BigDecimal("0.0"));
        entity.setUserId(userEntity.getId());

        AccountEntity accountEntity = accountDao.save(entity);
        AuditDTO auditDTO = new AuditDTO(accountEntity.getUserId(), ACCOUNT_CREATED);
        auditService.create(auditDTO);
        return accountEntity;
    }

    /**
     * get entity by number of an account
     * @param numberAccount get entity by number of an account
     * @return entity for farther interaction with app
     */
    @Override
    public AccountEntity get(Long numberAccount) {
        return accountDao.find(numberAccount);
    }

    /**
     * get entity by number of the account and login of the user
     * @param numberAccount get entity by number of Account
     * @param login get entity by user login
     * @return entity for farther interaction with app
     */
    @Override
    public AccountEntity get(Long numberAccount, String login) {
        return accountDao.find(numberAccount, login);
    }

    /**
     * get entity by number of the account and login of the user
     * @param login get entity by user login
     * @return entity for farther interaction with app
     */
    @Override
    public AccountEntity get(String login) {
        return accountDao.find(login);
    }


    /**
     * Update entity with given number of account.
     * Use the returned instance for further operations as the update operation
     * Check if credit or debit transaction. If debit - check whether sumOfTransact more than balance
     * @param numberAccount used to update account with this number
     * @param transactionDTO used for checking can the operation be performed
     * @return the updated entity
     */
    @Override
    public AccountEntity updateBalance(Long numberAccount, TransactionDTO transactionDTO) {
        AccountEntity entity = accountDao.find(numberAccount);
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
}
