package controller;

import dao.implementation.UserDaoImpl;
import org.mindrot.jbcrypt.BCrypt;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserController {

    private static UserController instance = null;

    private UserController() {
        // prevent instantiation
    }

    public static UserController getInstance() {
        if (instance == null) {
            instance = new UserController();
        }
        return instance;
    }

    public String userLogin(Request req, Response res) {
        String userName = req.queryParams("userName");
        String userPassword = req.queryParams("userPassword");

        UserDaoImpl userDao = UserDaoImpl.getInstance();
        List<Map<String, String>> userArray = userDao.select(userName);

        if (userArray.size() == 0) {
            System.out.println("Not valid username.");
        } else {
            Map<String, String> userMap = userArray.get(0);
            String passwordInDatabase = userMap.get("password");

            if (BCrypt.checkpw(userPassword, passwordInDatabase)) {
                System.out.println("Password matches.");
            } else {
                System.out.println("Password not matches.");
            }
        }

        res.redirect("/");
        return "";
    }

    public String userRegistration(Request req, Response res) {
        String userName = req.queryParams("userName");
        String userPassword = req.queryParams("userPassword");
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        String hashedPassword = BCrypt.hashpw(userPassword, BCrypt.gensalt());

        boolean success = userDao.insert(userName, hashedPassword);

        if (success) {
            System.out.println("Registration successful.");
        } else {
            System.out.println("Registration is not successful.");
        }

        res.redirect("/");
        return "";
    }

    public String renderIndex(Request req, Response res) {
        Map model = new HashMap();
        ControllerUtils controllerUtils = ControllerUtils.getInstance();
        return controllerUtils.renderTemplate(model, "index");
    }

    public String jsonResponse(Request request, Response response) {
        Map<String, Object> model = new HashMap<>();

        ControllerUtils controllerUtils = ControllerUtils.getInstance();
        return controllerUtils.toJson(model);
    }
}
