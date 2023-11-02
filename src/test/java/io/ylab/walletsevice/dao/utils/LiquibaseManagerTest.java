package io.ylab.walletsevice.dao.utils;

import io.ylab.walletservice.core.conf.PropertiesLoaderTest;
import io.ylab.walletservice.dao.ds.api.IConnectionWrapper;
import io.ylab.walletsevice.dao.utils.api.ILiquibaseManagerTest;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.RequiredArgsConstructor;

import java.sql.Connection;
import java.util.Properties;

@RequiredArgsConstructor
public class LiquibaseManagerTest implements ILiquibaseManagerTest {

    /**
     * Initialize class {@link Properties} for using properties from test.properties
     */
    private static final Properties CONF = PropertiesLoaderTest.loadProperties();


    private final IConnectionWrapper connection;

    public void migrateDbCreate() {
        try (Connection connection = this.connection.getConnection()){
            connection.createStatement().execute("CREATE SCHEMA IF NOT EXISTS liquibase");
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            database.setDefaultSchemaName(CONF.getProperty("database.default-schema"));
            database.setLiquibaseSchemaName(CONF.getProperty("database.liquibase-schema"));
            Liquibase liquibase = new Liquibase(CONF.getProperty("liquibase.changelog-file"), new ClassLoaderResourceAccessor(), database);
            liquibase.update();
            System.out.println("Создание и наполнение таблиц успешно выполнено");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void migrateDbDrop() {
        try (Connection connection = this.connection.getConnection()){
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            database.setDefaultSchemaName(CONF.getProperty("database.default-schema"));
            Liquibase liquibase = new Liquibase(CONF.getProperty("liquibase.drop-file"), new ClassLoaderResourceAccessor(), database);
            liquibase.update();
            System.out.println("Удаление данных из таблиц успешно выполнено");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
