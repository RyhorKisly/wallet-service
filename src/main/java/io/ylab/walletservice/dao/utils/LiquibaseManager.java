package io.ylab.walletservice.dao.utils;

import io.ylab.walletservice.core.utils.PropertiesLoader;
import io.ylab.walletservice.dao.ds.api.IConnectionWrapper;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.RequiredArgsConstructor;

import java.sql.Connection;
import java.util.Properties;

@RequiredArgsConstructor
public class LiquibaseManager {
    private static final Properties CONF = PropertiesLoader.loadProperties();
    private final IConnectionWrapper connection;

    public void MigrateDb() {
        try (Connection connection = this.connection.getConnection()){
            connection.createStatement().execute("CREATE SCHEMA IF NOT EXISTS liquibase");
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            database.setDefaultSchemaName(CONF.getProperty("database.default-schema"));
            database.setLiquibaseSchemaName(CONF.getProperty("database.liquibase-schema"));
            Liquibase liquibase = new Liquibase(CONF.getProperty("liquibase.changelog-file"), new ClassLoaderResourceAccessor(), database);
            liquibase.update();
            System.out.println("Миграция успешно выполнена");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
