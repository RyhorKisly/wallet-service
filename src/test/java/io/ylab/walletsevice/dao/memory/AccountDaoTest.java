package io.ylab.walletsevice.dao.memory;

import io.ylab.walletservice.dao.entity.AccountEntity;
import io.ylab.walletservice.dao.memory.AccountDao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

public class AccountDaoTest {

    @Test
    void findByNumberAccount() {
        AccountDao accountDao = new AccountDao();
        UUID uuid = UUID.randomUUID();
        AccountEntity entity = new AccountEntity(uuid, new BigDecimal("0.0"), "test");

        accountDao.save(entity);
        AccountEntity accountEntity = accountDao.find(uuid);

        Assertions.assertEquals(entity, accountEntity);
    }
    @Test
    void findByNumberAccountAndLogin() {
        AccountDao accountDao = new AccountDao();
        UUID uuid = UUID.randomUUID();
        AccountEntity entity = new AccountEntity(uuid, new BigDecimal("0.0"), "test2");

        accountDao.save(entity);
        AccountEntity accountEntity = accountDao.find(uuid, entity.getLogin());

        Assertions.assertEquals(entity, accountEntity);
    }

    @Test
    void findByLogin() {
        AccountDao accountDao = new AccountDao();
        UUID uuid = UUID.randomUUID();
        AccountEntity entity = new AccountEntity(uuid, new BigDecimal("0.0"), "test3");

        accountDao.save(entity);
        AccountEntity accountEntity = accountDao.find(entity.getLogin());

        Assertions.assertEquals(entity, accountEntity);
    }

    @Test
    void saveTest() {
        AccountDao accountDao = new AccountDao();
        UUID uuid = UUID.randomUUID();
        AccountEntity entity = new AccountEntity(uuid, new BigDecimal("0.0"), "test4");

        AccountEntity accountEntity = accountDao.save(entity);

        Assertions.assertEquals(entity, accountEntity);
    }

    @Test
    void updateBalanceTest() {
        AccountDao accountDao = new AccountDao();
        UUID uuid = UUID.randomUUID();
        AccountEntity entity = new AccountEntity(uuid, new BigDecimal("0.0"), "test5");
        accountDao.save(entity);
        entity.setBalance(entity.getBalance().subtract(new BigDecimal("17.0")));

        AccountEntity accountEntity = accountDao.updateBalance(entity);

        Assertions.assertEquals(entity, accountEntity);
    }
}
