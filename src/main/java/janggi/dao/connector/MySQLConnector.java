package janggi.dao.connector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MySQLConnector implements DBConnector {

    private static final String SERVER = "localhost:13306";
    private static final String DATABASE = "janggi";
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "password";

    private final Connection connection;

    private MySQLConnector(Connection connection) {
        this.connection = connection;
    }

    public static MySQLConnector createConnection() {
        try {
            Connection connection = DriverManager.getConnection(
                "jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION, USERNAME, PASSWORD);
            return new MySQLConnector(connection);
        } catch (SQLException e) {
            throw new RuntimeException("DB 연결 오류:" + e);
        }
    }

    @Override
    public PreparedStatement handleQuery(String query) {
        try {
            return connection.prepareStatement(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Connection 종료 오류:" + e);
        }
    }
}
