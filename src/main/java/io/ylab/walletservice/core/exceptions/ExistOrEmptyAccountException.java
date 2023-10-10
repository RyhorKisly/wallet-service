package io.ylab.walletservice.core.exceptions;

/**
 * This class is an extension of exception {@link IllegalArgumentException}
 */
public class ExistOrEmptyAccountException extends IllegalArgumentException{

    /**
     * Аor the purpose of calling the default target constructor
     */
    public ExistOrEmptyAccountException() {
        super();
    }
}
