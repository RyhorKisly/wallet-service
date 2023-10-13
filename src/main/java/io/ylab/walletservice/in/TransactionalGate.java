package io.ylab.walletservice.in;

import io.ylab.walletservice.core.enums.Operation;
import io.ylab.walletservice.core.dto.TransactionDTO;
import io.ylab.walletservice.core.dto.UserDTO;
import io.ylab.walletservice.core.exceptions.ExistOrEmptyAccountException;
import io.ylab.walletservice.dao.entity.AccountEntity;
import io.ylab.walletservice.dao.entity.TransactionEntity;
import io.ylab.walletservice.service.api.IAccountService;
import io.ylab.walletservice.service.api.ITransactionService;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

/**
 * Class fo interaction between {@link ITransactionService} and {@link Menu}
 */
public class TransactionalGate {

    /**
     * Print if TransactionalId entered by user empty or exist
     */
    private static final String TRANSACTION_ID_EXIST_OR_EMPTY = "Empty or exist transactionID!";

    /**
     * Print if user data incorrectly
     */
    private static final String WRONG_DATA = "The information entered incorrectly. Try again";

    /**
     * Print if user entered sum of transaction incorrectly
     */
    private static final String WRONG_DATA_BALANCE = "Wrong dates. You should enter number in the format (x.x or xx). Try again!";

    /**
     * Print to show which one param user have to enter
     */
    private static final String ENTER_PARAM = "Enter %s. Or return menu entered \"back\": ";

    /**
     * marker for switch to make the code easier to read
     */
    private static final String TRANSACTIONAL_IDENTIFIER = "transactional identifier";

    /**
     * marker for switch to make the code easier to read
     */
    private static final String NUMBER_OF_ACCOUNT = "number of Account";

    /**
     * marker for switch to make the code easier to read
     */
    private static final String SUM_OF_TRANSACTION = "sum of Transaction";

    /**
     * Print if user do debit transaction and sum of transaction more than balance on account
     */
    private static final String TRANSACTION_FAILED = "Transaction faild! Balance can't be less than 0.0\n";

    /**
     * Print when transaction completed successfully
     */
    private static final String SUCCESSFUL_TRANSACTION = "Transaction completed successfully!\n";

    /**
     * Print to show whom history of transactions user will see
     */
    private static final String HISTORY_TRANSACTION = "History of transactions for User: ";

    /**
     * Print to show history of transaction
     */
    private static final String PRINT_HISTORY_TRANSACTION = "Operation: %s; Sum: %s; Date: %s\n";

    /**
     * Print if user have not done any transaction
     */
    private static final String USER_NOT_MADE_TRANSACTION = "User has not made any transactions";

    /**
     * Print if user entered wrong transactionID (empty or exist)
     */
    private static final String WRONG_DATA_FORMAT = "Wrong data format!";

    /**
     * Message should be entered if user want comeback to menu
     */
    private static final String BACK = "back";

    /**
     * Basic message error
     */
    private static final String ERROR = "Error";

    /**
     * define a field with a type {@link IAccountService} for further aggregation
     */
    private final IAccountService accountService;

    /**
     * define a field with a type {@link ITransactionService} for further aggregation
     */
    private final ITransactionService transactionService;

    /**
     * Used for passing an instance of a {@link IAccountService} and a {@link ITransactionService} from outside
     * @param accountService passed to the constructor to establish Aggregation
     * @param transactionService passed to the constructor to establish Aggregation
     */
    public TransactionalGate(IAccountService accountService, ITransactionService transactionService) {
        this.accountService = accountService;
        this.transactionService = transactionService;
    }

