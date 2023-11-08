package io.ylab.walletservice.service;

import io.ylab.walletservice.core.dto.TransactionDTO;
import io.ylab.walletservice.core.enums.Operation;
import io.ylab.walletservice.core.enums.UserRole;
import io.ylab.walletservice.dao.api.ITransactionDao;
import io.ylab.walletservice.dao.entity.AccountEntity;
import io.ylab.walletservice.dao.entity.TransactionEntity;
import io.ylab.walletservice.dao.entity.UserEntity;
import io.ylab.walletservice.service.api.IAccountService;
import io.ylab.walletservice.service.api.ITransactionService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@DisplayName("Class for testing methods of the class TransactionService")
public class TransactionServiceTest {

    /**
     * Define a field with a type {@link IAccountService} for further use in the test
     */
    @Mock
    private IAccountService accountService;

    /**
     * Define a field with a type {@link ITransactionDao} for further use in the test
     */
    @Mock
    private ITransactionDao transactionDao;

    /**
     * Define a field with a type {@link ITransactionService} for further use in the test
     */
    private ITransactionService transactionService;

    @BeforeEach
    @DisplayName("Initialize transaction with mocked transactionDao")
    public void setUp() {
        transactionService = new TransactionService(transactionDao, accountService);
    }

    @Test
    @DisplayName("Prositive test for creating transaction")
    void createTest() {
        AccountEntity accountEntity = new AccountEntity(
                1L, new BigDecimal("0.0"), 1L);

        TransactionDTO transactionDTO = new TransactionDTO(
                UUID.randomUUID().toString(), Operation.CREDIT,
                new BigDecimal("3.3"), accountEntity.getId());

        accountEntity.setBalance(transactionDTO.getSumOfTransaction());

        TransactionEntity transactionEntity = new TransactionEntity();
                transactionEntity.setTransactionId(transactionDTO.getTransactionId());
        transactionEntity.setSumOfTransaction(transactionDTO.getSumOfTransaction());
        transactionEntity.setOperation(transactionDTO.getOperation());
        transactionEntity.setAccountId(transactionDTO.getAccountId());

        when(transactionDao.save(transactionEntity)).thenReturn(transactionEntity);

        when(accountService.updateBalance(transactionDTO.getAccountId(), transactionDTO))
                .thenReturn(accountEntity);

        TransactionEntity createdEntity = transactionService.create(transactionDTO, accountEntity.getId());

        Assertions.assertEquals(transactionEntity.getTransactionId(), createdEntity.getTransactionId());
        Assertions.assertEquals(transactionEntity.getSumOfTransaction(), createdEntity.getSumOfTransaction());
        Assertions.assertEquals(transactionEntity.getAccountId(), createdEntity.getAccountId());
        Assertions.assertEquals(transactionEntity.getOperation(), createdEntity.getOperation());
    }

    @Test
    @DisplayName("Positive test for getting transaction by id")
    void getById() {
        String transactionId = UUID.randomUUID().toString();
        TransactionEntity transactionEntity = new TransactionEntity(
                UUID.randomUUID().toString(),
                Operation.CREDIT,
                new BigDecimal("3.3"),
                1L,
                LocalDateTime.now());

        when(transactionDao.find(transactionId)).thenReturn(Optional.of(transactionEntity));

        TransactionEntity foundEntity = transactionService.get(transactionId);

        Assertions.assertEquals(transactionEntity.getTransactionId(), foundEntity.getTransactionId());
        Assertions.assertEquals(transactionEntity.getSumOfTransaction(), foundEntity.getSumOfTransaction());
        Assertions.assertEquals(transactionEntity.getAccountId(), foundEntity.getAccountId());
        Assertions.assertEquals(transactionEntity.getOperation(), foundEntity.getOperation());
    }

    @Test
    @DisplayName("Negative test for getting transaction by id if entity not exist")
    void getByIdIfNotExist() {
        String transactionId = UUID.randomUUID().toString();
        when(transactionDao.find(transactionId)).thenReturn(Optional.empty());

        Assertions.assertThrows(RuntimeException.class, () -> transactionService.get(transactionId));
    }

    @Test
    @DisplayName("Positive test for getting transactions by account entity")
    void getAllByAccountEntity() {
        UserEntity userEntity = new UserEntity(
                1L,
                "Have never been created transaction CreateTestAudit",
                "test1",
                UserRole.USER
        );

        AccountEntity accountEntity = new AccountEntity(
                1L,
                new BigDecimal("0.0"),
                userEntity.getId()
        );

        TransactionEntity transactionEntity1 = new TransactionEntity(
                UUID.randomUUID().toString(),
                Operation.CREDIT,
                new BigDecimal("3.3"),
                accountEntity.getId(),
                LocalDateTime.now());
        TransactionEntity transactionEntity2 = new TransactionEntity(
                UUID.randomUUID().toString(),
                Operation.CREDIT,
                new BigDecimal("3.3"),
                accountEntity.getId(),
                LocalDateTime.now());
        Set<TransactionEntity> transactions = Set.of(transactionEntity1, transactionEntity2);

        when(accountService.getByUser(userEntity.getId())).thenReturn(accountEntity);
        when(transactionDao.findAllByNumberAccountAscByDTCreate(accountEntity.getId())).thenReturn(transactions);

        Set<TransactionEntity> savedTransactions = transactionService.get(accountEntity.getId(), accountEntity.getUserId());

        Assertions.assertEquals(transactions, savedTransactions);
    }

    @Test
    @DisplayName("Positive test for finding transaction by id with return value as boolean")
    void isExistTest() {
        String uuid = UUID.randomUUID().toString();
        when(transactionDao.isExist(uuid)).thenReturn(true);
        Assertions.assertTrue(transactionService.isExist(uuid));
    }

    @Test
    @DisplayName("Negative test for finding transaction by id with return value as boolean")
    void isNotExistTest() {
        String uuid = UUID.randomUUID().toString();
        when(transactionDao.isExist(uuid)).thenReturn(false);
        Assertions.assertFalse(transactionService.isExist(uuid));
    }


}
