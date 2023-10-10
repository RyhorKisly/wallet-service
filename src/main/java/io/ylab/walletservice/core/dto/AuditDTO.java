package io.ylab.walletservice.core.dto;

/**
 * Transfer account data for transfer to a storage location
 */
public class AuditDTO {

    /**
     * Identifies that the account belongs to a specific user.
     */
    private String userLogin;

    /**
     * Message where we write specific action performed by the user.
     */
    private String text;

    /**
     * Give you opportunity construct dto without initial params.
     * It can be useful in case where you want check some params for null before adding in to the object
     */
    public AuditDTO() {
    }

    /**
     * Constructs dto with the specified params.
     * @param userLogin the value used to set the {@code userLogin} dto field
     * @param text the value used to set the {@code text} dto field
     */
    public AuditDTO(
            String userLogin,
            String text
    ) {
        this.userLogin = userLogin;
        this.text = text;
    }

    /**
     * Returns user login.
     * @return user login
     */
    public String getUserLogin() {
        return userLogin;
    }

    /**
     * Sets the values for the field {@code userLogin}
     * @param userLogin the value used to set the {@code userLogin} dto field.
     */
    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    /**
     * Returns text message where we write specific action performed by the user.
     * @return text message where we write specific action performed by the user
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the values for the field {@code text}
     * @param text the value used to set the {@code text} dto field.
     */
    public void setText(String text) {
        this.text = text;
    }
}
