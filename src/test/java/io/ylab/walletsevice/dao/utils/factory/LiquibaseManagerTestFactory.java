package io.ylab.walletsevice.dao.utils.factory;

import io.ylab.walletsevice.dao.ds.factory.ConnectionWrapperFactoryTest;
import io.ylab.walletsevice.dao.utils.LiquibaseManagerTest;
import io.ylab.walletsevice.dao.utils.api.ILiquibaseManagerTest;

/**
 * This class implement pattern: singleton for {@link LiquibaseManagerTest}
 */
public class LiquibaseManagerTestFactory {

    /**
     * Instance of the class
     */
    private static volatile ILiquibaseManagerTest instance;

    /**
     * private constructor to exclude the possibility of creating
     * an object of this class without using a method getInstance()
     */
    private LiquibaseManagerTestFactory() {
    }

    /**
     * Return single instance of {@link LiquibaseManagerTest} class
     * @return single instance of {@link LiquibaseManagerTest} class
     */
    public static ILiquibaseManagerTest getInstance() {
        if(instance==null){
            synchronized (ILiquibaseManagerTest.class){
                if(instance==null){
                    instance = new LiquibaseManagerTest(ConnectionWrapperFactoryTest.getInstance());
                }
            }
        }
        return instance;
    }
}