package io.ylab.walletservice.dao.ds;

import io.ylab.walletservice.core.utils.PropertiesLoader;
import io.ylab.walletservice.dao.ds.api.IConnectionWrapper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection implements IConnectionWrapper {

    private static final Properties CONF = PropertiesLoader.loadProperties();

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

        String url = CONF.getProperty("database.url");
        props.setProperty("user", CONF.getProperty("database.user"));
        props.setProperty("password", CONF.getProperty("database.password"));
        return DriverManager.getConnection(url, props);
    }

    @Override
    public void close() throws Exception {

    }
}
