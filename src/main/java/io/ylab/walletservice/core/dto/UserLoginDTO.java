package io.ylab.walletservice.core.dto;

/**
 * Transfer user data with authentication for transfer to a storage location
 */
public class UserLoginDTO {

    /**
     * Identifies that the account belongs to a specific user. Used for log in.
     */
    private String login;

    /**
     * Used to protect data. Used for log in.
     */
    private String password;

    /**
     * Give you opportunity construct dto without initial params.
     * It can be useful in case where you want check some params for null before adding in to the object
     */
    public UserLoginDTO() {
    }

    /**
     *  Constructs dto with the specified params.
     * @param login the value used to set the {@code login} dto field
     * @param password the value used to set the {@code password} dto field
     */
    public UserLoginDTO(String login, String password) {
        this.login = login;
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
