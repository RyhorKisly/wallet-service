package io.ylab.walletservice.core.exceptions;

/**
 * Exception should be used if not exist user in db
 */
public class NotExistUserException extends RuntimeException{
    public NotExistUserException() {
    }

    public NotExistUserException(String message) {
        super(message);
    }

    public NotExistUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotExistUserException(Throwable cause) {
        super(cause);
    }

    public NotExistUserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
