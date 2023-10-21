package io.ylab.walletservice;

import io.ylab.walletservice.dao.ds.factory.ConnectionWrapperFactory;
import io.ylab.walletservice.dao.utils.LiquibaseManager;

/**
 * Class for execution code with liquibase
 * @author Grigoriy Kislyi
 */
public class MainLiquibase {

    /**
     * Here start point of the part of the program for execution code with liquibase
     * @param args command line values
     */
    public static void main(String[] args) {
        LiquibaseManager liquibaseManager = new LiquibaseManager(ConnectionWrapperFactory.getInstance());
        liquibaseManager.MigrateDb();
    }
}
