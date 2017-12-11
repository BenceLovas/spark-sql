import controller.Controller;

import static spark.Spark.*;
import static spark.debug.DebugScreen.enableDebugScreen;

public class Main {

    public static void main(String[] args) {
        exception(Exception.class, (e, req, res) -> e.printStackTrace());
        staticFiles.location("/public");
        port(5000);

        before((request, response) -> {
            System.out.println(request.requestMethod() + " @ " + request.url());
            // check if user logged in
            // put data to localStorage
        });

        Controller controller = Controller.getInstance();

        get("/", controller::renderIndex);
        post("/api/users", controller::userRegistration);
        post("/api/login", controller::userLogin);
        enableDebugScreen();
    }
}
