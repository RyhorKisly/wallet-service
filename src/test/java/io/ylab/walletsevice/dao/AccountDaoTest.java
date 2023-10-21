package io.ylab.walletsevice.dao;

import io.ylab.walletservice.core.enums.UserRole;
import io.ylab.walletservice.dao.AccountDao;
import io.ylab.walletservice.dao.UserDao;
import io.ylab.walletservice.dao.entity.AccountEntity;
import io.ylab.walletservice.dao.entity.UserEntity;
import io.ylab.walletsevice.dao.ds.factory.ConnectionWrapperFactoryTest;
import io.ylab.walletsevice.dao.utils.api.ILiquibaseManagerTest;
import io.ylab.walletsevice.dao.utils.factory.LiquibaseManagerTestFactory;
import io.ylab.walletsevice.testcontainers.config.ContainersEnvironment;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AccountDaoTest extends ContainersEnvironment {
    private AccountDao accountDao;
    private UserDao userDao;
    private ILiquibaseManagerTest liquibaseManagerTest;
    @BeforeEach
    @DisplayName("Initialize class for tests")
    public void setUp() {
        accountDao = new AccountDao(ConnectionWrapperFactoryTest.getInstance());
        userDao = new UserDao(ConnectionWrapperFactoryTest.getInstance());
        liquibaseManagerTest = LiquibaseManagerTestFactory.getInstance();
        liquibaseManagerTest.migrateDbCreate();
    }

    @AfterEach
    @DisplayName("Migrates dates to drop schema and tables")
    public void drop() {
        this.liquibaseManagerTest.migrateDbDrop();
    }

    @Test
    @DisplayName("Positive test for saving account")
    void saveTest() {
        UserEntity userEntity = new UserEntity();
        userEntity.setLogin("Have never been created account test1");
        userEntity.setPassword("1tset");
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
        userEntity.setPassword("1tset");
        userEntity.setRole(UserRole.USER);
        UserEntity savedUserEntity = userDao.save(userEntity);

        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setUserId(savedUserEntity.getId());
        accountEntity.setBalance(new BigDecimal("0.0"));
        accountDao.save(accountEntity);

        AccountEntity accountEntity2 = new AccountEntity();
        accountEntity2.setUserId(userEntity.getId());
        accountEntity2.setBalance(new BigDecimal("0.0"));
        AccountEntity savedAccountEntity2 = accountDao.save(accountEntity2);

        Assertions.assertNull(savedAccountEntity2.getId());

    }

    @Test
    @DisplayName("Test for finding account by id")
    void findByIdTest() {
        UserEntity userEntity = new UserEntity();
        userEntity.setLogin("Have never been created account test1");
        userEntity.setPassword("1tset");
        userEntity.setRole(UserRole.USER);
        UserEntity savedEntity = userDao.save(userEntity);

        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setUserId(savedEntity.getId());
        accountEntity.setBalance(new BigDecimal("0.0"));
        AccountEntity savedAccountEntity = accountDao.save(accountEntity);
        AccountEntity foundAccountEntity = accountDao.find(savedAccountEntity.getId());

        Assertions.assertEquals(savedAccountEntity, foundAccountEntity);
    }

    @Test
    @DisplayName("Negative test for checking null when id does not exist")
    void findByIdNonExistentAccountTest() {
        Assertions.assertNull(accountDao.find(-23423423424L));
    }

    @Test
    @DisplayName("Test for finding account by id and login")
    void findByIdAndLoginTest() {
        UserEntity userEntity = new UserEntity();
        userEntity.setLogin("Have never been created account test1");
        userEntity.setPassword("1tset");
        userEntity.setRole(UserRole.USER);
        UserEntity savedEntity = userDao.save(userEntity);

        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setUserId(savedEntity.getId());
        accountEntity.setBalance(new BigDecimal("0.0"));
        AccountEntity savedAccountEntity = accountDao.save(accountEntity);
        AccountEntity foundAccountEntity = accountDao.find(savedAccountEntity.getId(), userEntity.getLogin());

        Assertions.assertEquals(savedAccountEntity, foundAccountEntity);
    }

    @Test
    @DisplayName("Negative test for checking null when id and login does not exist")
    void findByIdAndLoginNonExistentAccountTest() {
        Assertions.assertNull(accountDao.find(-2332L, "Have never been created user)"));
    }

    @Test
    @DisplayName("Test for finding account by login")
    void findByLoginTest() {
        UserEntity userEntity = new UserEntity();
        userEntity.setLogin("Have never been created account test1");
        userEntity.setPassword("1tset");
        userEntity.setRole(UserRole.USER);
        UserEntity savedEntity = userDao.save(userEntity);

        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setUserId(savedEntity.getId());
        accountEntity.setBalance(new BigDecimal("0.0"));
        AccountEntity savedAccountEntity = accountDao.save(accountEntity);
        AccountEntity foundAccountEntity = accountDao.find(userEntity.getLogin());

        Assertions.assertEquals(savedAccountEntity, foundAccountEntity);
    }

    @Test
    @DisplayName("Negative test for checking null when login does not exist")
    void findByLoginNonExistentAccountTest() {
        Assertions.assertNull(accountDao.find( "Have never been created user)"));
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
