package io.ylab.walletsevice.dao.ds;

import io.ylab.walletservice.config.properties.PropertiesLoaderTest;
import io.ylab.walletsevice.dao.api.IConnectionWrapper;
import io.ylab.walletsevice.testcontainers.containers.PostgresTestContainer;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

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
    public Connection getConnection(String username, String password) throws SQLException {
        return null;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {

    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {

    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }
}
