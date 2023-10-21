package io.ylab.walletsevice.dao.ds;

import io.ylab.walletservice.core.conf.PropertiesLoaderTest;
import io.ylab.walletservice.dao.ds.api.IConnectionWrapper;
import io.ylab.walletsevice.testcontainers.containers.PostgresTestContainer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnectionTest implements IConnectionWrapper {

    /**
     * Initialize class {@link Properties} for using properties from test.properties
     */
    private static final Properties CONF = PropertiesLoaderTest.loadProperties();

    static {
        try {
            Class.forName(CONF.getProperty("database.driver-class-name"));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Ошибка, драйвер для базы не найден", e);
        }
    }
    @Override
    public Connection getConnection() throws SQLException {
        Properties props = new Properties();

        String urlStart = CONF.getProperty("database.url.start");
        String port = PostgresTestContainer.getInstance().getMappedPort(5432).toString();
        String urlEnd = CONF.getProperty("database.url.end");
        String url = urlStart + port + urlEnd;

        props.setProperty("user", CONF.getProperty("database.user"));
        props.setProperty("password", CONF.getProperty("database.password"));
        return DriverManager.getConnection(url, props);
    }

    @Override
    public void close() {

    }
}
