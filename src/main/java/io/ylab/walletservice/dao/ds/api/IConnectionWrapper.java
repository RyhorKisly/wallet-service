package io.ylab.walletservice.dao.ds.api;

import java.sql.Connection;
import java.sql.SQLException;

public interface IConnectionWrapper extends AutoCloseable{
    Connection getConnection() throws SQLException;
}