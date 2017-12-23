import controller.UserController;

import static spark.Spark.*;
import static spark.debug.DebugScreen.enableDebugScreen;

public class Main {

    private static UserController userController = UserController.getInstance();

    public static void main(String[] args) throws Exception {
        exception(Exception.class, (e, req, res) -> e.printStackTrace());
        staticFiles.location("/public");
        port(5000);
        enableDebugScreen();

        before((request, response) -> {
            System.out.println(request.requestMethod() + " @ " + request.url());
            // check if user logged in
            // put data to localStorage
        });

        before("/", (request, response) -> {
            if (request.session().attribute("username") != null) {
                response.redirect("/index");
                halt();
            } else {
                response.redirect("/login");
                halt(404, "User Not Logged In.");
            }
        });


        get ("/login",     userController::renderLogin);
        get ("/index",     userController::renderIndex);
        post("/api/users", userController::userRegistration);
        post("/api/login", userController::userLogin);


    }
}
