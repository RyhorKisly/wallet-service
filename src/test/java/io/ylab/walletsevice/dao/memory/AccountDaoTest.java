package io.ylab.walletsevice.dao.memory;

import io.ylab.walletservice.dao.entity.AccountEntity;
import io.ylab.walletservice.dao.memory.AccountDao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

public class AccountDaoTest {

    private AccountDao accountDao;

    @BeforeEach
    @DisplayName("Initialize class for tests")
    public void setUp() {
        accountDao = new AccountDao();
    }
    @Test
    @DisplayName("Test for finding account by number of account")
    void findByNumberAccount() {
        UUID uuid = UUID.randomUUID();
        AccountEntity entity = new AccountEntity(uuid, new BigDecimal("0.0"), "test");

        accountDao.save(entity);
        AccountEntity accountEntity = accountDao.find(uuid);

        Assertions.assertEquals(entity, accountEntity);
    }
    @Test
    @DisplayName("Test for finding account by number of account and user login")
    void findByNumberAccountAndLogin() {
        UUID uuid = UUID.randomUUID();
        AccountEntity entity = new AccountEntity(uuid, new BigDecimal("0.0"), "test2");

        accountDao.save(entity);
        AccountEntity accountEntity = accountDao.find(uuid, entity.getLogin());

        Assertions.assertEquals(entity, accountEntity);
    }

    @Test
    @DisplayName("Test for finding account by user login")

    void findByLogin() {
        UUID uuid = UUID.randomUUID();
        AccountEntity entity = new AccountEntity(uuid, new BigDecimal("0.0"), "test3");

        accountDao.save(entity);
        AccountEntity accountEntity = accountDao.find(entity.getLogin());

        Assertions.assertEquals(entity, accountEntity);
    }

    @Test
    @DisplayName("Test for checking whether correctly saved account into storage")
    void saveTest() {
        UUID uuid = UUID.randomUUID();
        AccountEntity entity = new AccountEntity(uuid, new BigDecimal("0.0"), "test4");

        AccountEntity accountEntity = accountDao.save(entity);

        Assertions.assertEquals(entity, accountEntity);
    }

    @Test
    @DisplayName("Test for checking correctly or not updated balance on account")
    void updateBalanceTest() {
        UUID uuid = UUID.randomUUID();
        AccountEntity entity = new AccountEntity(uuid, new BigDecimal("0.0"), "test5");
        accountDao.save(entity);
        entity.setBalance(entity.getBalance().subtract(new BigDecimal("17.0")));

        AccountEntity accountEntity = accountDao.updateBalance(entity);

        Assertions.assertEquals(entity, accountEntity);
    }
}
