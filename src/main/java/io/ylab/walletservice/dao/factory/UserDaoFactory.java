package io.ylab.walletservice.dao.factory;

import io.ylab.walletservice.dao.api.IUserDao;
import io.ylab.walletservice.dao.UserDao;

/**
 * Class implementing the singleton pattern for UserDao
 */
public class UserDaoFactory {

    /**
     * Instance of the class
     */
    private static volatile IUserDao instance;

    /**
     * private constructor to exclude the possibility of creating
     * an object of this class without using a method getInstance()
     */
    private UserDaoFactory() {

    }

    /**
     * Return single instance of UserDao class
     * @return single instance of UserDao class
     */
    public static IUserDao getInstance() {
        if(instance == null) {
            synchronized (UserDaoFactory.class) {
                if(instance == null) {
                    instance = new UserDao();
                }
            }
        }
        return instance;
    }
}
