package io.ylab.walletservice.core.dto;

import io.ylab.walletservice.core.enums.UserRole;

/**
 * Transfer user data with registration for transfer to a storage location
 */
public class UserCreateDTO {

    /**
     * Identifies that the account belongs to a specific user. Used for log in.
     */
    private String login;

    /**
     * Enum type for description the role of user
     */
    private UserRole role;

    /**
     * Used to protect data. Used for log in.
     */
    private String password;

    /**
     * Give you opportunity construct dto without initial params.
     * It can be useful in case where you want check some params for null before adding in to the object
     */
    public UserCreateDTO() {

    }

    /**
     *  Constructs dto with the specified params.
     * @param login the value used to set the {@code login} dto field
     * @param role the value used to set the {@code role} dto field
     * @param password the value used to set the {@code password} dto field
     */
    public UserCreateDTO(
            String login,
            UserRole role,
            String password
    ) {
        this.login = login;
        this.role = role;
        this.password = password;
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

    /**
     * Returns current role of user.
     * @return current role of user
     */
    public UserRole getRole() {
        return role;
    }

    /**
     * Sets the values for the field {@code role}
     * @param role the value used to set the {@code role} dto field.
     */
    public void setRole(UserRole role) {
        this.role = role;
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
     * @param password the value used to set the {@code password} dto field.
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
