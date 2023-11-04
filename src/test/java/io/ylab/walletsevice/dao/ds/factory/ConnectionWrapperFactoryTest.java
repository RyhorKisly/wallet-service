package io.ylab.walletsevice.dao.ds.factory;

import io.ylab.walletsevice.dao.api.IConnectionWrapper;
import io.ylab.walletsevice.dao.ds.DatabaseConnectionTest;

/**
 * This class implement pattern: singleton for {@link DatabaseConnectionTest}
 */
public class ConnectionWrapperFactoryTest {

    /**
     * Instance of the class
     */
    private static volatile IConnectionWrapper instance;

    /**
     * private constructor to exclude the possibility of creating
     * an object of this class without using a method getInstance()
     */
    private ConnectionWrapperFactoryTest() {
    }

    /**
     * Return single instance of {@link DatabaseConnectionTest} class
     * @return single instance of {@link DatabaseConnectionTest} class
     */
    public static IConnectionWrapper getInstance() {
        if(instance==null){
            synchronized (ConnectionWrapperFactoryTest.class){
                if(instance==null){
                    instance = new DatabaseConnectionTest();
                }
            }
        }
        return instance;
    }
}
