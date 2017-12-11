package dao.implementation;

import dao.DatabaseConnection;
import dao.UserDao;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDaoImpl implements UserDao {

    private static UserDaoImpl instance = null;

    private UserDaoImpl() {}

    public static UserDaoImpl getInstance() {
        if (instance == null) {
            instance = new UserDaoImpl();
        }

        return instance;
    }

    @Override
    public Map<String, String> insert(String userName, String userPassword) {
        String[] parameters = {userName, userPassword};
        DatabaseConnection databaseConnection = DatabaseConnection.getInstance();
        Connection connection = databaseConnection.getConnection();
        PreparedStatement statement = null;
        Map<String, String> mapToReturn = new HashMap<>();

        try {
            statement = connection.prepareStatement("INSERT INTO users (name, password) VALUES (?, ?)");
            for (int i = 0; i < parameters.length; i++) {
                statement.setString(i + 1, parameters[i]);
            }
            statement.executeUpdate();
            mapToReturn.put("valid", "true");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            mapToReturn.put("valid", "false");
        }

        databaseConnection.closeConnection(statement, connection);
        return mapToReturn;
    }

    @Override
    public List<Map<String, String>> select(String userName) {
        DatabaseConnection databaseConnection = DatabaseConnection.getInstance();
        Connection connection = databaseConnection.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Map<String, String>> resultArray = new ArrayList<>();

        try {
            statement = connection.prepareStatement("SELECT id, name, password FROM users WHERE name = ?");
            statement.setString(1, userName);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Map<String, String> userMap = new HashMap<>();
                userMap.put("id", String.valueOf(resultSet.getInt("id")));
                userMap.put("name", resultSet.getString("name"));
                userMap.put("password", resultSet.getString("password"));
                resultArray.add(userMap);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        databaseConnection.closeConnection(statement, connection, resultSet);
        return resultArray;
    }
}
