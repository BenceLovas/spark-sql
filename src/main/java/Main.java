import controller.UserController;
import spark.HaltException;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static spark.Spark.*;
import static spark.debug.DebugScreen.enableDebugScreen;

public class Main {

    private static UserController userController = UserController.getInstance();

    public static void main(String[] args) throws Exception {
        exception(Exception.class, (e, req, res) -> e.printStackTrace());
        staticFiles.location("/public");
        port(5000);
        enableDebugScreen();

        Set<String> protectedURI = new HashSet<String>(Arrays.asList("/index"));

        before((request, response) -> {
            System.out.println(request.requestMethod() + " @ " + request.url());
            if (protectedURI.contains(request.uri())) {
                if (request.session().attribute("username") == null) {
                    response.redirect("/login");
                    halt();
                }
            }
        });

        before("/", (request, response) -> {
            if (request.session().attribute("username") == null) {
                response.redirect("/login");
            } else {
                response.redirect("/index");
            }
        });

        get ("/login",      userController::renderLogin);
        get ("/index",      userController::renderIndex);
        post("/api/users",  userController::userRegistration);
        post("/api/login",  userController::userLogin);
        get ("/api/logout", userController::userLogout);

    }
}
