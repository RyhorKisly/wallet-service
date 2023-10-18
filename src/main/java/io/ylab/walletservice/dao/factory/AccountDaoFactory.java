package io.ylab.walletservice.dao.factory;

import io.ylab.walletservice.dao.api.IAccountDao;
import io.ylab.walletservice.dao.AccountDao;

/**
 * Class implementing the singleton pattern for AccountDao
 */
public class AccountDaoFactory {

    /**
     * Instance of the class
     */
    private static volatile IAccountDao instance;

    /**
     * private constructor to exclude the possibility of creating
     * an object of this class without using a method getInstance()
     */
    private AccountDaoFactory() {

    }

    /**
     * Return single instance of AccountDao class
     * @return single instance of AccountDao class
     */
    public static IAccountDao getInstance() {
        if(instance == null) {
            synchronized (AccountDaoFactory.class) {
                if(instance == null) {
                    instance = new AccountDao();
                }
            }
        }
        return instance;
    }
}
