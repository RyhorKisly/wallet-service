package io.ylab.walletservice.in.listeners;

import io.ylab.walletservice.dao.utils.LiquibaseManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Listener for initializing Liquibase manager and execute code for creating and fulfilling db
 */
@Component
@RequiredArgsConstructor
public class LiquibaseListener {

    /**
     * Manager for migrating data in database
     */
    private final LiquibaseManager liquibaseManager;

    /**
     * Call method for migrating data in database
     */
    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        liquibaseManager.MigrateDb();
    }

}
