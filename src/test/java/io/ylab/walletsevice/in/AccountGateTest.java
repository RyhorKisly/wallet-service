package io.ylab.walletsevice.in;

import io.ylab.walletservice.dao.UserDao;
import io.ylab.walletservice.dao.entity.AccountEntity;
import io.ylab.walletservice.dao.entity.UserEntity;
import io.ylab.walletservice.in.AccountGate;
import io.ylab.walletservice.service.factory.AccountServiceFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AccountGateTest {
    private AccountGate accountGate;
    private UserDao userDao;

    @BeforeEach
    @DisplayName("Initialize classes for tests")
    public void setUp() {
        accountGate = new AccountGate(AccountServiceFactory.getInstance());
        userDao = new UserDao();
    }

    @Test
    @DisplayName("Test for getting account by login")
    void getAccountTest() {
        UserEntity userEntity = userDao.find("admin");
        AccountEntity foundAccountEntity = accountGate.getAccount("admin");

        Assertions.assertEquals(userEntity.getId(), foundAccountEntity.getUserId());
    }
}
