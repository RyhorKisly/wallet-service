package io.ylab.walletservice.service.factory;

import io.ylab.walletservice.dao.memory.factory.AccountDaoFactory;
import io.ylab.walletservice.service.AccountService;
import io.ylab.walletservice.service.api.IAccountService;

/**
 * Class implementing the singleton pattern for AccountService
 */
public class AccountServiceFactory {

    /**
     * Instance of the class
     */
    private static volatile IAccountService instance;

    /**
     * private constructor to exclude the possibility of creating
     * an object of this class without using a method getInstance()
     */
    private AccountServiceFactory() {

    }

    /**
     * Return single instance of AccountService class
     * @return single instance of AccountService class
     */
    public static IAccountService getInstance() {
        if(instance == null) {
            synchronized (AccountServiceFactory.class) {
                if(instance == null) {
                    instance = new AccountService(
                            AccountDaoFactory.getInstance(),
                            AuditServiceFactory.getInstance()
                    );
                }
            }
        }
        return instance;
    }
}
