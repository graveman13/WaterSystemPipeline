package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
    private static final String JDBC_DRIVER = PropertiesUtil.getPropertiesByKey("jdbc_driver");
    private static final String DATABASE_URL = PropertiesUtil.getPropertiesByKey("database_url");
    private static final String LOGIN = PropertiesUtil.getPropertiesByKey("login");
    private static final String PASSWORD = PropertiesUtil.getPropertiesByKey("password");

    static {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't find database driver", e);
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(DATABASE_URL, LOGIN, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Can't establish the connection to DB", e);
        }
    }
}
