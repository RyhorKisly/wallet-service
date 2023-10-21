package io.ylab.walletservice.service;

import io.ylab.walletservice.core.enums.Operation;
import io.ylab.walletservice.core.dto.AuditDTO;
import io.ylab.walletservice.core.dto.TransactionDTO;
import io.ylab.walletservice.dao.api.ITransactionDao;
import io.ylab.walletservice.dao.entity.AccountEntity;
import io.ylab.walletservice.dao.entity.TransactionEntity;
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
public class TransactionService implements ITransactionService {

    /**
     * Save text in audit when type of transaction was created.
     */
    private static final String TRANSACTION_CREATED = "%s transaction was created";

    /**
     * Save text in audit when user request history of transaction.
     */
    private static final String REQUEST_HISTORY = "User requested transaction history";

    /**
     * define a field with a type {@link ITransactionDao} for further aggregation
     */
    private final ITransactionDao transactionDao;

    /**
     * define a field with a type {@link IAuditService} for further aggregation
     */
    private final IAuditService auditService;

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
        TransactionEntity entity = new TransactionEntity(
                dto.getTransactionId(),
                dto.getOperation(),
                dto.getSumOfTransaction(),
                dto.getNumberAccount(),
                LocalDateTime.now()
        );
        for (Operation value : Operation.values()) {
            if(dto.getOperation().equals(value)) {
                AuditDTO auditDTO = new AuditDTO(
                userId, String.format(TRANSACTION_CREATED, dto.getOperation()));
                auditService.create(auditDTO);
            }
        }
        return transactionDao.save(entity);
    }

    /**
     * get entity by ID
     * @param transactionID get entity by ID
     * @return entity for farther interaction with app
     */
    @Override
    public TransactionEntity get(String transactionID) {
        return transactionDao.find(transactionID);
    }

    /**
     * get set of entities by number of an account
     * @param entity get entity by number of an account
     * @return set of entities for farther interaction
     */
    @Override
    public Set<TransactionEntity> get(AccountEntity entity) {
        Set<TransactionEntity> transactions =
                transactionDao.findAllByNumberAccountAscByDTCreate(entity.getId());

        AuditDTO auditDTO = new AuditDTO(
                entity.getUserId(), REQUEST_HISTORY);
        auditService.create(auditDTO);

        return transactions;
    }

    /**
     * Return true if transaction with ID exists
     * @param transactionalID find entity by ID
     * @return true if transaction with ID exists
     */
    @Override
    public boolean isExist(String transactionalID) {
        return transactionDao.isExist(transactionalID);
    }
}
