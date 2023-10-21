package io.ylab.walletsevice.dao.ds.factory;

import io.ylab.walletservice.dao.ds.api.IConnectionWrapper;
import io.ylab.walletsevice.dao.ds.DatabaseConnectionTest;

public class ConnectionWrapperFactoryTest {
    private static volatile IConnectionWrapper instance;

    private ConnectionWrapperFactoryTest() {
    }

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
