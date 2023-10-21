package io.ylab.walletsevice.service;

import io.ylab.walletservice.core.dto.TransactionDTO;
import io.ylab.walletservice.core.enums.Operation;
import io.ylab.walletservice.core.enums.UserRole;
import io.ylab.walletservice.dao.AccountDao;
import io.ylab.walletservice.dao.AuditDao;
import io.ylab.walletservice.dao.TransactionDao;
import io.ylab.walletservice.dao.UserDao;
import io.ylab.walletservice.dao.api.IAccountDao;
import io.ylab.walletservice.dao.api.ITransactionDao;
import io.ylab.walletservice.dao.api.IUserDao;
import io.ylab.walletservice.dao.entity.AccountEntity;
import io.ylab.walletservice.dao.entity.TransactionEntity;
import io.ylab.walletservice.dao.entity.UserEntity;
import io.ylab.walletservice.service.AuditService;
import io.ylab.walletservice.service.TransactionService;
import io.ylab.walletservice.service.api.ITransactionService;
import io.ylab.walletsevice.dao.ds.factory.ConnectionWrapperFactoryTest;
import io.ylab.walletsevice.dao.utils.api.ILiquibaseManagerTest;
import io.ylab.walletsevice.dao.utils.factory.LiquibaseManagerTestFactory;
import io.ylab.walletsevice.testcontainers.config.ContainersEnvironment;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Class for testing methods of the class TransactionService")
public class TransactionServiceTest extends ContainersEnvironment {

    /**
     * Define a field with a type {@link IUserDao} for further use in the test
     */
    private IUserDao userDao;

    /**
     * Define a field with a type {@link IAccountDao} for further use in the test
     */
    private IAccountDao accountDao;

    /**
     * Define a field with a type {@link ITransactionDao} for further use in the test
     */
    private ITransactionDao transactionDao;

    /**
     * Define a field with a type {@link ITransactionService} for further use in the test
     */
    private ITransactionService transactionService;

    /**
     * Define a field with a type {@link ILiquibaseManagerTest} for further use in the test
     */
    private ILiquibaseManagerTest liquibaseManagerTest;

    @BeforeAll
    @DisplayName("Initialize classes for tests and call method for creating schema and tables in test db")
    public void setUp() {
        userDao = new UserDao(ConnectionWrapperFactoryTest.getInstance());
        accountDao = new AccountDao(ConnectionWrapperFactoryTest.getInstance());
        transactionDao = new TransactionDao(ConnectionWrapperFactoryTest.getInstance());
        AuditDao auditDao = new AuditDao(ConnectionWrapperFactoryTest.getInstance());
        AuditService auditService = new AuditService(auditDao);
        transactionService = new TransactionService(transactionDao, auditService);

        liquibaseManagerTest = LiquibaseManagerTestFactory.getInstance();
        liquibaseManagerTest.migrateDbCreate();
    }

    @AfterEach
    @DisplayName("Migrates data to drop data in table")
    public void drop() {
        this.liquibaseManagerTest.migrateDbDrop();
    }

    @Test
    @DisplayName("Positive test for creating transaction")
    void createTest() {
        UserEntity userEntity = new UserEntity();
        userEntity.setLogin("Have never been created transaction CreateTestAudit");
        userEntity.setPassword("test");
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

        Assertions.assertEquals(transactionDTO.getTransactionId(), transactionEntity.getTransactionId());
        Assertions.assertEquals(transactionDTO.getSumOfTransaction(), transactionEntity.getSumOfTransaction());
        Assertions.assertEquals(transactionDTO.getOperation(), transactionEntity.getOperation());
        Assertions.assertEquals(transactionDTO.getNumberAccount(), transactionEntity.getAccountId());
    }

    @Test
    @DisplayName("Positive test for getting transaction by id")
    void getById() {
        UserEntity userEntity = new UserEntity();
        userEntity.setLogin("Have never been created transaction CreateTestAudit");
        userEntity.setPassword("test1");
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

        TransactionEntity createdEntity = transactionService.create(transactionDTO, savedUserEntity.getId());
        TransactionEntity foundEntity = transactionService.get(createdEntity.getTransactionId());

        Assertions.assertEquals(createdEntity, foundEntity);
    }

    @Test
    @DisplayName("Positive test for getting transactions by account entity")
    void getAllByAccountEntity() {
        UserEntity userEntity = new UserEntity();
        userEntity.setLogin("Have never been created transaction CreateTestAudit");
        userEntity.setPassword("test1");
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

        Assertions.assertEquals(transactions, savedTransactions);
    }

    @Test
    @DisplayName("Positive test for finding transaction by id with return value as boolean")
    void isExistTest() {
        UserEntity userEntity = new UserEntity();
        userEntity.setLogin("Have never been created transaction CreateTestAudit");
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

        Assertions.assertTrue(transactionService.isExist(savedTransactionEntity.getTransactionId()));
    }


}
