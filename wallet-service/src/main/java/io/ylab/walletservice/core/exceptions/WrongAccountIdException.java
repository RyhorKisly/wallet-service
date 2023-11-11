package io.ylab.walletservice.core.exceptions;

/**
 * Exception should be used when find account in db, account already exist or not exist
 */
public class WrongAccountIdException extends RuntimeException{
    public WrongAccountIdException() {
    }

    public WrongAccountIdException(String message) {
        super(message);
    }

    public WrongAccountIdException(String message, Throwable cause) {
        super(message, cause);
    }

    public WrongAccountIdException(Throwable cause) {
        super(cause);
    }

    public WrongAccountIdException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
