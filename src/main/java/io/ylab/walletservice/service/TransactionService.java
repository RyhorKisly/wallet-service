package io.ylab.walletservice.service;

import io.ylab.walletservice.aop.annotations.Loggable;
import io.ylab.walletservice.core.dto.TransactionDTO;
import io.ylab.walletservice.core.exceptions.TransactionFailedException;
import io.ylab.walletservice.core.exceptions.WrongAccountIdException;
import io.ylab.walletservice.dao.api.ITransactionDao;
import io.ylab.walletservice.dao.entity.AccountEntity;
import io.ylab.walletservice.dao.entity.TransactionEntity;
import io.ylab.walletservice.service.api.IAccountService;
import io.ylab.walletservice.service.api.IAuditService;
import io.ylab.walletservice.service.api.ITransactionService;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * Class for generic operations on a service for an Account.
 * Interact with {@link ITransactionDao} and {@link IAuditService}
 * This an implementation of {@link ITransactionService}
 */
@RequiredArgsConstructor
@Loggable
public class TransactionService implements ITransactionService {

    /**
     * Message when can't update account because of wrong sum of transaction.
     */
    private static final String TRANSACTION_FAILED = "Transaction failed, change sum of transaction";

    /**
     * Message if account does not belong to user
     */
    private static final String WRONG_NUMBER_ACCOUNT = "This account number does not belong to you. Enter a different number.";

    /**
     * define a field with a type {@link ITransactionDao} for further aggregation
     */
    private final ITransactionDao transactionDao;

    private final static String WRONG_ACCOUNT = "Wrong account id";
    private final IAccountService accountService;

    /**
     * Create entity.
     * Use the returned instance for further operations as the save operation.
     * Save action in audit.
     * @param dto used for creating entity
     * @param userId used for creating entity
     * @return created entity
     */
    @Override
    public TransactionEntity create(TransactionDTO dto, Long userId) {
        AccountEntity accountEntity = accountService.get(userId);

        if(accountEntity == null) {
            throw new WrongAccountIdException(WRONG_ACCOUNT);
        }

        TransactionEntity entity = new TransactionEntity(
                dto.getTransactionId(),
                dto.getOperation(),
                dto.getSumOfTransaction(),
                dto.getAccountId(),
                LocalDateTime.now()
        );

        entity = transactionDao.save(entity);

        if (accountService.updateBalance(dto.getAccountId(), dto) == null) {
            transactionDao.delete(entity.getTransactionId());
            throw new TransactionFailedException(TRANSACTION_FAILED);
        }

        return entity;
    }

    @Override
    public TransactionEntity get(String transactionID) {
        return transactionDao.find(transactionID);
    }

    @Override
    public Set<TransactionEntity> get(Long accountId, Long UserId) {

        AccountEntity accountEntity = accountService.getByUser(UserId);
        if(accountEntity == null) {
            throw new WrongAccountIdException(WRONG_NUMBER_ACCOUNT);
        }
        if(!accountId.equals(accountEntity.getId())) {
            throw new WrongAccountIdException(WRONG_NUMBER_ACCOUNT);
        }

        return transactionDao.findAllByNumberAccountAscByDTCreate(accountId);
    }

    @Override
    public boolean isExist(String transactionalID) {
        return transactionDao.isExist(transactionalID);
    }
}
