package io.ylab.walletservice.service.factory;

import io.ylab.walletservice.dao.factory.TransactionDaoFactory;
import io.ylab.walletservice.service.TransactionService;
import io.ylab.walletservice.service.api.ITransactionService;

/**
 * Class implementing the singleton pattern for TransactionService
 */
public class TransactionServiceFactory {

    /**
     * Instance of the class
     */
    private static volatile ITransactionService instance;

    /**
     * private constructor to exclude the possibility of creating
     * an object of this class without using a method getInstance()
     */
    private TransactionServiceFactory() {

    }

    /**
     * Return single instance of TransactionService class
     * @return single instance of TransactionService class
     */
    public static ITransactionService getInstance() {
        if(instance == null) {
            synchronized (TransactionServiceFactory.class) {
                if(instance == null) {
                    instance = new TransactionService(
                            TransactionDaoFactory.getInstance(),
                            AccountServiceFactory.getInstance()
                    );
                }
            }
        }
        return instance;
    }
}
