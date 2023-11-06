package io.ylab.walletservice.core.exceptions;

/**
 * Exception should be used when dto parsed from json has nullable fields
 */
public class BlankBodyFieldsException extends RuntimeException {
    public BlankBodyFieldsException() {
    }

    public BlankBodyFieldsException(String message) {
        super(message);
    }

    public BlankBodyFieldsException(String message, Throwable cause) {
        super(message, cause);
    }

    public BlankBodyFieldsException(Throwable cause) {
        super(cause);
    }

    public BlankBodyFieldsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
