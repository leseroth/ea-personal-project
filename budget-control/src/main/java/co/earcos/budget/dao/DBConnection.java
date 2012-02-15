package co.earcos.budget.dao;

import java.sql.Connection;
import java.sql.SQLException;
import org.h2.jdbcx.JdbcConnectionPool;

public class DBConnection {

    private static Connection conn;
    private static JdbcConnectionPool connPool;
    private static final String DB_URL = "jdbc:h2:file:D:/ErikArcos/Dropbox/Docs/db/budget;MODE=Oracle;AUTO_SERVER=TRUE";
    private static final String DB_USER = "earcos";
    private static final String DB_PASS = "earcos";

    public static Connection getConnection() {
        try {
            if (connPool == null) {
                connPool = JdbcConnectionPool.create(DB_URL, DB_USER, DB_PASS);
            }
            conn = connPool.getConnection();
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.toString());
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
            System.out.println("SQLException " + e.toString());
        }
    }
}
