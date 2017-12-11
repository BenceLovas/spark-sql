package dao;

import java.sql.*;

public class DatabaseConnection {

    private static final String BASE_URL = "jdbc:postgresql://localhost:5432/";
    private static final String DATABASE = "spark";
    private static final String USERNAME = "bans";
    private static final String PASSWORD = "pass";
    private static final String URL = BASE_URL + DATABASE;

    private static DatabaseConnection instance = null;

    private DatabaseConnection() {}

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }

        return instance;
    }

    public static Connection getConnection() {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Failed to create the database connection.");
            System.out.println(e.getMessage());
        }

        return connection;
    }

    public static void closeConnection(PreparedStatement statement, Connection connection) {
        try {
            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Failed to close connection.");
            System.out.println(e.getMessage());
        }
    }

    public static void closeConnection(PreparedStatement statement, Connection connection, ResultSet resultSet) {
        try {
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Failed to close connection.");
            System.out.println(e.getMessage());
        }
    }


}
