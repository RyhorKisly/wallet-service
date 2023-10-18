package io.ylab.walletsevice.dao;

import io.ylab.walletservice.core.enums.Operation;
import io.ylab.walletservice.core.enums.UserRole;
import io.ylab.walletservice.dao.AccountDao;
import io.ylab.walletservice.dao.TransactionDao;
import io.ylab.walletservice.dao.UserDao;
import io.ylab.walletservice.dao.entity.AccountEntity;
import io.ylab.walletservice.dao.entity.TransactionEntity;
import io.ylab.walletservice.dao.entity.UserEntity;
import io.ylab.walletsevice.testcontainers.config.ContainersEnvironment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransactionDaoTest extends ContainersEnvironment {

    private TransactionDao transactionDao;
    private UserDao userDao;
    private AccountDao accountDao;
    @BeforeEach
    @DisplayName("Initialize class for tests")
    public void setUp() {
        transactionDao = new TransactionDao();
        userDao = new UserDao();
        accountDao = new AccountDao();
    }

    @Test
    @DisplayName("Positive test for saving transaction")
    void saveTest() {
        UserEntity userEntity = new UserEntity();
        userEntity.setLogin("Have never been created account test1");
        userEntity.setPassword("1tset");
        userEntity.setRole(UserRole.USER);
        UserEntity savedUserEntity = userDao.save(userEntity);

        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setUserId(userEntity.getId());
        accountEntity.setBalance(new BigDecimal("0.0"));
        AccountEntity savedAccountEntity = accountDao.save(accountEntity);

        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setTransactionId(UUID.randomUUID().toString());
        transactionEntity.setSumOfTransaction(new BigDecimal("3.3"));
        transactionEntity.setOperation(Operation.CREDIT);
        transactionEntity.setDtCreate(LocalDateTime.now());
        transactionEntity.setAccountId(savedAccountEntity.getId());
        TransactionEntity savedTransactionEntity = transactionDao.save(transactionEntity);

        transactionDao.delete(savedTransactionEntity.getTransactionId());
        accountDao.delete(savedAccountEntity.getId());
        userDao.delete(savedUserEntity.getId());

        assertEquals(transactionEntity, savedTransactionEntity);
    }

    @Test
    @DisplayName("Test for finding transaction by id")
    void findByIdTest() {
        UserEntity userEntity = new UserEntity();
        userEntity.setLogin("Have never been created account test1");
        userEntity.setPassword("1tset");
        userEntity.setRole(UserRole.USER);
        UserEntity savedUserEntity = userDao.save(userEntity);

        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setUserId(userEntity.getId());
        accountEntity.setBalance(new BigDecimal("0.0"));
        AccountEntity savedAccountEntity = accountDao.save(accountEntity);

        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setTransactionId(UUID.randomUUID().toString());
        transactionEntity.setSumOfTransaction(new BigDecimal("3.3"));
        transactionEntity.setOperation(Operation.CREDIT);
        transactionEntity.setDtCreate(LocalDateTime.now());
        transactionEntity.setAccountId(savedAccountEntity.getId());
        TransactionEntity savedTransactionEntity = transactionDao.save(transactionEntity);
        TransactionEntity foundTransactionEntity = transactionDao.find(savedTransactionEntity.getTransactionId());

        transactionDao.delete(savedTransactionEntity.getTransactionId());
        accountDao.delete(savedAccountEntity.getId());
        userDao.delete(savedUserEntity.getId());

        Assertions.assertEquals(savedTransactionEntity, foundTransactionEntity);
    }

    @Test
    @DisplayName("Negative test for checking null when id does not exist")
    void findByIdNonExistentTransactionTest() {
        Assertions.assertNull(transactionDao.find("aslnasipuhg238rfjrrtest)"));
    }

    @Test
    @DisplayName("Test for checking transaction by id exist or not")
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

        Assertions.assertTrue(transactionDao.isExist(savedTransactionEntity.getTransactionId()));

        transactionDao.delete(savedTransactionEntity.getTransactionId());
    }

}
