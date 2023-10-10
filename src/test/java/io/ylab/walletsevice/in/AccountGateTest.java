package io.ylab.walletsevice.in;

import io.ylab.walletservice.dao.entity.AccountEntity;
import io.ylab.walletservice.dao.memory.AccountDao;
import io.ylab.walletservice.dao.memory.factory.AccountDaoFactory;
import io.ylab.walletservice.in.AccountGate;
import io.ylab.walletservice.service.factory.AccountServiceFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

public class AccountGateTest {
    private AccountGate accountGate;
    private AccountDao accountDao;

    @BeforeEach
    public void setUp() {
        accountGate = new AccountGate(AccountServiceFactory.getInstance());
        accountDao = (AccountDao) AccountDaoFactory.getInstance();
    }

    @Test
    void getAccountTest() {
        UUID uuid = UUID.randomUUID();
        AccountEntity entity = new AccountEntity(uuid, new BigDecimal("0.0"), "test24");
        accountDao.save(entity);

        AccountEntity entityCreated = accountGate.getAccount(entity.getLogin());

        Assertions.assertEquals(entity, entityCreated);
    }
}
