package co.earcos.budget.dao;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.h2.jdbcx.JdbcConnectionPool;

import co.earcos.budget.util.Util;

public class DBConnection {

    private static Connection conn;
    private static JdbcConnectionPool connPool;
    private static Log log = LogFactory.getLog(DBConnection.class);

    public static Connection getConnection() {
        try {
            String dbUrl = Util.getProperty("h2.database.url");
            String dbUser = Util.getProperty("h2.database.user");
            String dbPass = Util.getProperty("h2.database.pass");

            if (connPool == null) {
                connPool = JdbcConnectionPool.create(dbUrl, dbUser, dbPass);
            }
            conn = connPool.getConnection();
        } catch (SQLException e) {
            log.error("SQL Exception", e);
        }
        return conn;
    }

    public static void closeConnection() {
        try {
            if (conn != null) {
                conn.close();
            }
            conn = null;
        } catch (SQLException e) {
            log.error("SQL Exception", e);
        }
    }
}