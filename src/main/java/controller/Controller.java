package controller;

import spark.Request;
import spark.Response;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Controller {

    private static Controller instance = null;

    private Controller() {
    }

    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    public String userRegistration(Request req, Response res) {
        String userName = req.queryParams("userName");
        String userPassword = req.queryParams("userPassword");
        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("PostgreSQL JDBC Driver Registered!");
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/spark", "bans","pass");
            PreparedStatement insertUser = connection.prepareStatement("INSERT INTO users (name, password) VALUES (?, ?)");
            insertUser.setString(1, userName);
            insertUser.setString(2, userPassword);
            insertUser.executeQuery();
            insertUser.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            System.out.println(e.getMessage());
        }

        res.redirect("/");
        return "";
    }

    public String renderIndex(Request req, Response res) {
        List users = null;
        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("PostgreSQL JDBC Driver Registered!");
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/spark", "bans","pass");
            PreparedStatement selectUsers = connection.prepareStatement("SELECT * FROM users");
            ResultSet resultSet = selectUsers.executeQuery();
            users = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String userName = resultSet.getString("name");
                String userPassword = resultSet.getString("password");

                Map user = new HashMap();
                user.put("id", id);
                user.put("name", userName);
                user.put("password", userPassword);

                users.add(user);
            }
            selectUsers.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            System.out.println(e.getMessage());
        }
        Map<String, Object> model = new HashMap<>();
        model.put("users", users);
        Utils utils = Utils.getInstance();
        return utils.renderTemplate(model, "index");
    }

    public String jsonResponse(Request request, Response response) {
        Map<String, Object> model = new HashMap<>();

        Utils utils = Utils.getInstance();
        return utils.toJson(model);
    }
}
