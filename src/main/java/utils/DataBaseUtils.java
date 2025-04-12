package utils;

import java.sql.*;

public class DataBaseUtils {
    private static final String URL = "jdbc:mysql://localhost:3306/assetvn_net";
    private static final String USER = "root";
    private static final String PASSWORD = "kFviK&1466FT@Oct";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static ResultSet executeQuery(String query) throws SQLException {
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        return stmt.executeQuery(query);
    }

    public static int executeUpdate(String query) throws SQLException {
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        return stmt.executeUpdate(query);
    }

    public static void closeConnection(Connection connection) throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
