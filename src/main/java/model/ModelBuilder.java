package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModelBuilder {

    private static ModelBuilder instance = null;

    private ModelBuilder() {}

    public static ModelBuilder getInstance() {
        if (instance == null) {
            instance = new ModelBuilder();
        }

        return instance;
    }

    public Object usersModel(ResultSet resultSet) {
        List<Map> users = new ArrayList<>();

        try {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String userName = resultSet.getString("name");
                String userPassword = resultSet.getString("password");

                Map<String, Object> user = new HashMap<>();
                user.put("id", id);
                user.put("name", userName);
                user.put("password", userPassword);

                users.add(user);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        Map<String, Object> model = new HashMap<>();
        model.put("users", users);

        return model;
    }

    public Object getUserPassword(ResultSet resultSet) {
        try {
            if (!resultSet.next()) {
                System.out.println("not valid username");
                return null;
            } else {
                return resultSet.getString("password");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

}
