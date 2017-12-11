package controller;

import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.ResponseTransformer;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
        System.out.println(userName);
        
        return "";
    }

    public String renderIndex(Request req, Response res) {
        Map<String, Object> model = new HashMap<>();

        Utils utils = Utils.getInstance();
        return utils.renderTemplate(model, "index");
    }

    public String jsonResponse(Request request, Response response) {
        Map<String, Object> model = new HashMap<>();

        Utils utils = Utils.getInstance();
        return utils.toJson(model);
    }
}
