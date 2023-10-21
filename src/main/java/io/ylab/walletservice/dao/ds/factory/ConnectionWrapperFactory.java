package io.ylab.walletservice.dao.ds.factory;

import io.ylab.walletservice.dao.ds.DatabaseConnection;
import io.ylab.walletservice.dao.ds.api.IConnectionWrapper;

public class ConnectionWrapperFactory {
    private static volatile IConnectionWrapper instance;

    private ConnectionWrapperFactory() {
    }

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
