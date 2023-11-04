package io.ylab.walletservice.dao.utils;

import io.ylab.walletservice.aop.annotations.Loggable;
import io.ylab.walletservice.config.properties.DBProperties;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Properties;

@Component
@RequiredArgsConstructor
@Loggable
public class LiquibaseManager {

    /**
     * Initialize class {@link Properties} for using properties from application.yml
     */
    private final DBProperties properties;

    /**
     * DataSource for connecting to database
     */
    private final DataSource connection;

    public void MigrateDb() {
        try (Connection connection = this.connection.getConnection()){
            connection.createStatement().execute("CREATE SCHEMA IF NOT EXISTS liquibase");
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            database.setDefaultSchemaName(properties.getDefaultSchema());
            database.setLiquibaseSchemaName(properties.getLiquibaseSchema());
            Liquibase liquibase = new Liquibase(properties.getChangelogFile(), new ClassLoaderResourceAccessor(), database);
            liquibase.update();
            System.out.println("Миграция успешно выполнена");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
