package io.ylab.walletservice.core.dto;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Transfer account data for transfer to a storage location
 */
public class AccountDTO {

    /**
     * Used like ID of account in order to save in storage place and show in menu.
     */
    private UUID numberAccount;

    /**
     * Current account balance. Used for transfer the original balance value to the storage place.
     */
    private BigDecimal balance;

    /**
     * Identifies that the account belongs to a specific user.
     */
    private String login;

    /**
     * Give you opportunity construct dto without initial params.
     * It can be useful in case where you want check some params for null before adding in to the object
     */
    public AccountDTO() {
    }

    /**
     *  Constructs dto with the specified params.
     * @param numberAccount the value used to set the {@code numberAccount} dto field
     * @param balance the value used to set the {@code balance} dto field
     * @param login the value used to set the {@code login} dto field
     */
    public AccountDTO(
            UUID numberAccount,
            BigDecimal balance,
            String login
    ) {
        this.numberAccount = numberAccount;
        this.balance = balance;
        this.login = login;
    }

    /**
     * Returns account ID.
     * @return account ID with UUID type
     */
    public UUID getNumberAccount() {
        return numberAccount;
    }

    /**
     * Sets the values for the field {@code numberAccount}
     * @param numberAccount the value used to set the {@code numberAccount} dto field.
     */
    public void setNumberAccount(UUID numberAccount) {
        this.numberAccount = numberAccount;
    }

    /**
     * Returns current balance.
     * @return current balance
     */
    public BigDecimal getBalance() {
        return balance;
    }

    /**
     * Sets the values for the field {@code balance}
     * @param balance the value used to set the {@code balance} dto field.
     */
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    /**
     * Returns current login.
     * @return current login
     */
    public String getLogin() {
        return login;
    }

    /**
     * Sets the values for the field {@code login}
     * @param login the value used to set the {@code login} dto field.
     */
    public void setLogin(String login) {
        this.login = login;
    }
}
