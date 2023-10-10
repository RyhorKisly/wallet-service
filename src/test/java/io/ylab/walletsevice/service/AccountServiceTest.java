package io.ylab.walletsevice.service;

import io.ylab.walletservice.core.dto.AccountDTO;
import io.ylab.walletservice.core.dto.TransactionDTO;
import io.ylab.walletservice.core.enums.Operation;
import io.ylab.walletservice.dao.entity.AccountEntity;
import io.ylab.walletservice.dao.memory.AccountDao;
import io.ylab.walletservice.dao.memory.factory.AccountDaoFactory;
import io.ylab.walletservice.service.AccountService;
import io.ylab.walletservice.service.factory.AccountServiceFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

class AccountServiceTest {

    private AccountService accountService;

    private AccountDao accountDao;

    @BeforeEach
    public void setUp() {
        accountDao = (AccountDao) AccountDaoFactory.getInstance();
        accountService = (AccountService) AccountServiceFactory.getInstance();
    }
    @Test
    void createTest() {
        UUID uuid = UUID.randomUUID();
        AccountDTO accountDTO = new AccountDTO(uuid, new BigDecimal("0.0"), "test");
        AccountEntity entity = new AccountEntity(accountDTO.getNumberAccount(), new BigDecimal("0.0"), accountDTO.getLogin());

        AccountEntity entityCreated = accountService.create(accountDTO);

        Assertions.assertEquals(entity, entityCreated);
    }

    @Test
    void getByNumberAccountTest() {
        UUID uuid = UUID.randomUUID();
        AccountEntity entity = new AccountEntity(uuid, new BigDecimal("0.0"), "test1");
        accountDao.save(entity);

        AccountEntity entityCreated = accountService.get(uuid);

        Assertions.assertEquals(entity, entityCreated);
    }

    @Test
    void getByNumberAccountAndLoginTest() {
        UUID uuid = UUID.randomUUID();
        AccountEntity entity = new AccountEntity(uuid, new BigDecimal("0.0"), "test2");
        accountDao.save(entity);

        AccountEntity entityCreated = accountService.get(uuid.toString(), entity.getLogin());

        Assertions.assertEquals(entity, entityCreated);
    }

    @Test void getByLoginTest() {
        UUID uuid = UUID.randomUUID();
        AccountEntity entity = new AccountEntity(uuid, new BigDecimal("0.0"), "test33");
        accountDao.save(entity);

        AccountEntity entityCreated = accountService.get(entity.getLogin());

        Assertions.assertEquals(entity, entityCreated);
    }

    @Test
    void updateBalanceIfCreditTest() {
        UUID uuid = UUID.randomUUID();
        TransactionDTO transactionDTO = new TransactionDTO("qwerty", Operation.CREDIT, new BigDecimal("4.3"), uuid);

        AccountEntity entity = new AccountEntity(uuid, new BigDecimal("0.0"), "test4");
        accountDao.save(entity);

        AccountEntity entityCreated = accountService.updateBalance(uuid, transactionDTO);
        entity.setBalance(entity.getBalance().add(new BigDecimal("4.3")));

        Assertions.assertEquals(entity, entityCreated);
    }

    @Test
    void updateBalanceIfDebitTest() {
        UUID uuid = UUID.randomUUID();
        TransactionDTO transactionDTO = new TransactionDTO("qwer", Operation.DEBIT, new BigDecimal("4.3"), uuid);

        AccountEntity entity = new AccountEntity(uuid, new BigDecimal("10.0"), "test5");
        accountDao.save(entity);

        AccountEntity entityCreated = accountService.updateBalance(uuid, transactionDTO);
        entity.setBalance(entity.getBalance().subtract(new BigDecimal("4.3")));

        Assertions.assertEquals(entity, entityCreated);
    }

    @Test
    void updateBalanceIfDebitMoreThanBalanceTest() {
        UUID uuid = UUID.randomUUID();
        TransactionDTO transactionDTO = new TransactionDTO("qwert", Operation.DEBIT, new BigDecimal("4.3"), uuid);

        AccountEntity entity = new AccountEntity(uuid, new BigDecimal("3.0"), "test");
        accountDao.save(entity);

        AccountEntity entityCreated = accountService.updateBalance(uuid, transactionDTO);
        entity.setBalance(entity.getBalance().subtract(new BigDecimal("4.3")));

        Assertions.assertNull(entityCreated);
    }

}
