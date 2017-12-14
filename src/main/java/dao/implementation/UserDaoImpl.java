package dao.implementation;

import dao.DatabaseManager;
import dao.UserDao;

import javax.sql.rowset.CachedRowSet;
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
    public boolean insert(String userName, String userPassword) {
        String[] parameters = {userName, userPassword};
        DatabaseManager databaseManager = DatabaseManager.getInstance();

        return databaseManager.queryModify("INSERT INTO users (name, password) VALUES (?, ?)", parameters);
    }

    @Override
    public List<Map<String, String>> select(String userName) throws NullPointerException {
        DatabaseManager databaseManager = DatabaseManager.getInstance();
        String[] parameters = {userName};
        CachedRowSet data = databaseManager.queryFetch("SELECT id, name, password FROM users WHERE name = ?", parameters);

        /* there must be one result because of the unique constraint of the database for username
        *  this constraint is checked in the UserController (userRegistration method) */
        try {
            List<Map<String, String>> resultArray = new ArrayList<>();
            while (data.next()) {
                Map<String, String> userMap = new HashMap<>();
                userMap.put("id", String.valueOf(data.getInt("id")));
                userMap.put("name", data.getString("name"));
                userMap.put("password", data.getString("password"));
                resultArray.add(userMap);
            }

            return resultArray;

        } catch (Exception exception) {
            System.out.println("ERROR: Failed to fetch selected user's data.");
            System.out.println(exception.getMessage());

            return new ArrayList<>();
        }
    }

}
