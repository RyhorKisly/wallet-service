package io.ylab.walletservice.in.listeners;

import io.ylab.walletservice.dao.ds.factory.ConnectionWrapperFactory;
import io.ylab.walletservice.dao.utils.LiquibaseManager;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

/**
 * Listener for initializing Liquibase manager and execute code for creating and fulfilling db
 */
public class LiquibaseJakartaServletListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        LiquibaseManager liquibaseManager = new LiquibaseManager(ConnectionWrapperFactory.getInstance());
        liquibaseManager.MigrateDb();
    }
}
