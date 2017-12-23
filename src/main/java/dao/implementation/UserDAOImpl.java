package dao.implementation;

import dao.DatabaseManager;
import dao.UserDAO;
import model.User;

import javax.sql.rowset.CachedRowSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDAOImpl implements UserDAO {

    private static UserDAOImpl instance = null;
    private static DatabaseManager databaseManager = DatabaseManager.getInstance();

    private UserDAOImpl() {}

    public static UserDAOImpl getInstance() {
        if (instance == null) {
            instance = new UserDAOImpl();
        }

        return instance;
    }

    @Override
    public boolean insert(User user) {
        String[] parameters = {user.getName(), user.getPassword()};

        return databaseManager.queryModify("INSERT INTO users (name, password) VALUES (?, ?)", parameters);
    }

    @Override
    public List<User> select(User user) throws NullPointerException {
        String[] parameters = {user.getName()};
        CachedRowSet data = databaseManager.queryFetch("SELECT id, name, password FROM users WHERE name = ?", parameters);

        /* there must be one result because of the unique constraint of the database for username
        *  this constraint is checked in the UserController (userRegistration method) */
        try {
            List<User> users = new ArrayList<>();
            while (data.next()) {
                User userFromDB = new User(data.getInt("id"),
                                           data.getString("name"),
                                           data.getString("password"));
                users.add(userFromDB);
            }

            return users;

        } catch (Exception exception) {
            System.out.println("ERROR: Failed to fetch selected user's data.");
            System.out.println(exception.getMessage());

            return new ArrayList<>();
        }
    }

}
