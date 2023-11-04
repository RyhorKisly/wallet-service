package io.ylab.walletservice.core.exceptions;

public class SizeLengthException extends RuntimeException{
    public SizeLengthException() {
    }

    public SizeLengthException(String message) {
        super(message);
    }

    public SizeLengthException(String message, Throwable cause) {
        super(message, cause);
    }

    public SizeLengthException(Throwable cause) {
        super(cause);
    }

    public SizeLengthException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
