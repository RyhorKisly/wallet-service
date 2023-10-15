package io.ylab.walletservice.in;

import io.ylab.walletservice.core.enums.Operation;
import io.ylab.walletservice.core.dto.AuditDTO;
import io.ylab.walletservice.core.dto.UserDTO;
import io.ylab.walletservice.dao.entity.AccountEntity;
import lombok.RequiredArgsConstructor;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * Menu class. With this class begin program in console
 */
@RequiredArgsConstructor
public class Menu {

    /**
     * Main menu. First printed thing into console
     */
    private static final String MAIN_MENU = """
            MAIN MENU
            1. Sign up
            2. Sign in
            3. Exit
            Enter from 1 to 3
            """;

    /**
     * User see it into console when user authorized.
     */
    private static final String USER_MENU = """
            User: %s
            Number of your account: %s
            Balance: %s
            1. Credit transaction
            2. Debit transaction
            3. Check history of transactions
            4. Audit of user actions
            5. Sign out and Back to main menu
            Enter from 1 to 5
            """;

    /**
     * Basic Error message
     */
    private static final String ERROR = "Error";

    /**
     * Basic Wrong data message
     */
    private static final String WRONG_DATA = "The information entered incorrectly. Try again";

    /**
     * For interaction with menu
     */
    private final AuthenticationGate authenticationGate;

    /**
     * For interaction with menu
     */
    private final TransactionalGate transactionalGate;

    /**
     * define a field with a type {@link AccountGate} for further aggregation
     */
    private final AccountGate accountGate;

    /**
     * define a field with a type {@link AuditGate} for further aggregation
     */
    private final AuditGate auditGate;

    /**
     * With this method our app start.
     * On console user see text which gives him opportunity to sign up, sign in or exit
     */
    public  void start(BufferedReader reader) {
        while (true) {
            switch (orderMainMenu(reader)) {
                case 1:
                    authenticationGate.register(reader);
                    break;
                case 2:
                    UserDTO userDTO = authenticationGate.authorize(reader);
                    if(userDTO != null) {
                        startAuthorizedMenu(reader, userDTO);
                    }
                    break;
                case 3: System.exit(0);
                    break;
            }
        }
    }

    /**
     * With this method user see menu when user was authorised and choose available action
     * @param reader Reads text from a character-input stream
     * @param userDTO used to provide authorised user
     */
    private void startAuthorizedMenu(BufferedReader reader, UserDTO userDTO) {
        while (true) {
            switch (orderAuthorizedMenu(reader, userDTO)) {
                case 1 -> {
                    transactionalGate.creditDebitTransaction(reader, userDTO, Operation.CREDIT);
                }
                case 2 -> {
                    transactionalGate.creditDebitTransaction(reader, userDTO, Operation.DEBIT);
                }
                case 3 -> {
                    transactionalGate.getTransactionsHistory(userDTO, accountGate.getAccount(userDTO.getLogin()));
                }
                case 4 -> {
                    auditGate.getUserAudit(userDTO.getLogin());
                }
                case 5 -> {
                    AuditDTO auditDTO = new AuditDTO(userDTO.getLogin(), "User logged out");
                    auditGate.create(auditDTO);
                    return;
                }
            }
        }
    }

    /**
     * Print main menu in app where you can choose the next action
     * @param reader reads text from User
     * @return number chosen for next action
     */
    private static int orderMainMenu(BufferedReader reader) {
        System.out.println(MAIN_MENU);
        while (true) {
            try {
                try {
                    int count = Integer.parseInt(reader.readLine());
                    if (count == 1 || count == 2 || count == 3) {
                        return count;
                    } else {
                        System.out.println(WRONG_DATA);
                    }
                } catch (NumberFormatException e) {
                    System.out.println(WRONG_DATA);
                }
            } catch (IOException e) {
                System.out.println(ERROR);
                System.exit(0);
            }
        }
    }

    /**
     * Print menu for authorised user in app where you can choose the next action
     * @param reader reads text from User
     * @return number chosen for next action
     */
    private int orderAuthorizedMenu(BufferedReader reader, UserDTO userDTO) {
        AccountEntity accountEntity = accountGate.getAccount(userDTO.getLogin());
        UUID numberAccount = accountEntity.getNumberAccount();
        BigDecimal balance = accountEntity.getBalance();

        System.out.printf((USER_MENU) + "%n", userDTO.getLogin(), numberAccount, balance);

        while (true) {
            try {
                try {
                    int count = Integer.parseInt(reader.readLine());
                    if (count == 1 || count == 2 || count == 3 || count == 4 || count == 5) {
                        System.out.println();
                        return count;
                    } else {
                        System.out.println(WRONG_DATA);
                    }
                } catch (NumberFormatException e) {
                    System.out.println(WRONG_DATA);
                }
            } catch (IOException e) {
                System.out.println(ERROR);
                System.exit(0);
            }
        }
    }
}
