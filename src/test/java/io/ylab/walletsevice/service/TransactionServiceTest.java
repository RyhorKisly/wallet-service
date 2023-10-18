package io.ylab.walletsevice.service;

import io.ylab.walletservice.core.dto.TransactionDTO;
import io.ylab.walletservice.core.enums.Operation;
import io.ylab.walletservice.core.enums.UserRole;
import io.ylab.walletservice.dao.AccountDao;
import io.ylab.walletservice.dao.AuditDao;
import io.ylab.walletservice.dao.TransactionDao;
import io.ylab.walletservice.dao.UserDao;
import io.ylab.walletservice.dao.entity.AccountEntity;
import io.ylab.walletservice.dao.entity.TransactionEntity;
import io.ylab.walletservice.dao.entity.UserEntity;
import io.ylab.walletservice.service.TransactionService;
import io.ylab.walletservice.service.factory.TransactionServiceFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

public class TransactionServiceTest {
    private TransactionService transactionService;
    private UserDao userDao;
    private AuditDao auditDao;
    private AccountDao accountDao;
    private TransactionDao transactionDao;

    @BeforeEach
    @DisplayName("Initialize classes for tests")
    public void setUp() {
        transactionService = (TransactionService) TransactionServiceFactory.getInstance();
        userDao = new UserDao();
        auditDao = new AuditDao();
        accountDao = new AccountDao();
        transactionDao = new TransactionDao();
    }

    @Test
    @DisplayName("Test for creating transaction")
    void createTest() {
        UserEntity userEntity = new UserEntity();
        userEntity.setLogin("Have never been created transaction CreateTestAudit");
        userEntity.setPassword("1tset");
        userEntity.setRole(UserRole.USER);
        UserEntity savedUserEntity = userDao.save(userEntity);

        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setUserId(savedUserEntity.getId());
        accountEntity.setBalance(new BigDecimal("0.0"));
        AccountEntity savedAccountEntity = accountDao.save(accountEntity);

        TransactionDTO transactionDTO = new TransactionDTO(
                UUID.randomUUID().toString(),
                Operation.CREDIT,
                new BigDecimal("3.3"),
                savedAccountEntity.getId());
        TransactionEntity transactionEntity = transactionService.create(transactionDTO, savedUserEntity.getId());

        auditDao.deleteByUserId(savedUserEntity.getId());
        transactionDao.delete(transactionEntity.getTransactionId());
        accountDao.delete(savedAccountEntity.getId());
        userDao.delete(savedUserEntity.getId());

        Assertions.assertEquals(transactionDTO.getTransactionId(), transactionEntity.getTransactionId());
        Assertions.assertEquals(transactionDTO.getSumOfTransaction(), transactionEntity.getSumOfTransaction());
        Assertions.assertEquals(transactionDTO.getOperation(), transactionEntity.getOperation());
        Assertions.assertEquals(transactionDTO.getNumberAccount(), transactionEntity.getAccountId());
    }

    @Test
    @DisplayName("Test for getting transaction by id")
    void getById() {
        UserEntity savedUserEntity = userDao.find("admin");
        AccountEntity savedAccountEntity = accountDao.find("admin");


        TransactionDTO transactionDTO = new TransactionDTO(
                UUID.randomUUID().toString(),
                Operation.CREDIT,
                new BigDecimal("3.3"),
                savedAccountEntity.getId());

        TransactionEntity createdEntity = transactionService.create(transactionDTO, savedUserEntity.getId());
        TransactionEntity foundEntity = transactionService.get(createdEntity.getTransactionId());

        transactionDao.delete(createdEntity.getTransactionId());

        Assertions.assertEquals(createdEntity, foundEntity);
    }

    @Test
    @DisplayName("Test for getting transactions by account entity")
    void getAllByAccountEntity() {
        UserEntity userEntity = new UserEntity();
        userEntity.setLogin("Have never been created transaction CreateTestAudit");
        userEntity.setPassword("1tset");
        userEntity.setRole(UserRole.USER);
        UserEntity savedUserEntity = userDao.save(userEntity);

        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setUserId(savedUserEntity.getId());
        accountEntity.setBalance(new BigDecimal("0.0"));
        AccountEntity savedAccountEntity = accountDao.save(accountEntity);

        TransactionDTO transactionDTO1 = new TransactionDTO(
                UUID.randomUUID().toString(),
                Operation.CREDIT,
                new BigDecimal("3.3"),
                savedAccountEntity.getId());
        TransactionDTO transactionDTO2 = new TransactionDTO(
                UUID.randomUUID().toString(),
                Operation.CREDIT,
                new BigDecimal("3.3"),
                savedAccountEntity.getId());

        TransactionEntity createdEntity1 = transactionService.create(transactionDTO1, savedUserEntity.getId());
        TransactionEntity createdEntity2 = transactionService.create(transactionDTO2, savedUserEntity.getId());

        Set<TransactionEntity> transactions = new TreeSet<>(Comparator.comparing(TransactionEntity::getDtCreate));
        transactions.add(createdEntity1);
        transactions.add(createdEntity2);

        Set<TransactionEntity> savedTransactions = transactionService.get(savedAccountEntity);

        auditDao.deleteByUserId(savedUserEntity.getId());
        transactionDao.delete(createdEntity1.getTransactionId());
        transactionDao.delete(createdEntity2.getTransactionId());
        accountDao.delete(savedAccountEntity.getId());
        userDao.delete(savedUserEntity.getId());

        Assertions.assertEquals(transactions, savedTransactions);
    }

    @Test
    @DisplayName("Test for finding transaction by id with return value as boolean")
    void isExistTest() {
        UserEntity userEntity = userDao.find("admin");

        AccountEntity savedAccountEntity = accountDao.find(userEntity.getLogin());

        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setTransactionId(UUID.randomUUID().toString());
        transactionEntity.setSumOfTransaction(new BigDecimal("3.3"));
        transactionEntity.setOperation(Operation.CREDIT);
        transactionEntity.setDtCreate(LocalDateTime.now());
        transactionEntity.setAccountId(savedAccountEntity.getId());
        TransactionEntity savedTransactionEntity = transactionDao.save(transactionEntity);

        Assertions.assertTrue(transactionService.isExist(savedTransactionEntity.getTransactionId()));

        transactionDao.delete(savedTransactionEntity.getTransactionId());
    }


}
