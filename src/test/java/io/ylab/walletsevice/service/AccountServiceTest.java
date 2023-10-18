package io.ylab.walletsevice.service;

import io.ylab.walletservice.core.dto.AccountDTO;
import io.ylab.walletservice.core.enums.UserRole;
import io.ylab.walletservice.dao.AccountDao;
import io.ylab.walletservice.dao.AuditDao;
import io.ylab.walletservice.dao.UserDao;
import io.ylab.walletservice.dao.factory.AccountDaoFactory;
import io.ylab.walletservice.dao.entity.AccountEntity;
import io.ylab.walletservice.dao.entity.UserEntity;
import io.ylab.walletservice.service.AccountService;
import io.ylab.walletservice.service.factory.AccountServiceFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AccountServiceTest {

    private AccountService accountService;

    private AccountDao accountDao;
    private UserDao userDao;
    private AuditDao auditDao;

    @BeforeEach
    @DisplayName("Initialize classes for tests")
    public void setUp() {
        accountDao = (AccountDao) AccountDaoFactory.getInstance();
        accountService = (AccountService) AccountServiceFactory.getInstance();
        userDao = new UserDao();
        auditDao = new AuditDao();
    }
    @Test
    @DisplayName("Test for creating account")
    void createTest() {
        UserEntity userEntity = new UserEntity();
        userEntity.setLogin("Have never been created account test1");
        userEntity.setPassword("1tset");
        userEntity.setRole(UserRole.USER);
        UserEntity savedUserEntity = userDao.save(userEntity);

        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setLogin(userEntity.getLogin());
        accountDTO.setBalance(new BigDecimal("0.0"));
        AccountEntity savedAccountEntity = accountService.create(accountDTO);

        accountDao.delete(savedAccountEntity.getId());
        auditDao.deleteByUserId(savedUserEntity.getId());
        userDao.delete(savedUserEntity.getId());

        assertEquals(accountDTO.getBalance(), savedAccountEntity.getBalance());
        assertEquals(savedUserEntity.getId(), savedAccountEntity.getUserId());
    }

    @Test
    @DisplayName("Test for getting account by number of account")
    void getByNumberAccountTest() {
        UserEntity userEntity = new UserEntity();
        userEntity.setLogin("Have never been created account test1");
        userEntity.setPassword("1tset");
        userEntity.setRole(UserRole.USER);
        UserEntity savedEntity = userDao.save(userEntity);

        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setUserId(userEntity.getId());
        accountEntity.setBalance(new BigDecimal("0.0"));
        AccountEntity savedAccountEntity = accountDao.save(accountEntity);
        AccountEntity foundAccountEntity = accountService.get(savedAccountEntity.getId());

        accountDao.delete(savedAccountEntity.getId());
        userDao.delete(savedEntity.getId());

        Assertions.assertEquals(savedAccountEntity, foundAccountEntity);
    }

    @Test
    @DisplayName("Test for getting account by number of account and login")
    void getByNumberAccountAndLoginTest() {
        UserEntity userEntity = new UserEntity();
        userEntity.setLogin("Have never been created account test1");
        userEntity.setPassword("1tset");
        userEntity.setRole(UserRole.USER);
        UserEntity savedEntity = userDao.save(userEntity);

        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setUserId(userEntity.getId());
        accountEntity.setBalance(new BigDecimal("0.0"));
        AccountEntity savedAccountEntity = accountDao.save(accountEntity);
        AccountEntity foundAccountEntity = accountService.get(savedAccountEntity.getId(), userEntity.getLogin());

        accountDao.delete(savedAccountEntity.getId());
        userDao.delete(savedEntity.getId());

        Assertions.assertEquals(savedAccountEntity, foundAccountEntity);
    }

    @Test
    @DisplayName("Test for getting account by login")
    void getByLoginTest() {
        UserEntity userEntity = new UserEntity();
        userEntity.setLogin("Have never been created account test1");
        userEntity.setPassword("1tset");
        userEntity.setRole(UserRole.USER);
        UserEntity savedEntity = userDao.save(userEntity);

        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setUserId(userEntity.getId());
        accountEntity.setBalance(new BigDecimal("0.0"));
        AccountEntity savedAccountEntity = accountDao.save(accountEntity);
        AccountEntity foundAccountEntity = accountService.get(userEntity.getLogin());

        accountDao.delete(savedAccountEntity.getId());
        userDao.delete(savedEntity.getId());

        Assertions.assertEquals(savedAccountEntity, foundAccountEntity);
    }

}
