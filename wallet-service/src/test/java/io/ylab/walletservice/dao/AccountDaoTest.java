package io.ylab.walletservice.dao;

import io.ylab.walletservice.core.enums.UserRole;
import io.ylab.walletservice.dao.api.IAccountDao;
import io.ylab.walletservice.dao.api.IUserDao;
import io.ylab.walletservice.utils.IntegrationTestBase;
import io.ylab.walletservice.dao.entity.AccountEntity;
import io.ylab.walletservice.dao.entity.UserEntity;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

@DisplayName("Class for testing methods of the class AccountDao")
public class AccountDaoTest extends IntegrationTestBase {

    /**
     * Define a field with a type {@link IAccountDao} for further use in the test
     */
    @Autowired
    private IAccountDao accountDao;

    /**
     * Define a field with a type {@link IUserDao} for further use in the test
     */
    @Autowired
    private IUserDao userDao;

    @Test
    @DisplayName("Positive test for saving account")
    void saveTest() {
        UserEntity userEntity = new UserEntity();
        userEntity.setLogin("Have never been created account test1");
        userEntity.setPassword("test");
        userEntity.setRole(UserRole.USER);
        UserEntity savedUserEntity = userDao.save(userEntity);

        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setUserId(savedUserEntity.getId());
        accountEntity.setBalance(new BigDecimal("0.0"));
        AccountEntity savedAccountEntity = accountDao.save(accountEntity);

        assertEquals(accountEntity.getBalance(), savedAccountEntity.getBalance());
        assertEquals(accountEntity.getUserId(), savedAccountEntity.getUserId());
    }

    @Test
    @DisplayName("Negative test for saving user with the same user_id as saved in account")
    void saveUniqueLoginTest() {
        UserEntity userEntity = new UserEntity();
        userEntity.setLogin("Have never been created account test1");
        userEntity.setPassword("test");
        userEntity.setRole(UserRole.USER);
        UserEntity savedUserEntity = userDao.save(userEntity);

        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setUserId(savedUserEntity.getId());
        accountEntity.setBalance(new BigDecimal("0.0"));
        accountDao.save(accountEntity);

        AccountEntity accountEntity2 = new AccountEntity();
        accountEntity2.setUserId(userEntity.getId());
        accountEntity2.setBalance(new BigDecimal("0.0"));

        assertThrowsExactly(RuntimeException.class, () -> accountDao.save(accountEntity2));

    }

    @Test
    @DisplayName("Positive test for finding account by id")
    void findByIdTest() {
        UserEntity userEntity = new UserEntity();
        userEntity.setLogin("Have never been created account test1");
        userEntity.setPassword("test");
        userEntity.setRole(UserRole.USER);
        UserEntity savedEntity = userDao.save(userEntity);

        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setUserId(savedEntity.getId());
        accountEntity.setBalance(new BigDecimal("0.0"));
        AccountEntity savedAccountEntity = accountDao.save(accountEntity);
        Optional<AccountEntity> foundAccountEntity = accountDao.find(savedAccountEntity.getId());

        Assertions.assertEquals(savedAccountEntity, foundAccountEntity.orElseThrow(RuntimeException::new));
    }

    @Test
    @DisplayName("Negative test for checking null when id does not exist")
    void findByIdNonExistentAccountTest() {
        Optional<AccountEntity> foundAccountEntity = accountDao.find(-23423423424L);
        assertThrowsExactly(RuntimeException.class, () -> foundAccountEntity.orElseThrow(RuntimeException::new));
    }

    @Test
    @DisplayName("Positive test for finding account by id and login")
    void findByIdAndLoginTest() {
        UserEntity userEntity = new UserEntity();
        userEntity.setLogin("Have never been created account test1234");
        userEntity.setPassword("test");
        userEntity.setRole(UserRole.USER);
        UserEntity savedEntity = userDao.save(userEntity);

        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setUserId(savedEntity.getId());
        accountEntity.setBalance(new BigDecimal("0.0"));
        AccountEntity savedAccountEntity = accountDao.save(accountEntity);
        Optional<AccountEntity> foundAccountEntity = accountDao.find(savedAccountEntity.getId(), userEntity.getLogin());

        Assertions.assertEquals(savedAccountEntity, foundAccountEntity.orElseThrow(RuntimeException::new));
    }

    @Test
    @DisplayName("Negative test for checking null when id and login does not exist")
    void findByIdAndLoginNonExistentAccountTest() {
        Optional<AccountEntity> foundAccountEntity = accountDao.find(-2332L, "Have never been created user)");
        assertThrowsExactly(RuntimeException.class, () -> foundAccountEntity.orElseThrow(RuntimeException::new));

    }

    @Test
    @DisplayName("Positive test for finding account by login")
    void findByLoginTest() {
        UserEntity userEntity = new UserEntity();
        userEntity.setLogin("Have never been created account test123");
        userEntity.setPassword("test");
        userEntity.setRole(UserRole.USER);
        UserEntity savedEntity = userDao.save(userEntity);

        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setUserId(savedEntity.getId());
        accountEntity.setBalance(new BigDecimal("0.0"));
        AccountEntity savedAccountEntity = accountDao.save(accountEntity);
        Optional<AccountEntity> foundAccountEntity = accountDao.findByUserId(userEntity.getId());

        Assertions.assertEquals(savedAccountEntity, foundAccountEntity.orElseThrow(RuntimeException::new));
    }

    @Test
    @DisplayName("Negative test for checking null when login does not exist")
    void findByLoginNonExistentAccountTest() {
        Optional<AccountEntity> foundAccountEntity = accountDao.find( 34534L);
        assertThrowsExactly(RuntimeException.class, () -> foundAccountEntity.orElseThrow(RuntimeException::new));

    }

    @Test
    @DisplayName("Test for checking update balance for account")
    void updateBalanceTest() {
        UserEntity userEntity = new UserEntity();
        userEntity.setLogin("Have never been created account test1");
        userEntity.setPassword("1tset");
        userEntity.setRole(UserRole.USER);
        UserEntity savedUserEntity = userDao.save(userEntity);

        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setUserId(savedUserEntity.getId());
        accountEntity.setBalance(new BigDecimal("0.0"));
        AccountEntity savedAccountEntity = accountDao.save(accountEntity);
        savedAccountEntity.setBalance(new BigDecimal("3.4"));
        AccountEntity updatedAccountEntity = accountDao.updateBalance(savedAccountEntity);

        assertEquals(savedAccountEntity.getBalance(), updatedAccountEntity.getBalance());
    }



}
