package io.ylab.walletservice.utils;

import io.ylab.walletservice.config.properties.AppProperties;
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

@RequiredArgsConstructor
@Component
public class LiquibaseManagerTest {

    /**
     * Initialize class {@link Properties} for using properties from test.properties
     */
    private final AppProperties prop;

    private final DataSource dataSource;

    public void migrateDbDrop() {
        try (Connection connection = this.dataSource.getConnection()){
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            database.setDefaultSchemaName(prop.getDefaultSchema());
            Liquibase liquibase = new Liquibase(prop.getChangeLogDrop(), new ClassLoaderResourceAccessor(), database);
            liquibase.update();
            System.out.println("Удаление данных из таблиц успешно выполнено");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
