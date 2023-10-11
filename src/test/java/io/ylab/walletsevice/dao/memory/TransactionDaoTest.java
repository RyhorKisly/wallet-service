package io.ylab.walletsevice.dao.memory;

import io.ylab.walletservice.core.enums.Operation;
import io.ylab.walletservice.dao.entity.TransactionEntity;
import io.ylab.walletservice.dao.memory.TransactionDao;
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

public class TransactionDaoTest {

    private TransactionDao transactionDao;

    @BeforeEach
    @DisplayName("Initialize class for tests")
    public void setUp() {
        transactionDao = new TransactionDao();
    }
    @Test
    @DisplayName("test for finding transaction by id")
    void findByTransactionIdTest() {
        String transactionID = "qwe";
        TransactionEntity entity = new TransactionEntity(
                transactionID, Operation.CREDIT,
                new BigDecimal("2.3"), UUID.randomUUID(), LocalDateTime.now());

        transactionDao.save(entity);

        Assertions.assertEquals(entity, transactionDao.find(transactionID));
    }

    @Test
    @DisplayName("Test for finding transaction by id with return type boolean")
    void isExistTest() {
        String transactionID = "qwer";
        TransactionEntity entity = new TransactionEntity(
                transactionID, Operation.CREDIT,
                new BigDecimal("2.3"), UUID.randomUUID(), LocalDateTime.now());

        transactionDao.save(entity);

        Assertions.assertTrue(transactionDao.isExist(transactionID));
    }

    @Test
    @DisplayName("Test for saving transaction")
    void saveTest() {
        String transactionID = "qwert";
        TransactionEntity entity = new TransactionEntity(
                transactionID, Operation.CREDIT,
                new BigDecimal("2.3"), UUID.randomUUID(), LocalDateTime.now());

        Assertions.assertEquals(entity, transactionDao.save(entity));
    }

    @Test
    @DisplayName("Test for finding history of transaction by id and asc by dtCreate")
    void findAllByNumberAccountAscByDTCreateTest() {
        String transactionID = "qwerty";
        String transactionID2 = "qwew";
        UUID uuid = UUID.randomUUID();
        TransactionEntity entity1 = new TransactionEntity(
                transactionID, Operation.CREDIT,
                new BigDecimal("2.3"), uuid, LocalDateTime.now());
        TransactionEntity entity2 = new TransactionEntity(
                transactionID2, Operation.CREDIT,
                new BigDecimal("2.5"), uuid, LocalDateTime.now());

        Set<TransactionEntity> historyOfTransactions = new TreeSet<>(Comparator.comparing(TransactionEntity::getDtCreate));
        historyOfTransactions.add(entity1);
        historyOfTransactions.add(entity2);

        transactionDao.save(entity1);
        transactionDao.save(entity2);

        Assertions.assertEquals(historyOfTransactions, transactionDao.findAllByNumberAccountAscByDTCreate(uuid));
    }
}
