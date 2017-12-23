package controller;

import controller.util.ControllerUtils;
import dao.implementation.UserDAOImpl;
import model.User;
import org.mindrot.jbcrypt.BCrypt;
import spark.Request;
import spark.Response;
import spark.Session;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserController {

    private static UserController instance = null;
    private static UserDAOImpl userDao = UserDAOImpl.getInstance();
    private static ControllerUtils controllerUtils = ControllerUtils.getInstance();

    private UserController() {
        // prevent instantiation
    }

    public static UserController getInstance() {
        if (instance == null) {
            instance = new UserController();
        }
        return instance;
    }

    public String renderIndex(Request req, Response res) {
        Map model = new HashMap();
        model.put("username", req.session().attribute("username"));
        return controllerUtils.renderTemplate(model, "index");
    }

    public String userLogin(Request req, Response res) {
        // storing password in user object without hash - that's sound problematic
        // there is a "kinda" solution saved under codecool dictionary in chrome
        User user = new User(req.queryParams("userName"), req.queryParams("userPassword"));
        List<User> users = userDao.select(user);
        HashMap<String, Object> model = new HashMap<>();

        if (users.size() == 0) {
            System.out.println("Not valid username.");
            res.redirect("/");
        } else {
            User selectedUser = users.get(0);
            if (BCrypt.checkpw(user.getPassword(), selectedUser.getPassword())) {
                System.out.println("Password matches.");
                req.session().attribute("username", selectedUser.getName());
                model.put("username", selectedUser.getName());
                res.redirect("/index");
            } else {
                System.out.println("Password not matches.");
                res.redirect("/");
            }
        }

        return "";
    }

    public String userRegistration(Request req, Response res) {
        User user = new User(req.queryParams("userName"),
                             BCrypt.hashpw(req.queryParams("userPassword"), BCrypt.gensalt()));

        boolean success = userDao.insert(user);

        if (success) {
            System.out.println("Registration successful.");
            req.session().attribute("username", user.getName());
            res.redirect("/index");
        } else {
            System.out.println("Registration is not successful.");
            res.redirect("/");
        }

        return "";
    }

    public String renderLogin(Request req, Response res) {
        Map model = new HashMap();
        return controllerUtils.renderTemplate(model, "login");
    }

    public String jsonResponse(Request request, Response response) {
        Map<String, Object> model = new HashMap<>();
        return controllerUtils.toJson(model);
    }
}
