package io.ylab.walletservice.dao.utils;

import io.ylab.walletservice.core.utils.PropertiesLoader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnectionFactory {

    private static final Properties CONF = PropertiesLoader.loadProperties();

    static {
        try {
            Class.forName(CONF.getProperty("database.driver-class-name"));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Ошибка, драйвер для базы не найден", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        Properties props = new Properties();

        String url = CONF.getProperty("database.url");
        props.setProperty("user", CONF.getProperty("database.user"));
        props.setProperty("password", CONF.getProperty("database.password"));
        return DriverManager.getConnection(url, props);
    }
}
