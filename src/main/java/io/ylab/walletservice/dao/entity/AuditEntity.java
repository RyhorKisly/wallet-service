package io.ylab.walletservice.dao.entity;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Stored business logic object. Used to save, retrieve and update state to a storage location
 */
public class AuditEntity {

    /**
     * Used like ID of an audit. Should be unique
     */
    private UUID uuid;

    /**
     * Class instance creation time
     */
    private LocalDateTime dtCreate;

    /**
     * Identifies that the account belongs to a specific user.
     */
    private String userLogin;

    /**
     * Message where we write specific action performed by the user.
     */
    private String text;

    /**
     * Give you opportunity construct entity without initial params.
     * This is a required condition for using entity with ORM
     */
    public AuditEntity() {
    }

    /**
     * Constructs entity with the specified params.
     * @param uuid the value used to set the {@code uuid} entity field
     * @param dtCreate the value used to set the {@code dtCreate} entity field
     * @param userLogin the value used to set the {@code userLogin} entity field
     * @param text the value used to set the {@code text} entity field
     */
    public AuditEntity(
            UUID uuid,
            LocalDateTime dtCreate,
            String userLogin,
            String text
    ) {
        this.uuid = uuid;
        this.dtCreate = dtCreate;
        this.userLogin = userLogin;
        this.text = text;
    }

    /**
     * Returns transaction ID.
     * @return transaction ID with UUID type
     */
    public UUID getUuid() {
        return uuid;
    }

    /**
     * Sets the values for the field {@code uuid}
     * @param uuid the value used to set the {@code uuid} entity field.
     */
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    /**
     * Returns date of creation.
     * @return date of creation
     */
    public LocalDateTime getDtCreate() {
        return dtCreate;
    }

    /**
     * Sets the values for the field {@code dtCreate}
     * @param dtCreate the value used to set the {@code dtCreate} entity field.
     */
    public void setDtCreate(LocalDateTime dtCreate) {
        this.dtCreate = dtCreate;
    }

    /**
     * Returns current login of user.
     * @return current login of user
     */
    public String getUserLogin() {
        return userLogin;
    }

    /**
     * Sets the values for the field {@code userLogin}
     * @param userLogin the value used to set the {@code userLogin} entity field.
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
     * @param text the value used to set the {@code text} entity field.
     */
    public void setText(String text) {
        this.text = text;
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
        AuditEntity that = (AuditEntity) o;
        return Objects.equals(uuid, that.uuid) && Objects.equals(dtCreate, that.dtCreate) && Objects.equals(userLogin, that.userLogin) && Objects.equals(text, that.text);
    }

    /**
     * Override for specifying hashCode according to the fields of the class
     * @return hashCode according to the fields of the class
     */
    @Override
    public int hashCode() {
        return Objects.hash(uuid, dtCreate, userLogin, text);
    }
}
