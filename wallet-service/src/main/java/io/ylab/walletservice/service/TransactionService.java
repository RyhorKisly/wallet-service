package io.ylab.walletservice.service;

import io.ylab.starteraspectlogger.aop.annotations.Loggable;
import io.ylab.walletservice.core.dto.TransactionDTO;
import io.ylab.walletservice.core.exceptions.NotUniqueException;
import io.ylab.walletservice.core.exceptions.TransactionFailedException;
import io.ylab.walletservice.core.exceptions.WrongAccountIdException;
import io.ylab.walletservice.dao.api.ITransactionDao;
import io.ylab.walletservice.dao.entity.AccountEntity;
import io.ylab.walletservice.dao.entity.TransactionEntity;
import io.ylab.walletservice.service.api.IAccountService;
import io.ylab.walletservice.service.api.IAuditService;
import io.ylab.walletservice.service.api.ITransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * Class for generic operations on a service for an Account.
 * Interact with {@link ITransactionDao} and {@link IAuditService}
 * This an implementation of {@link ITransactionService}
 */
@Service
@RequiredArgsConstructor
@Loggable
public class TransactionService implements ITransactionService {

    /**
     * Message when can't update account because of wrong sum of transaction.
     */
    private static final String TRANSACTION_FAILED = "Transaction failed, change sum of transaction";

    /**
     * Message when try save user with existed id of transaction
     */
    private static final String TRANSACTION_ID_EXIST = "Such transaction id exist";

    /**
     * Name of constraint
     */
    private static final String TRANSACTION_ID_PKEY= "Transaction_pkey";

    /**
     * Message if account does not belong to user
     */
    private static final String WRONG_NUMBER_ACCOUNT = "This account number does not belong to you. Enter different number.";

    /**
     * define a field with a type {@link ITransactionDao} for further aggregation
     */
    private final ITransactionDao transactionDao;

    /**
     * define a field with a type {@link IAccountService} for further aggregation
     */
    private final IAccountService accountService;

    /**
     * Create transaction. Update account balance.
     * Save action in audit.
     * @param dto used for creating transaction
     * @param userId used for updating account balance
     * @return created transactionEntity
     * @throws TransactionFailedException if transaction or account updating do not work
     */
    @Override
    public TransactionEntity create(TransactionDTO dto, Long userId) {
        accountService.getByUser(userId);

        TransactionEntity entity = saveOrThrow(convertToEntity(dto));
        entity.setDtCreate(LocalDateTime.now());

        if (accountService.updateBalance(dto.getAccountId(), dto) == null) {
            transactionDao.delete(entity.getTransactionId());
            throw new TransactionFailedException(TRANSACTION_FAILED);
        }

        return entity;
    }

    @Override
    public TransactionEntity get(String transactionID) {
        return transactionDao.find(transactionID)
                .orElseThrow(RuntimeException::new);
    }

    @Override
    public Set<TransactionEntity> get(Long accountId, Long UserId) {

        AccountEntity accountEntity = accountService.getByUser(UserId);
        if(!accountId.equals(accountEntity.getId())) {
            throw new WrongAccountIdException(WRONG_NUMBER_ACCOUNT);
        }

        return transactionDao.findAllByNumberAccountAscByDTCreate(accountId);
    }

    @Override
    public boolean isExist(String transactionalID) {
        return transactionDao.isExist(transactionalID);
    }

    /**
     * Convert TransactionDTO to Entity.
     * Don't use mapstruct because farther time will be created
     * by hibernate and here I can see conversion more explicitly.
     * @param dto dor converting to entity
     * @return {@link TransactionEntity}
     */
    private TransactionEntity convertToEntity(TransactionDTO dto) {
        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setTransactionId(dto.getTransactionId());
        transactionEntity.setSumOfTransaction(dto.getSumOfTransaction());
        transactionEntity.setOperation(dto.getOperation());
        transactionEntity.setAccountId(dto.getAccountId());
        return transactionEntity;
    }

    /**
     * Calls save method of transactionDao.
     * @param entity for saving.
     * @return transactionEntity if not exist return account in db.
     * @throws NotUniqueException if transaction exist in db.
     * @throws RuntimeException other problem with db.
     */
    private TransactionEntity saveOrThrow(TransactionEntity entity) {
        try{
            entity = transactionDao.save(entity);
        } catch (RuntimeException ex) {
            if(ex.getMessage().contains(TRANSACTION_ID_PKEY)) {
                throw new NotUniqueException(TRANSACTION_ID_EXIST, ex);
            }
            throw new RuntimeException(ex);
        }
        return entity;
    }
}
