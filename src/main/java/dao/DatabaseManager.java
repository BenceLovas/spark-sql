package dao;

import com.sun.rowset.CachedRowSetImpl;

import javax.sql.rowset.CachedRowSet;
import java.sql.*;

public class DatabaseManager {

    private static final String BASE_URL = "jdbc:postgresql://localhost:5432/";
    private static final String DATABASE = "spark";
    private static final String USERNAME = "bans";
    private static final String PASSWORD = "pass";
    private static final String URL = BASE_URL + DATABASE;

    private static DatabaseManager instance = null;

    private DatabaseManager() {}

    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }

        return instance;
    }

    private static Connection getConnection() {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException exception) {
            System.out.println("ERROR: Failed to connect to database.");
            System.out.println(exception.getMessage());
        }

        return connection;
    }

    private static PreparedStatement getPreparedStatement (String query, String[] queryParameters, Connection connection) {
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(query);
            for (int i = 0; i < queryParameters.length; i++) {
                statement.setString(i + 1, queryParameters[i]);
            }

        } catch (SQLException exception) {
            System.out.println("ERROR: Failed to create PreparedStatement.");
            System.out.println(exception.getMessage());
        }

        return statement;
    }

    public static CachedRowSet queryFetch (String query, String[] queryParameters) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            statement = getPreparedStatement(query, queryParameters, connection);
            resultSet = statement.executeQuery();
            CachedRowSet cachedRowSet = new CachedRowSetImpl();
            cachedRowSet.populate(resultSet);

            return cachedRowSet;

        } catch (SQLException exception){
            System.out.println("ERROR: Failed to get ResultSet.");
            System.out.println(exception.getMessage());

            return null;

        } finally {
            if (resultSet != null) { try { resultSet.close(); } catch (Exception e) { /* ignored */ } }
            if (statement != null) { try { statement.close(); } catch (Exception e) { /* ignored */ } }
            if (connection != null) { try { connection.close(); } catch (Exception e) { /* ignored */ } }
        }
    }

    public static boolean queryModify (String query, String[] queryParameters) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = getConnection();
            statement = getPreparedStatement(query, queryParameters, connection);
            statement.executeUpdate();

            System.out.println(query.split(" ")[0] + " done.");
            return true;

        } catch (SQLException exception) {
            System.out.println("ERROR: Failed to modify database data.");
            return false;

        } finally {
            if (statement != null) { try { statement.close(); } catch (Exception e) { /* ignored */ } }
            if (connection != null) { try { connection.close(); } catch (Exception e) { /* ignored */ } }
        }
    }

//    public static void closeConnection(PreparedStatement statement, Connection connection) {
//        try {
//            statement.close();
//            connection.close();
//        } catch (SQLException e) {
//            System.out.println("Failed to close connection.");
//            System.out.println(e.getMessage());
//        }
//    }
//
//    public static void closeConnection(PreparedStatement statement, Connection connection, ResultSet resultSet) {
//        try {
//            resultSet.close();
//            statement.close();
//            connection.close();
//        } catch (SQLException e) {
//            System.out.println("Failed to close connection.");
//            System.out.println(e.getMessage());
//        }
//    }


}
