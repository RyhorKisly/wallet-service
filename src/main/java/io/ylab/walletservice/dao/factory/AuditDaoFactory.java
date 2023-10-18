package io.ylab.walletservice.dao.factory;

import io.ylab.walletservice.dao.api.IAuditDao;
import io.ylab.walletservice.dao.AuditDao;

/**
 * Class implementing the singleton pattern for AuditDao
 */
public class AuditDaoFactory {

    /**
     * Instance of the class
     */
    private static volatile IAuditDao instance;

    /**
     * private constructor to exclude the possibility of creating
     * an object of this class without using a method getInstance()
     */
    private AuditDaoFactory() {

    }

    /**
     * Return single instance of AuditDao class
     * @return single instance of AuditDao class
     */
    public static IAuditDao getInstance() {
        if(instance == null) {
            synchronized (AuditDaoFactory.class) {
                if(instance == null) {
                    instance = new AuditDao();
                }
            }
        }
        return instance;
    }
}