    /**
     * Сarries out a debit or credit transaction
     * @param reader Reads text from a character-input stream
     * @param userDTO used to identify account
     * @param operation used to identify transaction (debit, credit)
     */
    public void creditDebitTransaction(BufferedReader reader, UserDTO userDTO, Operation operation) {
        String transactionID = enterParam(reader, TRANSACTIONAL_IDENTIFIER, userDTO);
        if(transactionID == null) return;
        String numberAccount = enterParam(reader, NUMBER_OF_ACCOUNT, userDTO);
        if(numberAccount == null) return;
        String sumOfTransaction = enterParam(reader, SUM_OF_TRANSACTION, userDTO);
        if(sumOfTransaction == null) return;

        TransactionDTO transactionDTO = new TransactionDTO(
                transactionID,
                operation,
                new BigDecimal(sumOfTransaction),
                accountService.get(numberAccount, userDTO.getLogin()).getNumberAccount()
        );

        transactionService.create(transactionDTO, userDTO.getLogin());
        AccountEntity accountEntity = accountService.updateBalance(UUID.fromString(numberAccount), transactionDTO);
        if(accountEntity == null) {
            System.out.println(TRANSACTION_FAILED);
        } else {
            System.out.println(SUCCESSFUL_TRANSACTION);
        }
    }

    /**
     * Get history of transactions
     * @param userDTO for identifying user in menu
     * @param accountEntity for getting history of transaction specific user with specific account
     */
    public void getTransactionsHistory(UserDTO userDTO, AccountEntity accountEntity) {
        Set<TransactionEntity> historyOfTransactions = transactionService.get(accountEntity);
        System.out.println(HISTORY_TRANSACTION + userDTO.getLogin());
        for (TransactionEntity transaction : historyOfTransactions) {
            System.out.printf(PRINT_HISTORY_TRANSACTION,
                    transaction.getOperation(),
                    transaction.getSumOfTransaction(),
                    transaction.getDtCreate()
            );
        }
        if(historyOfTransactions.isEmpty()) {
            System.out.println(USER_NOT_MADE_TRANSACTION);
        }
        System.out.println();
    }

    /**
     * The method is used to enter the user parameters for carrying out a transaction.
     * @param reader Reads text from a character-input stream
     * @param param say to user which one param should be entered in console
     * @param userDTO used to check number of Account for user
     * @return String with param entered by user
     */
    private String enterParam(BufferedReader reader, String param, UserDTO userDTO) {
        while (true) {
                try {
                    System.out.printf(ENTER_PARAM, param);
                    String inParam = reader.readLine();
                    if (inParam.equals(BACK)) {
                        return null;
                    }

                    switch (param) {
                        case TRANSACTIONAL_IDENTIFIER -> {
                            return checkTransactionalID(inParam);
                        }
                        case NUMBER_OF_ACCOUNT -> {
                            return checkAccountNumber(inParam, userDTO);
                        }
                        case SUM_OF_TRANSACTION -> {
                            new BigDecimal(inParam);
                            return inParam;
                        }
                        default -> {
                            return inParam;
                        }
                    }
                } catch(ExistOrEmptyAccountException ex) {
                    System.out.println(WRONG_DATA_FORMAT);
                } catch (NumberFormatException ex) {
                    System.out.println(WRONG_DATA_BALANCE);
                } catch(IllegalArgumentException ex) {
                    System.out.println(WRONG_DATA);
                } catch (IOException e) {
                    System.out.println(ERROR);
                    System.exit(0);
                }
        }
    }

    /**
     *  Method for checking transaction id (exist or empty)
     * @param transactionalID this parameter is checked for correct input
     * @return if correct transactionID or error
     */
    private String checkTransactionalID(String transactionalID) {
        if (transactionalID.isEmpty() || transactionService.isExist(transactionalID)) {
            System.err.println(TRANSACTION_ID_EXIST_OR_EMPTY);
            System.exit(1);
        }
        return transactionalID;
    }

    /**
     * Method for checking accountNumber
     * @param accountNumber for checking this param
     * @param userDTO used to check number of Account for user
     * @return if correct accountNumber or exception
     */
    private String checkAccountNumber(String accountNumber, UserDTO userDTO) {
        UUID.fromString(accountNumber);
        if (accountService.get(accountNumber, userDTO.getLogin()) == null || accountNumber.isEmpty()) {
            throw new ExistOrEmptyAccountException();
        }
        return accountNumber;
    }

}
