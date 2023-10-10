package io.ylab.walletservice.core.enums;

/**
 * Class to list the main types of transactions
 */
public enum Operation {
    /**
     * Transaction of finds write-off
     */
    DEBIT,
    /**
     * Transaction of finds write-on
     */
    CREDIT
}
