package io.ylab.walletservice.dao.entity;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

/**
 * Stored business logic object. Used to save, retrieve and update state to a storage location
 */
public class AccountEntity {

    /**
     * Used like ID of an account. Should be unique
     */
    private UUID numberAccount;

    /**
     * Current account balance.
     */
    private BigDecimal balance;

    /**
     * Identifies that the account belongs to a specific user.
     */
    private String login;

    /**
     * Give you opportunity construct entity without initial params.
     * This is a required condition for using entity with ORM
     */
    public AccountEntity() {
    }

    /**
     *  Constructs entity with the specified params.
     * @param numberAccount the value used to set the {@code numberAccount} entity field
     * @param balance the value used to set the {@code balance} entity field
     * @param login the value used to set the {@code login} entity field
     */
    public AccountEntity(UUID numberAccount, BigDecimal balance, String login) {
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
     * @param numberAccount the value used to set the {@code numberAccount} entity field.
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
     * @param balance the value used to set the {@code balance} entity field.
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
     * @param login the value used to set the {@code login} entity field.
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Method which specifies comparison of instances
     * of a class according to the fields of the class
     * @param o comparison object
     * @return true if objects equals
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountEntity entity = (AccountEntity) o;
        return Objects.equals(numberAccount, entity.numberAccount) && Objects.equals(balance, entity.balance) && Objects.equals(login, entity.login);
    }

    /**
     * Override for specifying hashCode according to the fields of the class
     * @return hashCode according to the fields of the class
     */
    @Override
    public int hashCode() {
        return Objects.hash(numberAccount, balance, login);
    }
}
