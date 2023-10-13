package io.ylab.walletservice.service.factory;

import io.ylab.walletservice.dao.memory.factory.AuditDaoFactory;
import io.ylab.walletservice.service.AuditService;
import io.ylab.walletservice.service.api.IAuditService;

/**
 * Class implementing the singleton pattern for AuditService
 */
public class AuditServiceFactory {

    /**
     * Instance of the class
     */
    private static volatile IAuditService instance;

    /**
     * private constructor to exclude the possibility of creating
     * an object of this class without using a method getInstance()
     */
    private AuditServiceFactory() {

    }

    /**
     * Return single instance of AuditService class
     * @return single instance of AuditService class
     */
    public static IAuditService getInstance() {
        if(instance == null) {
            synchronized (AuditServiceFactory.class) {
                if(instance == null) {
                    instance = new AuditService(AuditDaoFactory.getInstance());
                }
            }
        }
        return instance;
    }
}
