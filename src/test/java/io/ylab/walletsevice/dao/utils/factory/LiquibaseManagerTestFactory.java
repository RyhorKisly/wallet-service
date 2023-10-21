package io.ylab.walletsevice.dao.utils.factory;

import io.ylab.walletsevice.dao.ds.factory.ConnectionWrapperFactoryTest;
import io.ylab.walletsevice.dao.utils.LiquibaseManagerTest;
import io.ylab.walletsevice.dao.utils.api.ILiquibaseManagerTest;

public class LiquibaseManagerTestFactory {
    private static volatile ILiquibaseManagerTest instance;

    private LiquibaseManagerTestFactory() {
    }

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