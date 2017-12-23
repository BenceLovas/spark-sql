package controller.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import spark.ModelAndView;
import spark.Request;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import java.lang.reflect.Type;
import java.util.Map;

public class ControllerUtils {

    private static ControllerUtils instance = null;

    private ControllerUtils() {
        // prevent instantiation
    }

    public static ControllerUtils getInstance() {
        if (instance == null) {
            instance = new ControllerUtils();
        }
        return instance;
    }

    public Map<String, String> parseJson(Request request) {
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, String>>(){}.getType();

        return gson.fromJson(request.body(), type);
    }

    public String toJson(Object object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    public String renderTemplate(Map model, String template) {
        return new ThymeleafTemplateEngine().render(new ModelAndView(model, template));
    }

}