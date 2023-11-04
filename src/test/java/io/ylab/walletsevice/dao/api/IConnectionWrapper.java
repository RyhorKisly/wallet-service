package io.ylab.walletsevice.dao.api;

import org.springframework.jdbc.datasource.embedded.DataSourceFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Interface with one method for getting connection (session) with a specific database
 */
public interface IConnectionWrapper extends DataSource {

    /**
     * Method for getting connection (session) with a specific database
     */
    Connection getConnection() throws SQLException;
}