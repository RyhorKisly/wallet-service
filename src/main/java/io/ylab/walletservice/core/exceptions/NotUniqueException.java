package io.ylab.walletservice.core.exceptions;

import java.sql.SQLException;

/**
 * Exception should be used if added entity field exist in db and have to be unique
 */
public class NotUniqueException extends RuntimeException {
    public NotUniqueException() {
    }

    public NotUniqueException(String message) {
        super(message);
    }

    public NotUniqueException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotUniqueException(Throwable cause) {
        super(cause);
    }

    public NotUniqueException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
