package io.ylab.walletservice;

import io.ylab.walletservice.dao.ds.factory.ConnectionWrapperFactory;
import io.ylab.walletservice.dao.utils.LiquibaseManager;

public class MainLiquibase {
    public static void main(String[] args) {
        LiquibaseManager liquibaseManager = new LiquibaseManager(ConnectionWrapperFactory.getInstance());
        liquibaseManager.MigrateDb();
    }
}
