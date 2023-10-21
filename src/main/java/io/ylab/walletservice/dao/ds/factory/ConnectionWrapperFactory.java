package io.ylab.walletservice.dao.ds.factory;

import io.ylab.walletservice.dao.ds.DatabaseConnection;
import io.ylab.walletservice.dao.ds.api.IConnectionWrapper;

/**
 * This class implement pattern: singleton for {@link DatabaseConnection}
 */
public class ConnectionWrapperFactory {

    /**
     * Instance of the class
     */
    private static volatile IConnectionWrapper instance;

    /**
     * private constructor to exclude the possibility of creating
     * an object of this class without using a method getInstance()
     */
    private ConnectionWrapperFactory() {
    }

    /**
     * Return single instance of {@link DatabaseConnection} class
     * @return single instance of {@link DatabaseConnection} class
     */
    public static IConnectionWrapper getInstance() {
        if(instance==null){
            synchronized (ConnectionWrapperFactory.class){
                if(instance==null){
                    instance = new DatabaseConnection();
                }
            }
        }
        return instance;
    }
}
