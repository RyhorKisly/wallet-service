package io.ylab.walletservice.dao.ds.api;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Interface with one method for getting connection (session) with a specific database
 */
public interface IConnectionWrapper extends AutoCloseable{

    /**
     * Method for getting connection (session) with a specific database
     */
    Connection getConnection() throws SQLException;
}