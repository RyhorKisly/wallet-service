package io.ylab.walletservice.service.factory;

import io.ylab.walletservice.dao.memory.factory.UserDaoFactory;
import io.ylab.walletservice.service.UserService;
import io.ylab.walletservice.service.api.IUserService;

/**
 * Class implementing the singleton pattern for UserService
 */
public class UserServiceFactory {

    /**
     * Instance of the class
     */
    private static volatile IUserService instance;

    /**
     * private constructor to exclude the possibility of creating
     * an object of this class without using a method getInstance()
     */
    private UserServiceFactory() {

    }

    /**
     * Return single instance of UserService class
     * @return single instance of UserService class
     */
    public static IUserService getInstance() {
        if(instance == null) {
            synchronized (UserServiceFactory.class) {
                if(instance == null) {
                    instance = new UserService(UserDaoFactory.getInstance());
                }
            }
        }
        return instance;
    }
}
