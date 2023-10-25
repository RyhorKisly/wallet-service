package io.ylab.walletsevice.core.mappers;

import io.ylab.walletservice.core.dto.AccountDTO;
import io.ylab.walletservice.core.mappers.AccountMapper;
import io.ylab.walletservice.core.mappers.AccountMapperImpl;
import io.ylab.walletservice.dao.entity.AccountEntity;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Class for testing mappers for Account DTOs and entities")
public class AccountMapperTest {

    /**
     * Define a field with a type {@link AccountMapper} for further use in the test
     */
    private AccountMapper accountMapper;

    @BeforeAll
    @DisplayName("Initialize classes for tests")
    public void setUp() {
        this.accountMapper = new AccountMapperImpl();
    }

    @Test
    @DisplayName("Positive test for mapping auditEntity to AduitDTO")
    void toDTOTest() {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setId(33L);
        accountEntity.setBalance(new BigDecimal("0.0"));
        accountEntity.setUserId(33L);

        AccountDTO accountDTO = accountMapper.toDTO(accountEntity);

        Assertions.assertNotNull(accountDTO);

        Assertions.assertEquals(accountEntity.getId(), accountDTO.getId());
        Assertions.assertEquals(accountEntity.getBalance(), accountDTO.getBalance());
        Assertions.assertEquals(accountEntity.getUserId(), accountDTO.getUserId());
    }
}
