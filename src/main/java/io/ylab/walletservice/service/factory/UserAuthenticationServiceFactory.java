package io.ylab.walletservice.service.factory;

import io.ylab.walletservice.service.AuthenticationService;
import io.ylab.walletservice.service.api.IUserAuthenticationService;

/**
 * Class implementing the singleton pattern for {@link AuthenticationService}
 */
public class UserAuthenticationServiceFactory {

    /**
     * Instance of the class
     */
    private static volatile IUserAuthenticationService instance;

    /**
     * private constructor to exclude the possibility of creating
     * an object of this class without using a method getInstance()
     */
    private UserAuthenticationServiceFactory() {

    }

    /**
     * Return single instance of {@link AuthenticationService}
     * @return single instance of {@link AuthenticationService}
     */
    public static IUserAuthenticationService getInstance() {
        if(instance == null) {
            synchronized (UserAuthenticationServiceFactory.class) {
                if(instance == null) {
                    instance = new AuthenticationService(
                            UserServiceFactory.getInstance(),
                            AccountServiceFactory.getInstance(),
                            AuditServiceFactory.getInstance()
                    );
                }
            }
        }
        return instance;
    }

}
