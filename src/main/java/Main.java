import controller.UserController;

import static spark.Spark.*;
import static spark.debug.DebugScreen.enableDebugScreen;

public class Main {

    private static UserController userController = UserController.getInstance();

    public static void main(String[] args) {
        exception(Exception.class, (e, req, res) -> e.printStackTrace());
        staticFiles.location("/public");
        port(5000);

        before((request, response) -> {
            System.out.println(request.requestMethod() + " @ " + request.url());
            // check if user logged in
            // put data to localStorage
        });

        get("/", userController::renderIndex);
        post("/api/users", userController::userRegistration);
        post("/api/login", userController::userLogin);

        enableDebugScreen();
    }
}
