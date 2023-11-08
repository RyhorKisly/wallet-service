package io.ylab.walletservice.service;

import io.ylab.walletservice.core.dto.AccountDTO;
import io.ylab.walletservice.core.enums.UserRole;
import io.ylab.walletservice.dao.api.IAccountDao;
import io.ylab.walletservice.dao.entity.AccountEntity;
import io.ylab.walletservice.dao.entity.UserEntity;
import io.ylab.walletservice.service.api.IAccountService;
import io.ylab.walletservice.service.api.IUserService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@DisplayName("Class for testing methods of the class AccountService")
class AccountServiceTest {

    /**
     * define a field with a type {@link IAccountDao} for further aggregation
     */
    @Mock
    private IAccountDao accountDao;

    /**
     * define a field with a type {@link IUserService} for further aggregation
     */
    @Mock
    private IUserService userService;

    /**
     * Define a field with a type {@link IAccountService} for further use in the test
     */
    private IAccountService accountService;

    @BeforeEach
    @DisplayName("Initialize accountService with mocked userService and accountDao")
    public void setUp() {
        accountService = new AccountService(accountDao, userService);
    }

    @Test
    @DisplayName("Positive test for creating account")
    void createTest() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setLogin("Have never been created account test1");
        userEntity.setPassword("test");
        userEntity.setRole(UserRole.USER);

        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setUserId(userEntity.getId());
        accountDTO.setBalance(new BigDecimal("0.0"));

        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setBalance(accountDTO.getBalance());
        accountEntity.setUserId(accountDTO.getUserId());

        when(userService.get(accountDTO.getUserId())).thenReturn(userEntity);
        when(accountDao.save(accountEntity)).thenReturn(accountEntity);

        AccountEntity savedEntity = accountService.create(accountDTO);

        assertEquals(accountEntity, savedEntity);
    }

    @Test
    @DisplayName("Positive test for getting account by number of account")
    void getByNumberAccountTest() {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setId(1L);
        accountEntity.setUserId(1L);
        accountEntity.setBalance(new BigDecimal("0.0"));

        when(accountDao.find(accountEntity.getId())).thenReturn(Optional.of(accountEntity));
        AccountEntity foundAccountEntity = accountService.get(accountEntity.getId());

        Assertions.assertEquals(accountEntity, foundAccountEntity);
    }

    @Test
    @DisplayName("Negative test for getting account by number of account if not find")
    void getByNumberAccountTestIfNotExist() {
        when(accountDao.find(1L)).thenReturn(Optional.empty());
        Assertions.assertThrows(RuntimeException.class, () -> accountService.get(1L));
    }

    @Test
    @DisplayName("Positive test for getting account by number of account and login")
    void getByNumberAccountAndLoginTest() {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setId(1L);
        accountEntity.setUserId(1L);
        accountEntity.setBalance(new BigDecimal("0.0"));

        when(accountDao.find(accountEntity.getId(), "test")).thenReturn(Optional.of(accountEntity));
        AccountEntity foundAccountEntity = accountService.get(accountEntity.getId(), "test");

        Assertions.assertEquals(accountEntity, foundAccountEntity);
    }

    @Test
    @DisplayName("Negative test for getting account by number of account and login if not find")
    void getByNumberAccountAndLoginTestIfNotExist() {
        when(accountDao.find(1L, "test")).thenReturn(Optional.empty());
        Assertions.assertThrows(RuntimeException.class, () -> accountService.get(1L, "test"));
    }

    @Test
    @DisplayName("Positive test for getting account by userId")
    void getByUserIdTest() {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setId(1L);
        accountEntity.setUserId(1L);
        accountEntity.setBalance(new BigDecimal("0.0"));

        when(accountDao.findByUserId(1L)).thenReturn(Optional.of(accountEntity));
        AccountEntity foundAccountEntity = accountService.getByUser(1L);

        Assertions.assertEquals(accountEntity, foundAccountEntity);
    }

    @Test
    @DisplayName("Negative test for getting account by userId if not find")
    void getByUserIdTestIfNotFind() {
        when(accountDao.findByUserId(1L)).thenReturn(Optional.empty());
        Assertions.assertThrows(RuntimeException.class, () -> accountService.getByUser(1L));
    }

}
