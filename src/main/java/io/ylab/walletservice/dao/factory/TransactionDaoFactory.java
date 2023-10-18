package io.ylab.walletservice.dao.factory;

import io.ylab.walletservice.dao.api.ITransactionDao;
import io.ylab.walletservice.dao.TransactionDao;

/**
 * Class implementing the singleton pattern for TransactionDao
 */
public class TransactionDaoFactory {

    /**
     * Instance of the class
     */
    private static volatile ITransactionDao instance;

    /**
     * private constructor to exclude the possibility of creating
     * an object of this class without using a method getInstance()
     */
    private TransactionDaoFactory() {

    }

    /**
     * Return single instance of TransactionDao class
     * @return single instance of TransactionDao class
     */
    public static ITransactionDao getInstance() {
        if(instance == null) {
            synchronized (TransactionDaoFactory.class) {
                if(instance == null) {
                    instance = new TransactionDao();
                }
            }
        }
        return instance;
    }
}
