package io.ylab.walletservice.core.dto;

import io.ylab.walletservice.core.enums.UserRole;

import java.util.UUID;

/**
 * Transfer user data for transfer to a storage location
 */
public class UserDTO {

    /**
     * Used like ID of transaction in order to save in storage place.
     * should be uniq and not be empty
     */
    private UUID uuid;

    /**
     * Identifies that the account belongs to a specific user. Used for log in.
     */
    private String login;

    /**
     * Enum type for description the role of user
     */
    private UserRole userRole;

    /**
     * Give you opportunity construct dto without initial params.
     * It can be useful in case where you want check some params for null before adding in to the object
     */
    public UserDTO() {
    }

    /**
     *  Constructs dto with the specified params.
     * @param uuid the value used to set the {@code uuid} dto field
     * @param login the value used to set the {@code login} dto field
     * @param userRole the value used to set the {@code userRole} dto field
     */
    public UserDTO(UUID uuid, String login, UserRole userRole) {
        this.uuid = uuid;
        this.login = login;
        this.userRole = userRole;
    }

    /**
     * Returns current user ID.
     * @return current user ID
     */
    public UUID getUuid() {
        return uuid;
    }

    /**
     * Sets the values for the field {@code uuid}
     * @param uuid the value used to set the {@code uuid} dto field.
     * @throws NullPointerException if param is null
     */
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
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
     * Returns current userRole.
     * @return current userRole
     */
    public UserRole getUserRole() {
        return userRole;
    }

    /**
     * Sets the values for the field {@code userRole}
     * @param userRole the value used to set the {@code userRole} dto field.
     */
    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }
}
