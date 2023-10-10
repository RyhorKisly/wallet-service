package io.ylab.walletsevice.service;

import io.ylab.walletservice.core.dto.AccountDTO;
import io.ylab.walletservice.core.dto.TransactionDTO;
import io.ylab.walletservice.core.dto.UserCreateDTO;
import io.ylab.walletservice.core.enums.Operation;
import io.ylab.walletservice.core.enums.UserRole;
import io.ylab.walletservice.dao.entity.AccountEntity;
import io.ylab.walletservice.dao.entity.TransactionEntity;
import io.ylab.walletservice.service.AccountService;
import io.ylab.walletservice.service.TransactionService;
import io.ylab.walletservice.service.UserService;
import io.ylab.walletservice.service.factory.AccountServiceFactory;
import io.ylab.walletservice.service.factory.TransactionServiceFactory;
import io.ylab.walletservice.service.factory.UserServiceFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

public class TransactionServiceTest {
    private TransactionService transactionService;
    private UserService userService;
    private AccountService accountService;

    @BeforeEach
    public void setUp() {
        transactionService = (TransactionService) TransactionServiceFactory.getInstance();
        userService = (UserService) UserServiceFactory.getInstance();
        accountService = (AccountService) AccountServiceFactory.getInstance();
    }

    @Test
    void createTest() {
        UserCreateDTO userCreateDTO = new UserCreateDTO("test20", UserRole.USER, "02tset");
        userService.create(userCreateDTO);

        UUID uuid = UUID.randomUUID();
        AccountDTO accountDTO = new AccountDTO(uuid, new BigDecimal("0.0"), "test20");
        accountService.create(accountDTO);

        TransactionDTO transactionDTO = new TransactionDTO(
                "zxc", Operation.CREDIT,
                new BigDecimal("3.3"), uuid);
        TransactionEntity transactionEntity = transactionService.create(transactionDTO, userCreateDTO.getLogin());

        Assertions.assertEquals(transactionDTO.getTransactionId(), transactionEntity.getTransactionId());
        Assertions.assertEquals(transactionDTO.getSumOfTransaction(), transactionEntity.getSumOfTransaction());
        Assertions.assertEquals(transactionDTO.getOperation(), transactionEntity.getOperation());
        Assertions.assertEquals(transactionDTO.getNumberAccount(), transactionEntity.getNumberAccount());
    }

    @Test
    void getById() {
        UserCreateDTO userCreateDTO = new UserCreateDTO("test21", UserRole.USER, "12tset");
        userService.create(userCreateDTO);

        UUID uuid = UUID.randomUUID();
        AccountDTO accountDTO = new AccountDTO(uuid, new BigDecimal("0.0"), "test21");
        accountService.create(accountDTO);

        TransactionDTO transactionDTO = new TransactionDTO(
                "zxcv", Operation.CREDIT,
                new BigDecimal("3.4"), uuid);
        TransactionEntity createdEntity = transactionService.create(transactionDTO, userCreateDTO.getLogin());

        Assertions.assertEquals(createdEntity, transactionService.get(createdEntity.getTransactionId()));
    }

    @Test
    void getByAccountEntity() {
        UserCreateDTO userCreateDTO = new UserCreateDTO("test22", UserRole.USER, "22tset");
        userService.create(userCreateDTO);

        UUID uuid = UUID.randomUUID();
        AccountDTO accountDTO = new AccountDTO(uuid, new BigDecimal("0.0"), "test22");
        AccountEntity accountEntity = accountService.create(accountDTO);

        TransactionDTO transactionDTO1 = new TransactionDTO(
                "zxcvb", Operation.CREDIT,
                new BigDecimal("3.4"), uuid);
        TransactionDTO transactionDTO2 = new TransactionDTO(
                "zxcvbn", Operation.CREDIT,
                new BigDecimal("3.4"), uuid);
        TransactionEntity createdEntity1 = transactionService.create(transactionDTO1, userCreateDTO.getLogin());
        TransactionEntity createdEntity2 = transactionService.create(transactionDTO2, userCreateDTO.getLogin());

        Set<TransactionEntity> transactions = new TreeSet<>(Comparator.comparing(TransactionEntity::getDtCreate));
        transactions.add(createdEntity1);
        transactions.add(createdEntity2);

        Assertions.assertEquals(transactions, transactionService.get(accountEntity));
    }

    @Test
    void isExistTest() {
        UserCreateDTO userCreateDTO = new UserCreateDTO("test23", UserRole.USER, "32tset");
        userService.create(userCreateDTO);

        UUID uuid = UUID.randomUUID();
        AccountDTO accountDTO = new AccountDTO(uuid, new BigDecimal("0.0"), "test23");
        accountService.create(accountDTO);

        TransactionDTO transactionDTO = new TransactionDTO(
                "zxcvbnm", Operation.CREDIT,
                new BigDecimal("3.4"), uuid);
        TransactionEntity createdEntity = transactionService.create(transactionDTO, userCreateDTO.getLogin());

        Assertions.assertTrue(transactionService.isExist(createdEntity.getTransactionId()));
    }


}
