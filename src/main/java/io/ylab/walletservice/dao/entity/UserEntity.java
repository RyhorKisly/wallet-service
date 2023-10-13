package io.ylab.walletservice.dao.entity;

import io.ylab.walletservice.core.enums.UserRole;

import java.util.Objects;

/**
 * Stored business logic object. Used to save, retrieve and update state to a storage location
 */
public class UserEntity {

    /**
     * Identifies that the account belongs to a specific user.
     */
    private String login;

    /**
     * Identifies that account belongs to a specific user.
     */
    private String password;

    /**
     * Enum type for description the role of user
     */
    private UserRole role;

    /**
     * Give you opportunity construct entity without initial params.
     * This is a required condition for using entity with ORM
     */
    public UserEntity() {
    }

    /**
     *  Constructs entity with the specified params.
     * @param login the value used to set the {@code login} entity field
     * @param password the value used to set the {@code password} entity field
     * @param role the value used to set the {@code role} entity field
     */
    public UserEntity(String login, String password, UserRole role) {
        this.login = login;
        this.password = password;
        this.role = role;
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
     * Returns current password.
     * @return current password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the values for the field {@code password}
     * @param password the value used to set the {@code password} entity field.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns current role.
     * @return current role
     */
    public UserRole getRole() {
        return role;
    }


    /**
     * Sets the values for the field {@code role}
     * @param role the value used to set the {@code role} entity field.
     */
    public void setRole(UserRole role) {
        this.role = role;
    }

    /**
     * Override method which specifies comparison of instances
     * of a class according to the fields of the class
     * @param o comparison object
     * @return true if objects equals
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return Objects.equals(login, that.login) && Objects.equals(password, that.password) && role == that.role;
    }

    /**
     * Override for specifying hashCode according to the fields of the class
     * @return hashCode according to the fields of the class
     */
    @Override
    public int hashCode() {
        return Objects.hash(login, password, role);
    }
}
