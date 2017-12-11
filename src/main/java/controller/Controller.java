package controller;

import model.DatabaseConnection;
import model.ModelBuilder;
import org.mindrot.jbcrypt.BCrypt;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class Controller {

    private static Controller instance = null;

    private Controller() {
        // prevent instantiation
    }

    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    public String userLogin(Request req, Response res) {
        String userName = req.queryParams("userName");
        String userPassword = req.queryParams("userPassword");

        DatabaseConnection databaseConnection = DatabaseConnection.getInstance();
        ModelBuilder modelBuilder = ModelBuilder.getInstance();
        String[] parameters = {userName};
        Object passwordInDatabase = databaseConnection.fetchDatabase("SELECT id, password FROM users WHERE name = ?", modelBuilder::getUserPassword, parameters);

        if (BCrypt.checkpw(userPassword, (String) passwordInDatabase)) {
            System.out.println("password matches");
            // set session with userid
        } else {
            System.out.println("password not matches");
        }

        res.redirect("/");
        return "";
    }

    public String userRegistration(Request req, Response res) {
        String userName = req.queryParams("userName");
        String userPassword = req.queryParams("userPassword");
        userPassword = BCrypt.hashpw(userPassword, BCrypt.gensalt());
        String[] parameters = {userName, userPassword};

        DatabaseConnection databaseConnection = DatabaseConnection.getInstance();
        databaseConnection.modifyDatabase("INSERT INTO users (name, password) VALUES (?, ?)", parameters);

        res.redirect("/");
        return "";
    }

    public String renderIndex(Request req, Response res) {
        ModelBuilder modelBuilder = ModelBuilder.getInstance();
        DatabaseConnection databaseConnection = DatabaseConnection.getInstance();
        Object model = databaseConnection.fetchDatabase("SELECT * FROM users", modelBuilder::usersModel);
        Utils utils = Utils.getInstance();

        return utils.renderTemplate((Map) model, "login");
    }

    public String jsonResponse(Request request, Response response) {
        Map<String, Object> model = new HashMap<>();

        Utils utils = Utils.getInstance();
        return utils.toJson(model);
    }
}
