package io.ylab.walletservice.dao;

import io.ylab.walletservice.core.enums.Operation;
import io.ylab.walletservice.core.enums.UserRole;
import io.ylab.walletservice.dao.api.IAccountDao;
import io.ylab.walletservice.dao.api.ITransactionDao;
import io.ylab.walletservice.dao.api.IUserDao;
import io.ylab.walletservice.utils.IntegrationTestBase;
import io.ylab.walletservice.dao.entity.AccountEntity;
import io.ylab.walletservice.dao.entity.TransactionEntity;
import io.ylab.walletservice.dao.entity.UserEntity;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

@DisplayName("Class for testing methods of the class TransactionDao")
public class TransactionDaoTest extends IntegrationTestBase {

    /**
     * Define a field with a type {@link ITransactionDao} for further use in the test
     */
    @Autowired
    private ITransactionDao transactionDao;

    /**
     * Define a field with a type {@link IUserDao} for further use in the test
     */
    @Autowired
    private IUserDao userDao;

    /**
     * Define a field with a type {@link IAccountDao} for further use in the test
     */
    @Autowired
    private IAccountDao accountDao;

    @Test
    @DisplayName("Positive test for saving transaction")
    void saveTest() {
        UserEntity userEntity = new UserEntity();
        userEntity.setLogin("Have never been created account test1");
        userEntity.setPassword("test1");
        userEntity.setRole(UserRole.USER);
        UserEntity savedUserEntity = userDao.save(userEntity);

        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setUserId(savedUserEntity.getId());
        accountEntity.setBalance(new BigDecimal("0.0"));
        AccountEntity savedAccountEntity = accountDao.save(accountEntity);

        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setTransactionId(UUID.randomUUID().toString());
        transactionEntity.setSumOfTransaction(new BigDecimal("3.3"));
        transactionEntity.setOperation(Operation.CREDIT);
        transactionEntity.setDtCreate(LocalDateTime.now());
        transactionEntity.setAccountId(savedAccountEntity.getId());
        TransactionEntity savedTransactionEntity = transactionDao.save(transactionEntity);

        assertEquals(transactionEntity, savedTransactionEntity);
    }

    @Test
    @DisplayName("Positive test for finding transaction by id")
    void findByIdTest() {
        UserEntity userEntity = new UserEntity();
        userEntity.setLogin("Have never been created account test1");
        userEntity.setPassword("test1");
        userEntity.setRole(UserRole.USER);
        UserEntity savedUserEntity = userDao.save(userEntity);

        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setUserId(savedUserEntity.getId());
        accountEntity.setBalance(new BigDecimal("0.0"));
        AccountEntity savedAccountEntity = accountDao.save(accountEntity);

        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setTransactionId(UUID.randomUUID().toString());
        transactionEntity.setSumOfTransaction(new BigDecimal("3.3"));
        transactionEntity.setOperation(Operation.CREDIT);
        transactionEntity.setDtCreate(LocalDateTime.now());
        transactionEntity.setAccountId(savedAccountEntity.getId());
        TransactionEntity savedTransactionEntity = transactionDao.save(transactionEntity);
        Optional<TransactionEntity> foundTransactionEntity = transactionDao.find(savedTransactionEntity.getTransactionId());

        Assertions.assertEquals(savedTransactionEntity, foundTransactionEntity.orElseThrow(RuntimeException::new));
    }

    @Test
    @DisplayName("Negative test for checking null when id does not exist")
    void findByIdNonExistentTransactionTest() {
        Optional<TransactionEntity> foundTransactionEntity = transactionDao.find("test");
        assertThrowsExactly(RuntimeException.class, () -> foundTransactionEntity.orElseThrow(RuntimeException::new));
    }

    @Test
    @DisplayName("Test for checking transaction by id exist or not")
    void isExistTest() {
        UserEntity userEntity = new UserEntity();
        userEntity.setLogin("Have never been created account test1");
        userEntity.setPassword("test");
        userEntity.setRole(UserRole.USER);
        UserEntity savedUserEntity = userDao.save(userEntity);

        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setUserId(savedUserEntity.getId());
        accountEntity.setBalance(new BigDecimal("0.0"));
        AccountEntity savedAccountEntity = accountDao.save(accountEntity);

        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setTransactionId(UUID.randomUUID().toString());
        transactionEntity.setSumOfTransaction(new BigDecimal("3.3"));
        transactionEntity.setOperation(Operation.CREDIT);
        transactionEntity.setDtCreate(LocalDateTime.now());
        transactionEntity.setAccountId(savedAccountEntity.getId());
        TransactionEntity savedTransactionEntity = transactionDao.save(transactionEntity);

        Assertions.assertTrue(transactionDao.isExist(savedTransactionEntity.getTransactionId()));
    }

}
